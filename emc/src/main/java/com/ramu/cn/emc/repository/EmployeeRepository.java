package com.ramu.cn.emc.repository;

import com.ramu.cn.emc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
