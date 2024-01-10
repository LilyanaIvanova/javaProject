package com.bn.employeemanagement.repositories;

import com.bn.employeemanagement.models.Department;
import com.bn.employeemanagement.models.StaffMember;
import org.springframework.data.jpa.repository.JpaRepositorysitory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repositorysitory;

import java.util.Optional;

@Repositorysitory
public interface StaffMemberRepository extends JpaRepositorysitory<StaffMember, Long> {

    boolean existsStaffMemberByStaffMemberNum(Integer num);

    Optional<StaffMember> findStaffMemberByStaffMemberNum(Integer num);

    @Modifying
    @Query("UPDATE StaffMember SET department = :department WHERE employeeNum = :employeeNum")
    void updateDepartment(Department department, Integer employeeNum);

    @Modifying
    @Query(value = "UPDATE employee SET department_id = :depId WHERE employee_number = :employeeNum", nativeQuery = true)
    void updateDepartmentNative(Long depId, Integer employeeNum);
}
