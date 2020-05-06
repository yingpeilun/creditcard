package com.credit.service;

import com.credit.api.BillApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "bill-service")
public interface BillFeignClient extends BillApi{



}
