package com.bn.employeemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StaffMemberManagementApplicationTest {

	@Autowired
	private StaffMemberManagementApplication underTest;

	@Test
	void contextLoads() {
		assertThat(underTest).isNotNull();
	}

}
