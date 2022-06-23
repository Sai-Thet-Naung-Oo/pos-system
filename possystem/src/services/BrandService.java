package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import models.Brand;
import share.mapper.saleMapper;

public class BrandService {

	private final saleMapper brandMapper;
	private final DBConfig dbConfig;

	public BrandService() {
		this.brandMapper = new saleMapper();
		this.dbConfig = new DBConfig();
	}

	public void createBrand(Brand brand) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("INSERT INTO brand (brandName) VALUES (?)");

			ps.setString(1, brand.getBrandName());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateBrand(String id, Brand brand) {
		try {
			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("Update brand set brandName=? where brand_id =?");

			ps.setString(1, brand.getBrandName());
			ps.setString(2, id);

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			// if(e instanceof MySQLIntegrityConstraintViolationException)
			JOptionPane.showMessageDialog(null, "Already Exits");
		}
	}

	public void deleteBrand(String id) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("delete from brand where brand_id = '" + id + "'");

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "!!!!!");
		}
	}

	public Brand findBrandId(String id) {
		Brand brand = new Brand();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "Select * from brand where brand_id = " + id + ""; //
			System.out.println(query);

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.brandMapper.mapToBrand(brand, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return brand;
	}

	public List<Brand> findAllBrands() {

		List<Brand> brandList = new ArrayList<>();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {
			String query = "select * from brand";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Brand brand = new Brand();
				brandList.add(this.brandMapper.mapToBrand(brand, rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return brandList;
	}
}
