package com.knewton.planner.web.controller;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.knewton.planner.web.view.Constant;
import com.knewton.planner.web.view.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.knewton.planner.account.client.AccountClient;
import com.knewton.planner.account.dto.PasswordResetRequest;
import com.knewton.planner.common.api.BaseResponse;
import com.knewton.planner.common.auth.AuthConstant;
import com.knewton.planner.common.error.ServiceException;
import com.knewton.planner.web.service.HelperService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResetController {

    public static final String PASSWORD_RESET_PATH = "/password-reset";

    static final ILogger logger = SLoggerFactory.getLogger(ResetController.class);

    @Autowired
    private PageFactory pageFactory;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private HelperService helperService;

    @RequestMapping(value = PASSWORD_RESET_PATH)
    public String passwordReset(@RequestParam(value="email", required = false) String email,
                                Model model,
                                HttpServletRequest request) {

        // TODO google recaptcha handling ignored for training/demo purpose
        // reference : https://www.google.com/recaptcha

        if (HelperService.isPost(request)) {
            PasswordResetRequest passwordResetRequest = PasswordResetRequest.builder()
                    .email(email)
                    .build();
            BaseResponse baseResponse = null;
            try {
                baseResponse = accountClient.requestPasswordReset(AuthConstant.AUTHORIZATION_WWW_SERVICE, passwordResetRequest);
            } catch (Exception ex) {
                String errMsg = "Failed password reset";
                helperService.logException(logger, ex, errMsg);
                throw new ServiceException(errMsg, ex);
            }
            if (!baseResponse.isSuccess()) {
                helperService.logError(logger, baseResponse.getMessage());
                throw new ServiceException(baseResponse.getMessage());
            }

            logger.info("Initiating password reset");

            model.addAttribute(Constant.ATTRIBUTE_NAME_PAGE, pageFactory.buildResetConfirmPage());
            return Constant.VIEW_CONFIRM;
        }

        model.addAttribute(Constant.ATTRIBUTE_NAME_PAGE, pageFactory.buildResetPage());
        return Constant.VIEW_RESET;
    }
}
