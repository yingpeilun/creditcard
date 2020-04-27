package com.credit.service;

import com.credit.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value="USER-SERVICE")
public interface UserFeignClient extends UserApi {


}
