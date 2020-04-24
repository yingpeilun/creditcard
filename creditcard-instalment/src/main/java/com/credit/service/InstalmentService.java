package com.credit.service;

import com.credit.bo.PageResult;
import com.credit.mapper.*;
import com.credit.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InstalmentService {

    @Autowired
    private EveryBillMapper everyBillMapper;

    @Autowired
    private NotEveryBillMapper notEveryBillMapper;

    @Autowired
    private CreditCardSecurityInfoMapper creditCardSecurityInfoMapper;

    @Autowired
    private CreditCardIfonMapper creditCardIfonMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private InstallmentMapper installmentMapper;


    /**
     * 根据uid查询到所有ccid
     * @param uid
     * @return
     */
    public List<Long> queryCcidByUid(Long uid){
        TbCreditCardSecurityInfo cc=new TbCreditCardSecurityInfo();
        cc.setUid(uid);
        List<TbCreditCardSecurityInfo> infos = this.creditCardSecurityInfoMapper.select(cc);
        List<Long> infoList = infos.stream().map(Info -> Info.getCcId()).collect(Collectors.toList());
        return infoList;
    }

    /**
     * 根据ccid查询信用卡信息
     * @param ccid
     * @return
     */
    public TbCreditCardInfo queryCreditCardInfoByCcid(Long ccid){
        TbCreditCardInfo info=new TbCreditCardInfo();
        info.setCcId(ccid);
        return this.creditCardIfonMapper.selectOne(info);

    }

    /**
     * 计算选中的未出账单的分期结果
     * @param ids
     * @param num
     * @return
     */
    public Map<String, String> mathNotEveryBill(List<Long> ids,Long num) {
        Example example=new Example(TbHistoryNotEverybill.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",ids);
        List<TbHistoryNotEverybill> notBillList = this.notEveryBillMapper.selectByExample(example);
        Long payAmount=0L;
        for (TbHistoryNotEverybill notBill : notBillList) {
            Long aa=notBill.getPayAmount();
            payAmount=aa+aa;
        }
        Map<String, String> map = getStringStringMap(num, payAmount);
        return map;
    }
    /**
     * 计算选中的已出账单的分期结果
     * @param ids
     * @param num
     * @return
     */
    public Map<String, String> mathEveryBill(List<Long> ids, Long num) {
        Example example=new Example(TbHistoryEverybill.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",ids);
        List<TbHistoryEverybill> billList = this.everyBillMapper.selectByExample(example);
        Long payAmount=0L;
        for (TbHistoryEverybill notBill : billList) {
            Long aa=notBill.getPayAmount();
            payAmount=aa+aa;
        }
        Map<String, String> map = getStringStringMap(num, payAmount);
        return map;
    }


    /**
     * 计算利息，本金，总还款额
     * @param num
     * @param payAmount
     * @return
     */
    private Map<String, String> getStringStringMap(Long num, Long payAmount) {

        Double instalMoney=0.00;
        Double everyMoney=0.00;
        Double total=0.00;
        switch (num.intValue()){
            case 3 :
                instalMoney=payAmount*0.1/12;//每期的利息
                everyMoney=payAmount*0.1*10/3;//每期的本金
                total=payAmount.doubleValue()+instalMoney*3;
                break; //可选
            case 6 :
                instalMoney=payAmount*0.09/12;//每期的利息
                everyMoney=payAmount*0.1*10/6;//每期的本金
                total=payAmount.doubleValue()+instalMoney*6;
                break; //可选
            case 12 :
                instalMoney=payAmount*0.088/12;//每期的利息
                everyMoney=payAmount*0.1*10/12;//每期的本金
                total=payAmount.doubleValue()+instalMoney*12;
                break; //可选
            case 24 :
                instalMoney=payAmount*0.086/12;//每期的利息
                everyMoney=payAmount*0.1*10/24;//每期的本金
                total=payAmount.doubleValue()+instalMoney*24;
                break; //可选
            //你可以有任意数量的case语句
            default : //可选
                instalMoney=0.00;
                everyMoney=0.00;
                total=0.00;
                //语句
        }
        DecimalFormat df=new DecimalFormat("0.00");
        String instalMoneyS=df.format(instalMoney);
        String totalS = df.format(total);
        String everyMoneyS = df.format(everyMoney);

        Map<String,String>map=new HashMap<>();
        map.put("instalMoney",instalMoneyS);//每期的利息
        map.put("everyMoney",everyMoneyS);//每期的本金
        map.put("total",totalS);//总还款额
        map.put("payAmount",payAmount.toString());
        return map;
    }

    /**
     * 提交未出账单分期
     * @param ids 被选择的id的list集合
     * @param num 期数
     */
    public Boolean aubmitInstaNot(List<Long> ids, Long num,Long ccid) throws ParseException {

        TbCreditCardInfo info =new TbCreditCardInfo();
        info.setCcId(ccid);
        TbCreditCardInfo infoOne = this.creditCardIfonMapper.selectOne(info);
        Map<String, String> map = mathNotEveryBill(ids, num);
        String payAmount = map.get("payAmount");
        String instalMoney = map.get("instalMoney");
        String everyMoney = map.get("everyMoney");
        String total = map.get("total");

        TbInstallment installment=new TbInstallment();
        installment.setCcId(ccid);
        installment.setInstaType(1L);//分期类型
        installment.setInstaStatus(1L);//分期状态
        installment.setInstaTotal(num);//分期总期数
        installment.setInstaAmount(Long.valueOf(payAmount));//'分期金额'
        installment.setInstaCurr(1L);// '当前期数'
        installment.setInstaTotalInserest(Long.valueOf(instalMoney)*num);// '分期总利息'
        installment.setInstaTotalPricipal(Long.valueOf(everyMoney)*num);// '分期总本金'
        installment.setCurrInserest(Long.valueOf(instalMoney));//'当月利息'
        installment.setCurrPricipal(Long.valueOf(everyMoney));//'当月本金'
        Date d=new Date();
        installment.setInstaDate(d);//分期时间

        Long billDateNum = infoOne.getBillDateNum();
        Long repayDateNum = info.getRepayDateNum();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Date date1 = sdf.parse(billDateNum.toString());
        Date date2 = sdf.parse(repayDateNum.toString());
        DateTime dateTime1= new DateTime(date1);
        DateTime dateTime2= new DateTime(date2);
        String dateformat1=sdf.format(dateTime1.plusMonths(1).toDate());
        String dateformat2=sdf.format(dateTime2.plusMonths(1).toDate());
        Date date3 = sdf.parse(dateformat1);
        Date date4 = sdf.parse(dateformat2);

        installment.setBillDate(date3);
        installment.setBillDateNum(Long.valueOf(dateformat1));
        installment.setRepayDate(date4);
        installment.setRepayDateNum(Long.valueOf(dateformat2));

        int insert = this.installmentMapper.insert(installment);

        if(insert!=0){
            Map<String,String>msg=new HashMap<>();
            msg.put("ccid",ccid.toString());
            msg.put("payInfo","分期");
            msg.put("moneyType","人民币");
            Long c=Long.valueOf(everyMoney)*num;
            msg.put("payAmount",c.toString());
            msg.put("payDateNum",sdf.format(d));
            msg.put("getMoneyCard","中信银行");
            msg.put("billDateNum",dateformat1);

            this.rabbitTemplate.convertAndSend("creditCard.bill.exchange","bill.instalment.msg",msg);
            return true;
        }
        return false;
    }

    /**
     * 根据条件返回未出账单信息
     * @param ccid
     * @param notBillDateNum
     * @param page
     * @param rows
     * @return
     */
    public PageResult<TbHistoryNotEverybill> queryNotEveryBill(Long ccid,Long notBillDateNum, Integer page, Integer rows) {
        Example example=new Example(TbHistoryNotEverybill.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("cc_id",ccid).andEqualTo("bill_date_num",notBillDateNum);

        //添加分页
        PageHelper.startPage(page,rows);

        //找到适合的通用mapper方法
        List<TbHistoryNotEverybill> everybills = this.notEveryBillMapper.selectByExample(example);
        PageInfo<TbHistoryNotEverybill> pageInfo=new PageInfo<>(everybills);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());

    }

    /**
     * 根据条件返回已出账单信息
     * @param ccid
     * @param billDateNum
     * @param page
     * @param rows
     * @return
     */
    public PageResult<TbHistoryEverybill> queryEveryBill(Long ccid,String billDateNum, Integer page, Integer rows) {
        Example example=new Example(TbHistoryNotEverybill.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("cc_id",ccid).andEqualTo("bill_date_num",Long.valueOf(billDateNum));
        //添加分页
        PageHelper.startPage(page,rows);

        //找到适合的通用mapper方法
        List<TbHistoryEverybill> bills = this.everyBillMapper.selectByExample(example);
        PageInfo<TbHistoryEverybill> pageInfo=new PageInfo<>(bills);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }
}
