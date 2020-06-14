package com.phoebe.staffjoy.whoami.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.phoebe.staffjoy.company.dto.AdminOfList;
import com.phoebe.staffjoy.company.dto.WorkerOfList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IAmDto {
    private boolean support;
    private String userId;
    private WorkerOfList workerOfList;
    private AdminOfList adminOfList;
}
