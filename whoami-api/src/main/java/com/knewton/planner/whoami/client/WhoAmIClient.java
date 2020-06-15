package com.knewton.planner.whoami.client;

import com.knewton.planner.whoami.WhoAmIConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.knewton.planner.common.auth.AuthConstant;
import com.knewton.planner.whoami.dto.FindWhoAmIResponse;
import com.knewton.planner.whoami.dto.GetIntercomSettingResponse;

@FeignClient(name = WhoAmIConstant.SERVICE_NAME, path = "/v1", url = "${planner.whoami-service-endpoint}")
public interface WhoAmIClient {
    @GetMapping
    FindWhoAmIResponse findWhoAmI(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz);

    @GetMapping(value = "/intercom")
    GetIntercomSettingResponse getIntercomSettings(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz);
}
