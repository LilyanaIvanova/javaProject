package com.bn.employeemanagement.mappers;

import com.bn.employeemanagement.dto.StaffMemberDto;
import com.bn.employeemanagement.models.StaffMember;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Converters;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StaffMemberConverterTest {

    private final StaffMemberConverter underTest = Converters.getConverter(StaffMemberConverter.class);

    @ParameterizedTest
    @MethodSource("paramProvider")
    void convertDtoToEntityTest(StaffMemberDto dto, String[] emptyFields) {
        StaffMember result = underTest.convertDtoToEntity(dto, 1L);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static Stream<Arguments> paramProvider() {
        return Stream.of(
                Arguments.of(
                        new StaffMemberDto("John", null, null, null),
                        new String[]{"lastName", "employeeNum", "cityId", "department", "projects"}
                ),
                Arguments.of(
                        new StaffMemberDto(null, "Travolta", null, null),
                        new String[]{"firstName", "employeeNum", "cityId", "department", "projects"}
                ),
                Arguments.of(
                        new StaffMemberDto(null, null, 123, null),
                        new String[]{"lastName", "firstName", "cityId", "department", "projects"}
                )
        );
    }
}
