package forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import config.DBConfig;
import models.Supplier;
import services.SupplierService;

import java.awt.SystemColor;

public class SupplierForm {

	public JFrame frameSup;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTextField txtSearch;
	private JTable tblSup;
	private Supplier sup;
	private SupplierService supService;
	private DefaultTableModel dtm = new DefaultTableModel();
	private final DBConfig dbConfig = new DBConfig();
	private List<Supplier> supList = new ArrayList();
	private List<Supplier> filterSupList = new ArrayList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierForm window = new SupplierForm();
					window.frameSup.setVisible(true);
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
	public SupplierForm()  {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllSuppliers(Optional.empty());
	}

	private void initializeDependency() {
			this.supService = new SupplierService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Phone No");
		dtm.addColumn("Email");
		dtm.addColumn("Address");
		this.tblSup.setModel(dtm);
	}

	private void loadAllSuppliers(Optional<List<Supplier>> optionalSup) {
		this.dtm = (DefaultTableModel) this.tblSup.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.supList = this.supService.findAllSuppliers();

		this.filterSupList = optionalSup.orElseGet(() -> this.supList).stream().collect(Collectors.toList());

		filterSupList.forEach(e -> {
			Object[] row = new Object[5];
			row[0] = e.getSup_id();
			row[1] = e.getSup_name();
			row[2] = e.getSup_phone();
			row[3] = e.getSup_email();
			row[4] = e.getSup_address();
			dtm.addRow(row);
		});

		this.tblSup.setModel(dtm);
	}

	private void resetFormData() {
		txtName.setText("");
		txtPhone.setText("");
		txtEmail.setText("");
		txtAddress.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameSup = new JFrame();
		frameSup.getContentPane().setBackground(SystemColor.activeCaption);
		frameSup.setTitle("Supplier Entry");
		frameSup.setBounds(100, 100, 763, 482);
		frameSup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSup.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(47, 39, 74, 29);
		frameSup.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(125, 39, 193, 29);
		frameSup.getContentPane().add(txtName);

		JLabel lblPhone = new JLabel("Phone No");
		lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone.setBounds(47, 78, 74, 29);
		frameSup.getContentPane().add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPhone.setColumns(10);
		txtPhone.setBounds(125, 78, 193, 29);
		frameSup.getContentPane().add(txtPhone);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setBackground(SystemColor.inactiveCaptionBorder);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Supplier sup = new Supplier();
				sup.setSup_name(txtName.getText());
				sup.setSup_phone(txtPhone.getText());
				sup.setSup_email(txtEmail.getText());
				sup.setSup_address(txtAddress.getText());

				if (!sup.getSup_name().isBlank() && !sup.getSup_phone().isBlank() && !sup.getSup_email().isBlank() && !sup.getSup_address().isBlank()) {

					supService.createSupplier(sup);
					JOptionPane.showMessageDialog(null, "Save Successfully!!!");
					resetFormData();
					loadAllSuppliers(Optional.empty());

				} else {
					JOptionPane.showMessageDialog(null, "Enter Required Field");
				}


			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(43, 408, 95, 29);
		frameSup.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(450, 156, 165, 29);
		frameSup.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBackground(SystemColor.inactiveCaptionBorder);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String search = txtSearch.getText();

				loadAllSuppliers(Optional.of(supList.stream()
						.filter(sup -> (sup.getSup_name().toLowerCase().startsWith(search.toLowerCase())) ||(sup.getSup_phone().equals(search)))
						.collect(Collectors.toList())));

			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(620, 156, 85, 29);
		frameSup.getContentPane().add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 195, 662, 203);
		frameSup.getContentPane().add(scrollPane);

		tblSup = new JTable();
		tblSup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblSup);
		this.tblSup.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblSup.getSelectionModel().isSelectionEmpty()) {
				String id = tblSup.getValueAt(tblSup.getSelectedRow(), 0).toString();

				sup = supService.findSupplierId(id);
				// System.out.println(employee);

				txtName.setText(sup.getSup_name());
				txtPhone.setText(sup.getSup_phone());
				txtEmail.setText(sup.getSup_email());
				txtAddress.setText(sup.getSup_address());

			}
		});

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(47, 156, 85, 29);
		frameSup.getContentPane().add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAddress.setColumns(10);
		txtAddress.setBounds(125, 156, 193, 29);
		frameSup.getContentPane().add(txtAddress);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBackground(SystemColor.inactiveCaptionBorder);
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				sup.setSup_name(txtName.getText());
				sup.setSup_phone(txtPhone.getText());
				sup.setSup_email(txtEmail.getText());
				sup.setSup_address(txtAddress.getText());

				if (!sup.getSup_name().isBlank() && !sup.getSup_phone().isBlank() && !sup.getSup_email().isBlank() && !sup.getSup_address().isBlank()) {

					supService.updateSupplier(String.valueOf(sup.getSup_id()), sup);
					
					
					JOptionPane.showMessageDialog(null, "Update Successfully!!!");
					resetFormData();
					loadAllSuppliers(Optional.empty());
					sup = null;

				}

			}
		});

		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(167, 408, 95, 29);
		frameSup.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBackground(SystemColor.inactiveCaptionBorder);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				supService.deleteSupplier(String.valueOf(sup.getSup_id()));
				//JOptionPane.showMessageDialog(null, "Update Successfully!!!");
				resetFormData();
				loadAllSuppliers(Optional.empty());
				sup = null;
			}

		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(296, 408, 95, 29);
		frameSup.getContentPane().add(btnDelete);

		JLabel lblNewLabel = new JLabel("Supplier Record");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(324, 10, 202, 29);
		frameSup.getContentPane().add(lblNewLabel);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(47, 117, 74, 29);
		frameSup.getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(125, 117, 193, 29);
		frameSup.getContentPane().add(txtEmail);
		
		JButton btnBeck = new JButton("Home");
		btnBeck.setBackground(SystemColor.inactiveCaptionBorder);
		btnBeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameSup.dispose();
				SaleForm.main(null);
			}
		});
		btnBeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBeck.setBounds(424, 408, 91, 29);
		frameSup.getContentPane().add(btnBeck);
	}
}
