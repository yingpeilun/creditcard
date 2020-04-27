package com.credit.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "bill-service")
public interface BillFeignClient {
}
