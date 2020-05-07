package com.credit.cilent;

import com.credit.api.ApplyApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("applycard-service")
public interface ApplyClient extends ApplyApi {
}
