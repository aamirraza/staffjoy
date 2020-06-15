package com.knewton.planner.company.dto;

import lombok.*;
import com.knewton.planner.common.api.BaseResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericWorkerResponse extends BaseResponse {
    private WorkerDto worker;
}
