package com.knewton.planner.bot.controller;

import com.knewton.planner.bot.client.BotClient;
import com.knewton.planner.bot.dto.OnboardWorkerRequest;
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
import com.knewton.planner.common.api.BaseResponse;
import com.knewton.planner.common.auth.AuthConstant;
import com.knewton.planner.company.client.CompanyClient;
import com.knewton.planner.company.dto.CompanyDto;
import com.knewton.planner.company.dto.GenericCompanyResponse;
import com.knewton.planner.mail.client.MailClient;
import com.knewton.planner.mail.dto.EmailRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext // avoid port conflict
@EnableFeignClients(basePackages = {"com.knewton.planner.bot.client"})
@Slf4j
public class OnBoardingControllerTest {
    @Autowired
    BotClient botClient;

    @MockBean
    AccountClient accountClient;

    @MockBean
    CompanyClient companyClient;

    @MockBean
    MailClient mailClient;

    @Test
    public void testOnboardingWorker() {
        // arrage mock
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

        String companyId = UUID.randomUUID().toString();
        CompanyDto companyDto = CompanyDto.builder()
                .name("test_company001")
                .defaultTimezone(TimeZone.getDefault().getID())
                .defaultDayWeekStarts("Monday")
                .id(companyId)
                .build();
        when(companyClient.getCompany(AuthConstant.AUTHORIZATION_BOT_SERVICE, companyId))
                .thenReturn(new GenericCompanyResponse(companyDto));

        when(mailClient.send(any(EmailRequest.class))).thenReturn(BaseResponse.builder().message("mail sent").build());

        BaseResponse baseResponse = botClient.onboardWorker(OnboardWorkerRequest.builder().companyId(companyId).userId(userId).build());
        log.info(baseResponse.toString());
        assertThat(baseResponse.isSuccess()).isTrue();
        ArgumentCaptor<EmailRequest> argument = ArgumentCaptor.forClass(EmailRequest.class);
        verify(mailClient, times(1)).send(argument.capture());
        EmailRequest emailRequest = argument.getValue();
        log.info(emailRequest.toString());
        assertThat(emailRequest.getTo()).isEqualTo(accountDto.getEmail());
        assertThat(emailRequest.getName()).isEqualTo(accountDto.getName());
        assertThat(emailRequest.getSubject()).isEqualTo("Onboarding Message");
        assertThat(emailRequest.getHtmlBody()).contains(userId, accountDto.getName(), companyDto.getName());
    }
}
