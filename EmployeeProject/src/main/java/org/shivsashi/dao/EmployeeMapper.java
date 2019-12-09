package org.shivsashi.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.shivsashi.entity.Employee;
import org.shivsashi.entity.SkillSet;
import org.shivsashi.util.MyBatisUtil;

public class EmployeeMapper
{
	/**
	 * @param employee
	 * @param skills
	 */
	public void saveEmployee(Employee employee, String[] skills)
	{
		String[] skill = skills[0].split(",");
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
			session.insert("insertEmployee", employee);
			employee = session.selectOne("getId");
			SkillSet skillSet = new SkillSet();
			for (int i = 0; i < skill.length; i++)
			{
				skillSet.setEmpId(employee.getEmployeeId());
				skillSet.setSkillName(skill[i]);
				session.insert("insertSkill", skillSet);
			}
			session.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
	}

	/**
	 * @param employee
	 * @param skills
	 */
	public void updateEmployee(Employee employee, String[] skills, int employeeId)
	{
		String[] skill = skills[0].split(",");
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
			employee.setEmployeeId(employeeId);
			session.update("updateEmployee", employee);
			session.delete("deleteSkill", employee.getEmployeeId());
			SkillSet skillSet;
			for (int i = 0; i < skill.length; i++)
			{
				skillSet = new SkillSet();
				skillSet.setEmpId(employee.getEmployeeId());
				skillSet.setSkillName(skill[i]);
				session.insert("insertSkill", skillSet);
			}
			session.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}

	}

	/**
	 * @param employeeId
	 */
	public void deleteEmployee(int employeeId)
	{
		try
		{
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			session.delete("deleteSkill", employeeId);
			session.delete("deleteEmployee", employeeId);
			session.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public List<Employee> getAllEmployees()
	{
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
			List<Employee> employeesList = session.selectList("getAllEmployees");
			session.commit();
			return employeesList;
		}
		finally
		{
			session.close();
		}
	}

	/**
	 * @param employeeId
	 * @return
	 */
	public Employee getEmployeeById(int employeeId)
	{
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
			Employee employee = (Employee) session.selectOne("getEmployeeById", employeeId);
			List<SkillSet> skillSet = session.selectList("getAllSkills", employeeId);
			StringBuilder skills = new StringBuilder();
			for(int i=0; i<skillSet.size(); i++)
			{
				skills.append(skillSet.get(i).getSkillName()).append(",");
			}
			skills.deleteCharAt(skills.length()-1);
			employee.setSkillSet(new String(skills));
			session.commit();
			return employee;
		}
		finally
		{
			session.close();
		}
	}

}
