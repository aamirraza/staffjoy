package com.phoebe.staffjoy.company.controller;

import com.phoebe.staffjoy.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.phoebe.staffjoy.common.auth.AuthConstant;
import com.phoebe.staffjoy.common.auth.AuthContext;
import com.phoebe.staffjoy.common.auth.Authorize;
import com.phoebe.staffjoy.common.validation.Group1;
import com.phoebe.staffjoy.common.validation.Group2;
import com.phoebe.staffjoy.company.dto.CompanyDto;
import com.phoebe.staffjoy.company.dto.CompanyList;
import com.phoebe.staffjoy.company.dto.ListCompanyResponse;
import com.phoebe.staffjoy.company.dto.GenericCompanyResponse;
import com.phoebe.staffjoy.company.service.CompanyService;
import com.phoebe.staffjoy.company.service.PermissionService;

@RestController
@RequestMapping("/v1/company")
@Validated
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    PermissionService permissionService;

    @PostMapping(path = "/create")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_SUPPORT_USER,
            AuthConstant.AUTHORIZATION_WWW_SERVICE
    })
    public GenericCompanyResponse createCompany(@RequestBody @Validated({Group2.class}) CompanyDto companyDto) {
        CompanyDto newCompanyDto = companyService.createCompany(companyDto);
        return new GenericCompanyResponse(newCompanyDto);
    }

    @GetMapping(path = "/list")
    @Authorize(value = {AuthConstant.AUTHORIZATION_SUPPORT_USER})
    public ListCompanyResponse listCompanies(@RequestParam int offset, @RequestParam int limit) {
        CompanyList companyList = companyService.listCompanies(offset, limit);
        return new ListCompanyResponse(companyList);
    }

    @GetMapping(path= "/get")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_ACCOUNT_SERVICE,
            AuthConstant.AUTHORIZATION_BOT_SERVICE,
            AuthConstant.AUTHORIZATION_WHOAMI_SERVICE,
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER,
            AuthConstant.AUTHORIZATION_WWW_SERVICE,
            AuthConstant.AUTHORIZATION_ICAL_SERVICE
    })
    public GenericCompanyResponse getCompany(@RequestParam("company_id") String companyId) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyDirectory(companyId);
        }
        CompanyDto companyDto = companyService.getCompany(companyId);
        return new GenericCompanyResponse(companyDto);
    }

    @PutMapping(path= "/update")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER
    })
    public GenericCompanyResponse updateCompany(@RequestBody @Validated({Group1.class}) CompanyDto companyDto) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(companyDto.getId());
        }
        CompanyDto updatedCompanyDto = companyService.updateCompany(companyDto);
        return new GenericCompanyResponse(updatedCompanyDto);
    }
}
