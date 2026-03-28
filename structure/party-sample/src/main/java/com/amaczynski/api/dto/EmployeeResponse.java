package com.amaczynski.api.dto;

import com.amaczynski.model.Employee;

public record EmployeeResponse(String id, String type, String name, String email) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.id().asString(),
                employee.getClass().getSimpleName(),
                employee.name(),
                employee.email()
        );
    }
}
