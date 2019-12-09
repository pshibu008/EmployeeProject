package org.shivsashi.controller;

import java.util.List;

import org.shivsashi.dao.EmployeeMapper;
import org.shivsashi.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class EmployeeController
{
	@Autowired
	private EmployeeMapper employeeMapper;

	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
	public List<Employee> getAllEmployees()
	{
		return employeeMapper.getAllEmployees();
	}

	@RequestMapping(value = "/deleteEmployeeById/{id}", method = RequestMethod.DELETE)
	public List<Employee> deleteEmployee(@PathVariable("id") int id)
	{
		employeeMapper.deleteEmployee(id);
		return employeeMapper.getAllEmployees();
	}

	@RequestMapping(value = "/insertEmployee", method = RequestMethod.POST)
	public List<Employee> insertEmployee(@RequestBody Employee employee)
	{
		String[] skills = new String[employee.getSkills().size()];
		for (int i = 0; i < employee.getSkills().size(); i++)
		{
			skills[i] = employee.getSkills().get(i).getSkillName();
		}

		employeeMapper.saveEmployee(employee, skills);
		return employeeMapper.getAllEmployees();
	}

	@RequestMapping(value = "/updateEmployeeById/{id}", method = RequestMethod.POST)
	public List<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee)
	{
		String[] skills = new String[employee.getSkills().size()];
		for (int i = 0; i < employee.getSkills().size(); i++)
		{
			skills[i] = employee.getSkills().get(i).getSkillName();
		}
		employeeMapper.updateEmployee(employee, skills, id);
		return employeeMapper.getAllEmployees();
	}

	@RequestMapping(value = "/getEmployeeById/{id}", method = RequestMethod.GET)
	public Employee getEmployee(@PathVariable("id") int id)
	{
		return employeeMapper.getEmployeeById(id);
	}
	
}
