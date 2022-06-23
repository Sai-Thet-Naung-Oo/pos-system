package share.mapper;

import java.sql.ResultSet;

import models.Brand;
import models.Category;
import models.Employee;
import models.Supplier;

public class saleMapper {

	public Brand mapToBrand(Brand brand, ResultSet rs) {
		try {
			brand.setBrand_id(rs.getInt("brand_id"));
			brand.setBrandName(rs.getString("brandName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return brand;
	}

	public Supplier mapToSupplier(Supplier supplier, ResultSet rs) {
		try {
			supplier.setSup_id(rs.getInt("sup_id"));
			supplier.setSup_name(rs.getString("sup_name"));
			supplier.setSup_phone(rs.getString("sup_phone"));
			supplier.setSup_email(rs.getString("sup_email"));
			supplier.setSup_address(rs.getString("sup_address"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplier;
	}

	public Category mapToCategory(Category category, ResultSet rs) {
		try {
			category.setCategory_id(rs.getInt("category_id"));
			category.setCategoryName(rs.getString("categoryName"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	public Employee mapToEmployee(Employee employee, ResultSet rs) {
		try {
			employee.setId(rs.getInt("emp_id"));
			employee.setEmp_name(rs.getString("emp_name"));
			employee.setEmp_phone(rs.getString("emp_phone"));
			employee.setEmp_email(rs.getString("emp_email"));
			employee.setEmp_address(rs.getString("emp_address"));
			employee.setUsername(rs.getString("username"));
			employee.setPassword(rs.getString("password"));
			employee.setRole(rs.getString("role"));
			employee.setActive(rs.getInt("active"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

}
