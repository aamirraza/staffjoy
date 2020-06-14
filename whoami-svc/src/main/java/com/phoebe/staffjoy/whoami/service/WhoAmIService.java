package com.phoebe.staffjoy.whoami.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.phoebe.staffjoy.whoami.dto.IAmDto;
import com.phoebe.staffjoy.whoami.dto.IntercomSettingsDto;
import com.phoebe.staffjoy.whoami.props.AppProps;
import io.sentry.SentryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoebe.staffjoy.account.client.AccountClient;
import com.phoebe.staffjoy.account.dto.AccountDto;
import com.phoebe.staffjoy.account.dto.GenericAccountResponse;
import com.phoebe.staffjoy.common.auth.AuthConstant;
import com.phoebe.staffjoy.common.crypto.Hash;
import com.phoebe.staffjoy.common.error.ServiceException;
import com.phoebe.staffjoy.company.client.CompanyClient;
import com.phoebe.staffjoy.company.dto.AdminOfList;
import com.phoebe.staffjoy.company.dto.GetAdminOfResponse;
import com.phoebe.staffjoy.company.dto.GetWorkerOfResponse;
import com.phoebe.staffjoy.company.dto.WorkerOfList;

@Service
public class WhoAmIService {

    static final ILogger logger = SLoggerFactory.getLogger(WhoAmIService.class);

    @Autowired
    CompanyClient companyClient;

    @Autowired
    AccountClient accountClient;

    @Autowired
    SentryClient sentryClient;

    @Autowired
    AppProps appProps;

    public IAmDto findWhoIAm(String userId) {
        IAmDto iAmDto = IAmDto.builder()
                .userId(userId)
                .build();

        GetWorkerOfResponse workerOfResponse = null;
        try {
            workerOfResponse = companyClient.getWorkerOf(AuthConstant.AUTHORIZATION_WHOAMI_SERVICE, userId);
        } catch (Exception ex) {
            String errMsg = "unable to get worker of list";
            handleErrorAndThrowException(ex, errMsg);
        }
        if (!workerOfResponse.isSuccess()) {
            handleErrorAndThrowException(workerOfResponse.getMessage());
        }
        WorkerOfList workerOfList = workerOfResponse.getWorkerOfList();
        iAmDto.setWorkerOfList(workerOfList);

        GetAdminOfResponse getAdminOfResponse = null;
        try {
            getAdminOfResponse = companyClient.getAdminOf(AuthConstant.AUTHORIZATION_WHOAMI_SERVICE, userId);
        } catch (Exception ex) {
            String errMsg = "unable to get admin of list";
            handleErrorAndThrowException(ex, errMsg);
        }

        if (!getAdminOfResponse.isSuccess()) {
            handleErrorAndThrowException(getAdminOfResponse.getMessage());
        }
        AdminOfList adminOfList = getAdminOfResponse.getAdminOfList();

        iAmDto.setAdminOfList(adminOfList);

        return iAmDto;
    }

    public IntercomSettingsDto findIntercomSettings(String userId) {
        IntercomSettingsDto intercomSettingsDto = IntercomSettingsDto.builder()
                .appId(appProps.getIntercomAppId())
                .userId(userId)
                .build();


        GenericAccountResponse genericAccountResponse = null;
        try {
            genericAccountResponse = this.accountClient.getAccount(AuthConstant.AUTHORIZATION_WHOAMI_SERVICE, userId);
        } catch (Exception ex) {
            String errMsg = "unable to get account";
            handleErrorAndThrowException(ex, errMsg);
        }
        if (!genericAccountResponse.isSuccess()) {
            handleErrorAndThrowException(genericAccountResponse.getMessage());
        }
        AccountDto account = genericAccountResponse.getAccount();

        intercomSettingsDto.setName(account.getName());
        intercomSettingsDto.setEmail(account.getEmail());
        intercomSettingsDto.setCreatedAt(account.getMemberSince());

        try {
            String userHash = Hash.encode(appProps.getIntercomSigningSecret(), userId);
            intercomSettingsDto.setUserHash(userHash);
        } catch (Exception ex) {
            String errMsg = "fail to compute user hash";
            handleErrorAndThrowException(ex, errMsg);
        }

        return intercomSettingsDto;
    }

    void handleErrorAndThrowException(String errMsg) {
        logger.error(errMsg);
        sentryClient.sendMessage(errMsg);
        throw new ServiceException(errMsg);
    }

    void handleErrorAndThrowException(Exception ex, String errMsg) {
        logger.error(errMsg, ex);
        sentryClient.sendException(ex);
        throw new ServiceException(errMsg, ex);
    }
}
