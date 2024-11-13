package com.ramu.cn.emc.service.impl;

import com.ramu.cn.emc.dto.EmployeeDto;
import com.ramu.cn.emc.entity.Employee;
import com.ramu.cn.emc.exception.ResourceNotFoundException;
import com.ramu.cn.emc.mapper.EmployeeMapper;
import com.ramu.cn.emc.repository.EmployeeRepository;
import com.ramu.cn.emc.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId) // Throw a custom exception for not having resource
                .orElseThrow( ()-> new ResourceNotFoundException("Employee not exists with the given id : " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll(); // These mapping functions are given by Spring Data JPA (Java persistence API)
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto) // take list of emp and map it to employeeDto , collectors will convert it into proper list
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId , EmployeeDto updatedEmployee) { // if employee not found then throw exception
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("employee not found with given id : " + employeeId));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        Employee updateEmployeeObject = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updateEmployeeObject);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("employee not found with given id : " + employeeId));
        employeeRepository.deleteById(employeeId);
    }

}
