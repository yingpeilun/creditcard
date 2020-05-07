package com.credit.cilent;

import com.credit.api.BillApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("bill-service")
public interface BillClient extends BillApi {
}
