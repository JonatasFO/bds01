package com.devsuperior.bds01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findAll(Pageable pageable) {
		Page<Employee> page = repository.findAll(pageable);
		return page.map(department -> new EmployeeDTO(department));
	}
	
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee employee = new Employee();
		copyDtoToEntity(dto, employee);
		employee = repository.save(employee);
		return new EmployeeDTO(employee);
	}

	private void copyDtoToEntity(EmployeeDTO dto, Employee employee) {
		employee.setName(dto.getName());
		employee.setEmail(dto.getEmail());
		employee.setDepartment(new Department(dto.getDepartmentId(), null));
	}

}
