package com.knewton.planner.company.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerEntries {
    private String companyId;
    private String teamId;
    @Builder.Default
    List<DirectoryEntryDto> workers = new ArrayList<>();
}
