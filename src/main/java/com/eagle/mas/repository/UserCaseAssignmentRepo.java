package com.eagle.mas.repository;

import com.eagle.mas.model.UserCaseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCaseAssignmentRepo extends JpaRepository<UserCaseAssignment,String> {


    public UserCaseAssignment findByUserId(String userId);

    @Query(value = "SELECT t1.requestId FROM UserCaseAssignment t1")
    List<String> getRequestIds();
}
