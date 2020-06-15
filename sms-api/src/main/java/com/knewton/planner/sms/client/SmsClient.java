package com.knewton.planner.sms.client;

import com.knewton.planner.common.api.BaseResponse;
import com.knewton.planner.common.auth.AuthConstant;
import com.knewton.planner.sms.SmsConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.knewton.planner.sms.dto.SmsRequest;

import javax.validation.Valid;

@FeignClient(name = SmsConstant.SERVICE_NAME, path = "/v1", url = "${planner.sms-service-endpoint}")
public interface SmsClient {
    @PostMapping(path = "/queue_send")
    BaseResponse send(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestBody @Valid SmsRequest smsRequest);
}
