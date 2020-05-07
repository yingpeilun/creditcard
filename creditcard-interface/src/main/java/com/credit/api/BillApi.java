package com.credit.api;

import com.credit.pojo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BillApi {
    /**********************************（ Bill ）****************************************/
    /**
     * 查找用户下的所有卡号(用户安全信息表)
     * @param uid
     * @return
     */
    @PostMapping("/client/findCardIdListByUid")
    @ResponseBody
    public List<TbCreditCardSecurityInfo> findCardIdListByUid(@RequestParam("uid") Long uid);

    /**
     * 通过卡号ccid查找卡片信息（信用卡信息表）
     * @param ccId
     * @return
     */
    @PostMapping("/client/findCardInfoByCcid")
    @ResponseBody
    public TbCreditCardInfo findCardInfoByCcid(@RequestParam("ccId") Long ccId);

    /**
     * 通过（已出月账单日）、（卡号） 查找1个 （月历史账单概要）
     * @param oneMonthbillday 上个月账单日
     * @param ccid 卡号
     * @return
     */
    @PostMapping("/bill/selectOneMonthBillHistoryByLong")
    @ResponseBody
    public TbHistorylMonthbill selectOneMonthBillHistory(@RequestParam("oneMonthbillday")Long oneMonthbillday, @RequestParam("ccid")Long ccid);


    /**
     * 通过（已出月账单日）、（卡号） 查找1个 （月历史账单概要）
     * @param oneMonthbillday 上个月账单日
     * @param ccid 卡号
     * @return
     */
    @PostMapping("/bill/selectOneMonthBillHistoryByDate")
    @ResponseBody
    public TbHistorylMonthbill selectOneMonthBillHistory(@RequestBody Date oneMonthbillday,@RequestParam("ccid") Long ccid);

    /**
     * 通过（最近上月账单日）、（最近上上月账单日-1）、（卡号）分页查找n个（最近上月的每笔历史账单明细）
     * s （上上个月账单日+1）
     * ss 上个月账单日
     * ccid 所选的卡号
     * @param map s ss ccid
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    @PostMapping("/bill/selectOneMonthEveryBillHistory")
    @ResponseBody
    public PageInfo<TbHistoryEverybill> selectOneMonthEveryBillHistory(
            @RequestBody Map<String, Object> map,
            @RequestParam("pageNo")Integer pageNo,
            @RequestParam("pageSize")Integer pageSize);

    /*********************************（ NotBill ）*********************************/
    /**
     * 通过（上个月账单日+1）、（当月还款日-1）、（卡号）分页查找n个（当月的每笔历史账单明细）
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param map s p ccid
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    @PostMapping("/notbill/selectOneMonthEveryNotBillHistory")
    @ResponseBody
    public PageInfo<TbHistoryNotEverybill> selectOneMonthEveryNotBillHistory(@RequestBody Map<String,Object> map,@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize);


    /**
     * 通过（上个月账单日+1）、（当月还款日-1）、（卡号）查找n个（当月的每笔历史账单明细）
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param map s p ccid
     * @return List<TbHistoryNotEverybill>
     */
    @PostMapping("/notbill/getOneMonthEveryNotBillHistory")
    @ResponseBody
    public List<TbHistoryNotEverybill> getOneMonthEveryNotBillHistory(@RequestBody Map<String,Object> map);
}
