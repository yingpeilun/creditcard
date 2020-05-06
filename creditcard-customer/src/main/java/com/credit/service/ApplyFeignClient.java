package com.credit.service;

import com.credit.api.ApplyApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value ="applycard-service")
public interface ApplyFeignClient extends ApplyApi {
}
