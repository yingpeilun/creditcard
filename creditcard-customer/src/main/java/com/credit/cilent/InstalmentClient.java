package com.credit.cilent;

import com.credit.api.InstalmentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("instalment-service")
public interface InstalmentClient extends InstalmentApi {
}
