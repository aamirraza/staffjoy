package com.knewton.planner.company.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.knewton.planner.company.model.Job;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, String> {
    List<Job> findJobByTeamId(String teamId);
    Job findJobById(String id);
}
