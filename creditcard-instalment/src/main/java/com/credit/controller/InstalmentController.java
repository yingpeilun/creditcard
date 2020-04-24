package com.credit.controller;

import com.credit.bo.PageResult;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.service.InstalmentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class InstalmentController {

    @Autowired
    private InstalmentService instalmentService;

    /**
     * 根据条件返回未出账单信息
     * @param ccid
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("notEveryBill")
    public ResponseEntity<PageResult<TbHistoryNotEverybill>> queryNotBillInfo(
            @RequestParam("ccid") Long ccid,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "10") Integer rows
    ){
        TbCreditCardInfo info = this.instalmentService.queryCreditCardInfoByCcid(ccid);
        Long notBillDateNum = info.getBillDateNum();//20200505

        PageResult<TbHistoryNotEverybill>pageNotBill=this.instalmentService.queryNotEveryBill(ccid,notBillDateNum,page,rows);
        if(CollectionUtils.isEmpty(pageNotBill.getItems())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(pageNotBill);
    }

    /**
     * 根据条件返回已出账单信息
     * @param ccid
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("everyBill")
    public ResponseEntity<PageResult<TbHistoryEverybill>> queryBillInfo(
            @RequestParam("ccid") Long ccid,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "10") Integer rows
    ){
        TbCreditCardInfo info = this.instalmentService.queryCreditCardInfoByCcid(ccid);
        Long notBillDateNum = info.getBillDateNum();//20200505

            SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
            try {
                Date sdfDate = sdf.parse(notBillDateNum.toString());
                DateTime dateTime=new DateTime(sdfDate);
                Date date = dateTime.minusMonths(1).toDate();
                String billDateNum = sdf.format(date);

                PageResult<TbHistoryEverybill>pageBill=this.instalmentService.queryEveryBill(ccid,billDateNum,page,rows);
                if(CollectionUtils.isEmpty(pageBill.getItems())){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                return ResponseEntity.ok(pageBill);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }



    /**
     * 返回被选中分期后的信息
     * @param ids 被选择的id的list集合
     * @param status （1未选择未出账单，2为已出账单）
     * @param num 期数
     */
    @PostMapping("mathInstalment")
    public ResponseEntity<Map<String,String>> mathInstalment(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("status") String status,
            @RequestParam("num") Long num
    ){
        if(status.equals(1)){
            Map<String, String> map = this.instalmentService.mathNotEveryBill(ids,num);
            return ResponseEntity.ok(map);
        }else if(status.equals(2)){
            Map<String, String> map = this.instalmentService.mathEveryBill(ids,num);
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 提交分期
     * @param ids 被选择的id的list集合
     * @param ccid （1未选择未出账单，2为已出账单）
     * @param num 期数
     */
    @PostMapping("aubmitInsta")
    public ResponseEntity<Void> aubmitInsta(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("num") Long num,
            @RequestParam("ccid") Long ccid
    ){
            Boolean boo = null;
            try {
                boo = this.instalmentService.aubmitInstaNot(ids,num,ccid);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(boo){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
