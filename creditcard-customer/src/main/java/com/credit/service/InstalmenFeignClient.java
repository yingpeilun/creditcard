package com.credit.service;

import com.credit.api.InstalmentApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "instalment-service")
public interface InstalmenFeignClient extends InstalmentApi {
}
