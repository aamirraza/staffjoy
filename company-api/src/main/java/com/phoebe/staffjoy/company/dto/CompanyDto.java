package com.phoebe.staffjoy.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.phoebe.staffjoy.common.validation.DayOfWeek;
import com.phoebe.staffjoy.common.validation.Group1;
import com.phoebe.staffjoy.common.validation.Group2;
import com.phoebe.staffjoy.common.validation.Timezone;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {
    @NotBlank(groups = {Group1.class})
    private String id;
    @NotBlank(groups = {Group1.class, Group2.class})
    private String name;
    private boolean archived;
    @Timezone(groups = {Group1.class, Group2.class})
    @NotBlank(groups = {Group1.class, Group2.class})
    private String defaultTimezone;
    @DayOfWeek(groups = {Group1.class, Group2.class})
    @NotBlank(groups = {Group1.class, Group2.class})
    private String defaultDayWeekStarts;
}
