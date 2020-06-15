package com.knewton.planner.mail.client;

import com.knewton.planner.common.api.BaseResponse;
import com.knewton.planner.mail.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.knewton.planner.mail.MailConstant;

import javax.validation.Valid;

@FeignClient(name = MailConstant.SERVICE_NAME, path = "/v1", url = "${planner.email-service-endpoint}")
public interface MailClient {
    @PostMapping(path = "/send")
    BaseResponse send(@RequestBody @Valid EmailRequest request);
}
