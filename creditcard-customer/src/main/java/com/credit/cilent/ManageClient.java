package com.credit.cilent;

import com.credit.api.ManageApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("cardmanage-service")
public interface ManageClient extends ManageApi {
}
