package com.howtodoinjava.demo.service;

import java.util.HashMap;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.howtodoinjava.demo.model.Employee;

@Service
public class EmployeeManager 
{
	static HashMap<Long, Employee> db = new HashMap<>();
	
	static {
		db.put(1L, new Employee(1L, "Alex", "Gussin"));
		db.put(2L, new Employee(2L, "Brian", "Schultz"));
	}
	
	
	@Cacheable(cacheNames="employeeCache", key="#id")
	public Employee getEmployeeById(Long id) {
		System.out.println("Getting employee from DB");
		return db.get(id);
	}

	
	public Employee getAndPutEmployee(Employee employee) {
		db.put(employee.getId(), employee);
		return db.get(employee.getId());
	}
}