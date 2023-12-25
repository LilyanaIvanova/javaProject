package com.bn.employeemanagement.controllers;

import com.bn.employeemanagement.models.StaffMember;
import com.bn.employeemanagement.services.StaffMemberService;
import com.bn.employeemanagement.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StaffMemberHandler.class)
class StaffMemberHandlerTest {

    @MockBean
    private StaffMemberService employeeService;

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void pageStaffMembersDefaultValuesOkTest() throws Exception {
        when(employeeService.getAllStaffMembers(any())).thenReturn(getStaffMemberPage());
        mockMvc.perform(get("/page/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    private Page<StaffMember> getStaffMemberPage() {
        Pageable pageable = PageRequest.of(0, 5);
        return new PageImpl<StaffMember>(List.of(new StaffMember()), pageable, 1L);
    }
}
