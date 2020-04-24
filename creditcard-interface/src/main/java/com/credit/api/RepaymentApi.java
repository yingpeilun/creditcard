package com.credit.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface RepaymentApi {

    /**
     * 信用卡还款
     * @param ccId 信用卡卡号
     * @param scId 储蓄卡卡号
     * @param money 还款金额
     * @return map
     */
    @PostMapping("repayMoney")
    public Map<String,String> repayMoney(
            @RequestParam("ccId") Long ccId,
            @RequestParam("scId") Long scId,
            @RequestParam("money") Long money
    );
}
