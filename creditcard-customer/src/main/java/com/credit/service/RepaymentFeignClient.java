package com.credit.service;

import com.credit.api.RepaymentApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "repayment-service")
public interface RepaymentFeignClient extends RepaymentApi {
}
