package com.bn.employeemanagement.repositories;

import com.bn.employeemanagement.models.StaffMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({
        "/sql/employee_data.sql"
})
class StaffMemberRepositoryTest {

    @Autowired
    private StaffMemberRepository employeeRepository;

    @Test
    void testFindStaffMemberByStaffMemberNumSuccess() {
        Optional<StaffMember> result = employeeRepository.findStaffMemberByStaffMemberNum(123);
        assertThat(result.get().getFirstName())
                .isNotNull()
                .isEqualTo("John");
    }
}
