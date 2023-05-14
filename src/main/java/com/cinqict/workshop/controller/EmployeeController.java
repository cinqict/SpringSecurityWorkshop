package com.cinqict.workshop.controller;

import com.cinqict.workshop.domain.Employee;
import com.cinqict.workshop.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public Employee create(@RequestBody final Employee employee) {
        return employeeService.create(employee);
    }

    @GetMapping("/{companyId}")
    public Employee retrieve(@PathVariable final String companyId) {
        return employeeService.retrieve(companyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
    }

    @GetMapping("")
    public List<Employee> retrieveAll() {
        return employeeService.retrieveAll();
    }

    @PutMapping
    public Employee update(@RequestBody final Employee employee) {
        return employeeService.update(employee);
    }

    @DeleteMapping("/{companyId}")
    public void delete(@PathVariable final String companyId) {
        employeeService.delete(companyId);
    }
}
