package com.phoebe.staffjoy.company.dto;

import lombok.*;
import com.phoebe.staffjoy.common.api.BaseResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListTeamResponse extends BaseResponse {
    private TeamList teamList;
}
