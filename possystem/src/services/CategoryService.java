package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import models.Category;
import share.mapper.saleMapper;

public class CategoryService {

	private final saleMapper categoryMapper;
	private final DBConfig dbConfig;

	public CategoryService() {
		this.categoryMapper = new saleMapper();
		this.dbConfig = new DBConfig();
	}

	public void createCategory(Category category) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("INSERT INTO category (categoryName) VALUES (?)");

			ps.setString(1, category.getCategoryName());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCategory(String id, Category category) {
		try {
			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("Update category set categoryName=? where category_id =?");

			ps.setString(1, category.getCategoryName());
			ps.setString(2, id);

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			// if(e instanceof MySQLIntegrityConstraintViolationException)
			JOptionPane.showMessageDialog(null, "Already Exits");
		}
	}

	public void deleteCategory(String id) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("delete from category where category_id = '" + id + "'");

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "!!!!!");
		}
	}

	public Category findCategoryId(String id) {

		Category category = new Category();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "Select * from category where category_id = " + id + ""; //
			System.out.println(query);

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.categoryMapper.mapToCategory(category, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	public List<Category> findAllCategories() {

		List<Category> categoryList = new ArrayList<>();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {
			String query = "select * from category";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Category category = new Category();
				categoryList.add(this.categoryMapper.mapToCategory(category, rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryList;
	}
}
