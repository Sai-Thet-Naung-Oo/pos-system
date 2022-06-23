package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import models.Supplier;
import share.mapper.saleMapper;

public class SupplierService {

	private final saleMapper supMapper;
	private final DBConfig dbConfig;

	public SupplierService() {
		this.supMapper = new saleMapper();
		this.dbConfig = new DBConfig();
	}

	public void createSupplier(Supplier sup) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"INSERT INTO supplier (sup_name,sup_phone,sup_email,sup_address) VALUES (?,?,?,?)");

			ps.setString(1, sup.getSup_name());
			ps.setString(2, sup.getSup_phone());
			ps.setString(3, sup.getSup_email());
			ps.setString(4, sup.getSup_address());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSupplier(String id, Supplier sup) {
		try {
			PreparedStatement ps = this.dbConfig.getConnection().prepareStatement(
					"Update supplier set sup_name=?,sup_phone=?,sup_email=?,sup_address=? where sup_id =?");

			ps.setString(1, sup.getSup_name());
			ps.setString(2, sup.getSup_phone());
			ps.setString(3, sup.getSup_email());
			ps.setString(4, sup.getSup_address());
			ps.setString(5, id);

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Already Exits");
		}
	}

	public void deleteSupplier(String id) {
		try {

			PreparedStatement ps = this.dbConfig.getConnection()
					.prepareStatement("delete from supplier where sup_id = '" + id + "'");

			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "!!!!!");
		}
	}

	public Supplier findSupplierId(String id) {
		Supplier sup = new Supplier();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {

			String query = "Select * from supplier where sup_id = " + id + ""; //
			System.out.println(query);

			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				this.supMapper.mapToSupplier(sup, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sup;
	}

	public List<Supplier> findAllSuppliers() {
		List<Supplier> supList = new ArrayList<>();

		try (Statement st = this.dbConfig.getConnection().createStatement()) {
			String query = "select * from supplier";

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Supplier sup = new Supplier();
				supList.add(this.supMapper.mapToSupplier(sup, rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supList;
	}
}
