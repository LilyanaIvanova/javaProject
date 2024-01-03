package com.bn.employeemanagement.services;

import com.bn.employeemanagement.dto.StaffMemberDto;
import com.bn.employeemanagement.mappers.DepartmentRepository;
import com.bn.employeemanagement.mappers.StaffMemberConverter;
import com.bn.employeemanagement.models.Department;
import com.bn.employeemanagement.models.StaffMember;
import com.bn.employeemanagement.repositories.StaffMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffMemberService {

    private final StaffMemberRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final StaffMemberConverter employeeConverter;

    public List<StaffMember> getAllStaffMembers() {
        System.out.println("\u001B[31m" + " " + Thread.currentThread().getName());
        return employeeRepository.findAll();
    }

    public List<Department> getAllDepartments() {
        System.out.println("\u001B[32m" + " " + Thread.currentThread().getName());
        return departmentRepository.findAll();
    }

    public Page<StaffMember> getAllStaffMembers(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Transactional
    public void updateStaffMemberDepartment(Long depId, Integer employeeNum) {
        Department department = departmentRepository.findById(depId).get();
        employeeRepository.updateDepartment(department, employeeNum);
    }

    @Transactional
    public void updateStaffMemberDepartmentNative(Long depId, Integer employeeNum) {
        employeeRepository.updateDepartmentNative(depId, employeeNum);
    }

    public StaffMember saveStaffMember(StaffMemberDto dto) {
        Optional<StaffMember> dbObject = employeeRepository.findStaffMemberByStaffMemberNum(dto.employeeNum());
        Long id;
        if(dbObject.isPresent()) {
            id = dbObject.get().getId();
            log.info("Updating StaffMember with id {}", id);
        } else {
            id = null;
            log.info("Inserting new StaffMember");
        }
        StaffMember employee = employeeConverter.convertDtoToEntity(dto, id);
        return employeeRepository.saveAndFlush(employee);
    }
}
