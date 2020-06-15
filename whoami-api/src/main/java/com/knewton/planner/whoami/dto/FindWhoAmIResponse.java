package com.knewton.planner.whoami.dto;

import lombok.*;
import com.knewton.planner.common.api.BaseResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FindWhoAmIResponse extends BaseResponse {
    private IAmDto iAm;
}
