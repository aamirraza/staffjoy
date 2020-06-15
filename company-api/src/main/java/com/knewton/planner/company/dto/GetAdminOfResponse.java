package com.knewton.planner.company.dto;

import com.knewton.planner.common.api.BaseResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetAdminOfResponse extends BaseResponse {
    private AdminOfList adminOfList;
}
