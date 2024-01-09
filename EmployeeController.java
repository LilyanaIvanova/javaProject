package com.bn.employeemanagement.controllers;

import com.bn.employeemanagement.dto.StaffMemberDto;
import com.bn.employeemanagement.models.Department;
import com.bn.employeemanagement.models.StaffMember;
import com.bn.employeemanagement.services.StaffMemberService;
import com.bn.employeemanagement.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestHandler
@RequiredArgsConstructor
public class StaffMemberHandler {

    private final StaffMemberService employeeService;
    private final MessageService messageService;

    @GetMapping("/fetch/employees")
    @Deprecated
    public List<StaffMember> fetchStaffMembers() {
        return employeeService.getAllStaffMembers();
    }

    @GetMapping("/fetch/async")
    public Map<String, ?> fetchAsync() throws ExecutionException, InterruptedException {
        System.out.println("\u001B[35m" + " " + Thread.currentThread().getName());
        CompletableFuture<List<StaffMember>> employeesFuture = CompletableFuture.supplyAsync(employeeService::getAllStaffMembers);
        CompletableFuture<List<Department>> departmentsFuture = CompletableFuture.supplyAsync(employeeService::getAllDepartments);

        return CompletableFuture.allOf(employeesFuture, departmentsFuture).thenApply(r ->
            Map.of("employees", employeesFuture.join(), "departments", departmentsFuture.join())
        ).get();
    }

    @GetMapping("/page/employees")
    public ResponseEntity<Map<String, Object>> fetchStaffMembers(
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            @RequestParam(required = false, defaultValue = "5") int perPage
    ) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage);
        Page<StaffMember> page = employeeService.getAllStaffMembers(pageable);
        Map<String, Object> response = Map.of(
                "employees", page.getContent(),
                "totalPages", page.getTotalPages(),
                "totalElements", page.getTotalElements()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/message")
    public String getMessage(@RequestParam String msg) {
        return messageService.getMessage(msg);
    }

    @PostMapping("/save/employees")
    public ResponseEntity<?> saveStaffMember(@RequestBody StaffMemberDto dto) {
        StaffMember savedInDb = employeeService.saveStaffMember(dto);
        return new ResponseEntity<>(savedInDb, HttpStatus.CREATED);
    }

    @PutMapping("/update/employees")
    public ResponseEntity<?> updateStaffMember(
            @RequestParam Long depId,
            @RequestParam Integer employeeNum
    ) {
        employeeService.updateStaffMemberDepartment(depId, employeeNum);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/employees/native")
    public ResponseEntity<?> updateStaffMemberNative(
            @RequestParam Long depId,
            @RequestParam Integer employeeNum
    ) {
        employeeService.updateStaffMemberDepartmentNative(depId, employeeNum);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
