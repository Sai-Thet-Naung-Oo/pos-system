package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import models.Employee;

import share.mapper.saleMapper;

public class EmployeeService {

	private final saleMapper supMapper;
	private final DBConfig dbConfig;

	public EmployeeService() {
		this.supMapper = new saleMapper();
		this.dbConfig = new DBConfig();
	}

	public void createEmployee(Employee emp) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"INSERT INTO employee (emp_name,emp_phone,emp_email,emp_address,username,password,role,active) VALUES (?,?,?,?,?,?,?,?)");

			ps.setString(1, emp.getEmp_name());
			ps.setString(2, emp.getEmp_phone());
			ps.setString(3, emp.getEmp_email());
			ps.setString(4, emp.getEmp_address());
			ps.setString(5, emp.getUsername());
			ps.setString(6, emp.getPassword());
			ps.setString(7, emp.getRole());
			ps.setInt(8, emp.getActive());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateEmployee(String id, Employee emp) {
		try {
			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"Update employee set emp_name=?,emp_phone=?,emp_email=?,emp_address=?,username=?,password=?,role=?,active=? where emp_id =?");

			ps.setString(1, emp.getEmp_name());
			ps.setString(2, emp.getEmp_phone());
			ps.setString(3, emp.getEmp_email());
			ps.setString(4, emp.getEmp_address());
			ps.setString(5, emp.getUsername());
			ps.setString(6, emp.getPassword());
			ps.setString(7, emp.getRole());
			ps.setInt(8, emp.getActive());
			ps.setInt(9, emp.getId());

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Already Exits");
		}
	}

	public void deleteEmployee(String id) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("update employee set  active=0 where emp_id = '" + id + "'");

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "!!!!!");
		}
	}

	public Employee findEmployeeByid(String id) {
		Employee emp = new Employee();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "Select * from employee where emp_id =" + id + " and active=1 "; //
			System.out.println(query);

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.supMapper.mapToEmployee(emp, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	public List<Employee> findAllEmployees() {
		List<Employee> empList = new ArrayList<>();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {
			String query = "select * from employee where active=1";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Employee emp = new Employee();
				empList.add(this.supMapper.mapToEmployee(emp, rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
}
