package com.credit.service;

import com.credit.api.ManageApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "cardmanage-service")
public interface ManageFeignClient extends ManageApi {
}
