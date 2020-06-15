package com.knewton.planner.bot.controller;

import com.knewton.planner.bot.client.BotClient;
import com.knewton.planner.bot.dto.GreetingRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import com.knewton.planner.account.client.AccountClient;
import com.knewton.planner.account.dto.AccountDto;
import com.knewton.planner.account.dto.GenericAccountResponse;
import com.knewton.planner.bot.BotConstant;
import com.knewton.planner.common.api.BaseResponse;
import com.knewton.planner.common.api.ResultCode;
import com.knewton.planner.common.auth.AuthConstant;
import com.knewton.planner.mail.client.MailClient;
import com.knewton.planner.mail.dto.EmailRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext // avoid port conflict
@EnableFeignClients(basePackages = {"com.knewton.planner.bot.client"})
@Slf4j
public class GreetingControllerTest {
    @Autowired
    BotClient botClient;

    @MockBean
    AccountClient accountClient;

    @MockBean
    MailClient mailClient;

    @Test
    public void testGreeting() {
        String userId = UUID.randomUUID().toString();
        AccountDto accountDto = AccountDto.builder()
                .name("test_user001")
                .phoneNumber("11111111111")
                .email("test_user001@staffjoy.xyz")
                .id(userId)
                .memberSince(Instant.now().minus(30, ChronoUnit.DAYS))
                .confirmedAndActive(true)
                .photoUrl("https://staffjoy.xyz/photo/test01.png")
                .build();
        when(accountClient.getAccount(AuthConstant.AUTHORIZATION_BOT_SERVICE, userId))
                .thenReturn(new GenericAccountResponse(accountDto));

        when(mailClient.send(any(EmailRequest.class))).thenReturn(BaseResponse.builder().message("mail sent").build());

        BaseResponse baseResponse = botClient.sendSmsGreeting(GreetingRequest.builder().userId(userId).build());
        log.info(baseResponse.toString());
        assertThat(baseResponse.isSuccess()).isTrue();

        ArgumentCaptor<EmailRequest> argument = ArgumentCaptor.forClass(EmailRequest.class);
        verify(mailClient, times(1)).send(argument.capture());
        EmailRequest emailRequest = argument.getValue();
        assertThat(emailRequest.getTo()).isEqualTo(accountDto.getEmail());
        assertThat(emailRequest.getName()).isEqualTo(accountDto.getName());
        assertThat(emailRequest.getSubject()).isEqualTo("Planner  Greeting");
        assertThat(emailRequest.getHtmlBody()).isEqualTo(BotConstant.GREETING_EMAIL_TEMPLATE);
    }

    @Test
    public void testGreetingException() {
        String userId = UUID.randomUUID().toString();
        when(accountClient.getAccount(AuthConstant.AUTHORIZATION_BOT_SERVICE, userId))
                .thenThrow(new RuntimeException("get account failure exception"));

        BaseResponse baseResponse = botClient.sendSmsGreeting(GreetingRequest.builder().userId(userId).build());
        log.info(baseResponse.toString());
        assertThat(baseResponse.isSuccess()).isFalse();
        assertThat(baseResponse.getCode()).isEqualTo(ResultCode.FAILURE);
    }
}
