package forms;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.Option;

import config.DBConfig;
import models.Brand;
import services.BrandService;
import share.mapper.saleMapper;

import java.awt.Color;
import java.awt.SystemColor;

public class BrandForm {

	public JFrame frameBrand;
	private JTextField txtName;
	private JTextField txtSearch;
	private JTable tblBrand;
	private Brand brand;
	private BrandService brandService;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Brand> brandList = new ArrayList();
	private List<Brand> filterBrandList = new ArrayList();

	private final DBConfig dbConfig = new DBConfig();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrandForm window = new BrandForm();
					window.frameBrand.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws SQLException
	 */
	public BrandForm() {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllBrands(Optional.empty());
	}

	private void initializeDependency() {
		this.brandService = new BrandService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Brand Name");
		this.tblBrand.setModel(dtm);
	}

	private void loadAllBrands(Optional<List<Brand>> optionalBrand) {

		this.dtm = (DefaultTableModel) this.tblBrand.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.brandList = this.brandService.findAllBrands();

		this.filterBrandList = optionalBrand.orElseGet(() -> this.brandList).stream().collect(Collectors.toList());

		filterBrandList.forEach(e -> {
			Object[] row = new Object[2];
			row[0] = e.getBrand_id();
			row[1] = e.getBrandName();
			dtm.addRow(row);
		});

		this.tblBrand.setModel(dtm);
	}

	private void resetFormData() {
		txtName.setText("");

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameBrand = new JFrame();
		frameBrand.getContentPane().setBackground(SystemColor.activeCaption);
		frameBrand.setTitle("Brand Entry");
		frameBrand.setBounds(100, 100, 857, 500);
		frameBrand.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBrand.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Brand Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(30, 65, 85, 29);
		frameBrand.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(127, 65, 193, 29);
		frameBrand.getContentPane().add(txtName);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setBackground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Brand brand = new Brand();
				brand.setBrandName(txtName.getText());

				if (!brand.getBrandName().isBlank()) {

					brandService.createBrand(brand);
					JOptionPane.showMessageDialog(null, "Save Successfully!!!");
					resetFormData();
					loadAllBrands(Optional.empty());

				} else {
					JOptionPane.showMessageDialog(null, "Enter Required Field");
				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(30, 410, 104, 29);
		frameBrand.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(538, 65, 165, 29);
		frameBrand.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBackground(Color.GRAY);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String search = txtSearch.getText();

				loadAllBrands(Optional.of(brandList.stream()
						.filter(brand -> brand.getBrandName().toLowerCase().startsWith(search.toLowerCase()))
						.collect(Collectors.toList())));

			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(715, 65, 85, 29);
		frameBrand.getContentPane().add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 104, 770, 296);
		frameBrand.getContentPane().add(scrollPane);

		tblBrand = new JTable();
		tblBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblBrand);
		this.tblBrand.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblBrand.getSelectionModel().isSelectionEmpty()) {
				String id = tblBrand.getValueAt(tblBrand.getSelectedRow(), 0).toString();

				brand = brandService.findBrandId(id);
				// System.out.println(employee);

				txtName.setText(brand.getBrandName());

			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				brand.setBrandName(txtName.getText());

				if (!brand.getBrandName().isBlank()) {

					brandService.updateBrand(String.valueOf(brand.getBrand_id()), brand);
					JOptionPane.showMessageDialog(null, "Update Successfully!!!");
					resetFormData();
					loadAllBrands(Optional.empty());
					brand = null;

				}
			}
		});

		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(159, 410, 104, 29);
		frameBrand.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBackground(Color.WHITE);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				brandService.deleteBrand(String.valueOf(brand.getBrand_id()));
				// JOptionPane.showMessageDialog(null, "Delete Successfully!!!");
				resetFormData();
				loadAllBrands(Optional.empty());

				brand = null;

			}

		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(288, 410, 111, 29);
		frameBrand.getContentPane().add(btnDelete);

		JLabel lblNewLabel = new JLabel("Brand Record");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(359, 10, 202, 29);
		frameBrand.getContentPane().add(lblNewLabel);

		JButton btnBack = new JButton("Home");
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frameBrand.dispose();
				SaleForm.main(null);

			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds(423, 410, 91, 29);
		frameBrand.getContentPane().add(btnBack);
	}
}
