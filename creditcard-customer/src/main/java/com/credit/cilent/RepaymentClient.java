package com.credit.cilent;

import com.credit.api.RepaymentApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient("repayment-service")
public interface RepaymentClient extends RepaymentApi {
}
