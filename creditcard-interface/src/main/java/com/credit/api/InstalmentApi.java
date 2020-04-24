package com.credit.api;

import com.credit.bo.PageResult;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistoryNotEverybill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InstalmentApi {

    /**
     * 根据条件返回未出账单信息
     * @param ccid
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("notEveryBill")
    public PageResult<TbHistoryNotEverybill> queryNotBillInfo(
            @RequestParam("ccid") Long ccid,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "10") Integer rows);

    /**
     * 根据条件返回已出账单信息
     * @param ccid
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("everyBill")
    public PageResult<TbHistoryEverybill> queryBillInfo(
            @RequestParam("ccid") Long ccid,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "10") Integer rows);



    /**
     * 返回被选中分期后的信息
     * @param ids 被选择的id的list集合
     * @param status （1未选择未出账单，2为已出账单）
     * @param num 期数
     */
    @PostMapping("mathInstalment")
    public Map<String,String> mathInstalment(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("status") String status,
            @RequestParam("num") Long num
    );

    /**
     * 提交分期
     * @param ids 被选择的id的list集合
     * @param ccid （1未选择未出账单，2为已出账单）
     * @param num 期数
     */
    @PostMapping("aubmitInsta")
    public Void aubmitInsta(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("num") Long num,
            @RequestParam("ccid") Long ccid
    );
}
