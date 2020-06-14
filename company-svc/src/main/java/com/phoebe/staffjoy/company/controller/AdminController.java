package com.phoebe.staffjoy.company.controller;

import com.phoebe.staffjoy.company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.phoebe.staffjoy.common.api.BaseResponse;
import com.phoebe.staffjoy.common.api.ResultCode;
import com.phoebe.staffjoy.common.auth.AuthConstant;
import com.phoebe.staffjoy.common.auth.AuthContext;
import com.phoebe.staffjoy.common.auth.Authorize;
import com.phoebe.staffjoy.common.auth.PermissionDeniedException;
import com.phoebe.staffjoy.common.error.ServiceException;
import com.phoebe.staffjoy.company.dto.*;
import com.phoebe.staffjoy.company.service.AdminService;
import com.phoebe.staffjoy.company.service.PermissionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/company/admin")
@Validated
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    PermissionService permissionService;

    @ApiOperation("Add description here")
    @GetMapping(path = "/list")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER
    })
    public ListAdminResponse listAdmins(@RequestParam String companyId) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(companyId);
        }
        AdminEntries adminEntries = adminService.listAdmins(companyId);
        return new ListAdminResponse(adminEntries);
    }

    @GetMapping(path = "/get")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER,
            AuthConstant.AUTHORIZATION_WWW_SERVICE
    })
    public GenericDirectoryResponse getAdmin(@RequestParam String companyId, @RequestParam String userId) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(companyId);
        }
        DirectoryEntryDto directoryEntryDto = adminService.getAdmin(companyId, userId);
        if (directoryEntryDto == null) {
            throw new ServiceException(ResultCode.NOT_FOUND, "admin relationship not found");
        }
        return new GenericDirectoryResponse(directoryEntryDto);
    }

    @PostMapping(path = "/create")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER,
            AuthConstant.AUTHORIZATION_WWW_SERVICE
    })
    public GenericDirectoryResponse createAdmin(@RequestBody @Validated DirectoryEntryRequest request) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(request.getCompanyId());
        }
        DirectoryEntryDto directoryEntryDto = adminService.createAdmin(request.getCompanyId(), request.getUserId());
        return new GenericDirectoryResponse(directoryEntryDto);
    }

    @DeleteMapping(path = "/delete")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER
    })
    public BaseResponse deleteAdmin(@RequestBody @Validated DirectoryEntryRequest request) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(request.getCompanyId());
        }
        adminService.deleteAdmin(request.getCompanyId(), request.getUserId());
        return BaseResponse.builder().build();
    }

    @GetMapping(path = "/admin_of")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_ACCOUNT_SERVICE,
            AuthConstant.AUTHORIZATION_WHOAMI_SERVICE,
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER,
            AuthConstant.AUTHORIZATION_WWW_SERVICE
    })
    public GetAdminOfResponse getAdminOf(@RequestParam String userId) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            if (!userId.equals(AuthContext.getUserId())) {
                throw new PermissionDeniedException("You do not have access to this service");
            }
        }
        AdminOfList adminOfList = adminService.getAdminOf(userId);
        return new GetAdminOfResponse(adminOfList);
    }
}
