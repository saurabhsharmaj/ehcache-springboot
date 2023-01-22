package com.howtodoinjava.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.demo.model.Employee;
import com.howtodoinjava.demo.service.EmployeeManager;

@RestController
@RequestMapping("/student")
public class ApiController {

	@Autowired
	private EmployeeManager employeeManager;

	
	@Autowired
	private CacheManager cacheManager;

	@GetMapping
	public ResponseEntity<Employee> updateStudent(@RequestParam(name = "id", required = false) Long id)
			throws InterruptedException {
		Employee emp=cacheManager.getCache("employeeCache").get(id,Employee.class);
		if(emp==null) {
			emp =employeeManager.getEmployeeById(id);
			if(emp==null) {
				emp=employeeManager.getAndPutEmployee(new Employee(id, "s", "s"));
				cacheManager.getCache("employeeCache").put(emp.getId(),emp);
			}
		}
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

	@GetMapping("/saveAndGet")
	public ResponseEntity<Employee> fetchStudent(@RequestParam(name = "id", required = false) Long id)
			throws InterruptedException {
		return new ResponseEntity<Employee>(cacheManager.getCache("employeeCache").get(id,Employee.class),HttpStatus.OK);
	}
}