package com.knewton.planner.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.knewton.planner.common.auth.AuthConstant;
import com.knewton.planner.common.auth.AuthContext;
import com.knewton.planner.common.auth.Authorize;
import com.knewton.planner.company.dto.*;
import com.knewton.planner.company.service.JobService;
import com.knewton.planner.company.service.PermissionService;

@RestController
@RequestMapping("/v1/company/job")
@Validated
public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    PermissionService permissionService;

    @PostMapping(path = "/create")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER
    })
    public GenericJobResponse createJob(@RequestBody @Validated CreateJobRequest request) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(request.getCompanyId());
        }

        JobDto jobDto = jobService.createJob(request);

        return new GenericJobResponse(jobDto);
    }

    @GetMapping(path = "/list")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER
    })
    public ListJobResponse listJobs(@RequestParam String companyId, @RequestParam String teamId) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) { // TODO need confirm
            permissionService.checkPermissionTeamWorker(companyId, teamId);
        }

        JobList jobList = jobService.listJobs(companyId, teamId);

        return new ListJobResponse(jobList);
    }

    @GetMapping(path = "/get")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER,
            AuthConstant.AUTHORIZATION_BOT_SERVICE
    })
    public GenericJobResponse getJob(String jobId, String companyId, String teamId) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionTeamWorker(companyId, teamId);
        }

        JobDto jobDto = jobService.getJob(jobId, companyId, teamId);

        return new GenericJobResponse(jobDto);
    }

    @PutMapping(path = "/update")
    @Authorize(value = {
            AuthConstant.AUTHORIZATION_AUTHENTICATED_USER,
            AuthConstant.AUTHORIZATION_SUPPORT_USER
    })
    public GenericJobResponse updateJob(@RequestBody @Validated JobDto jobDto) {
        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            permissionService.checkPermissionCompanyAdmin(jobDto.getCompanyId());
        }

        JobDto updatedJobDto = jobService.updateJob(jobDto);

        return new GenericJobResponse(updatedJobDto);
    }
}
