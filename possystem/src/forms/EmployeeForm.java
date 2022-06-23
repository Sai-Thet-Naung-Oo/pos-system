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
import models.Employee;
import services.EmployeeService;

import java.awt.SystemColor;

public class EmployeeForm {

	public JFrame frameemp;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTextField txtSearch;
	private JTable tblEmp;
	private Employee emp;
	private EmployeeService empService;
	private DefaultTableModel dtm = new DefaultTableModel();
	private final DBConfig dbConfig = new DBConfig();
	private List<Employee> empList = new ArrayList();
	private List<Employee> filterempList = new ArrayList();
	private JTextField txtusername;
	private JTextField txtpassword;
	private JTextField txtrole;
	private JTextField txtactive;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeForm window = new EmployeeForm();
					window.frameemp.setVisible(true);
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
	public EmployeeForm() {
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllEmployees(Optional.empty());
	}

	private void initializeDependency() {
		this.empService = new EmployeeService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Phone No");
		dtm.addColumn("Email");
		dtm.addColumn("Address");
		dtm.addColumn("Username");
		dtm.addColumn("Password");
		dtm.addColumn("Role");
		this.tblEmp.setModel(dtm);
	}

	private void loadAllEmployees(Optional<List<Employee>> optionalemp) {
		this.dtm = (DefaultTableModel) this.tblEmp.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.empList = this.empService.findAllEmployees();

		this.filterempList = optionalemp.orElseGet(() -> this.empList).stream().collect(Collectors.toList());

		filterempList.forEach(e -> {
			Object[] row = new Object[8];
			row[0] = e.getId();
			row[1] = e.getEmp_name();
			row[2] = e.getEmp_phone();
			row[3] = e.getEmp_email();
			row[4] = e.getEmp_address();
			row[5] = e.getUsername();
			row[6] = e.getPassword();
			row[7] = e.getRole();
			dtm.addRow(row);
		});

		this.tblEmp.setModel(dtm);
	}

	private void resetFormData() {
		txtName.setText("");
		txtPhone.setText("");
		txtEmail.setText("");
		txtAddress.setText("");
		txtusername.setText("");
		txtpassword.setText("");
		txtrole.setText("");
		txtactive.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameemp = new JFrame();
		frameemp.getContentPane().setBackground(SystemColor.activeCaption);
		frameemp.setTitle("empplier Entry");
		frameemp.setBounds(100, 50, 763, 674);
		frameemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameemp.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(47, 39, 74, 29);
		frameemp.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(125, 39, 193, 29);
		frameemp.getContentPane().add(txtName);

		JLabel lblPhone = new JLabel("Phone No");
		lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone.setBounds(47, 78, 74, 29);
		frameemp.getContentPane().add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPhone.setColumns(10);
		txtPhone.setBounds(125, 78, 193, 29);
		frameemp.getContentPane().add(txtPhone);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setBackground(SystemColor.inactiveCaptionBorder);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee emp = new Employee();
				emp.setEmp_name(txtName.getText());
				emp.setEmp_phone(txtPhone.getText());
				emp.setEmp_email(txtEmail.getText());
				emp.setEmp_address(txtAddress.getText());
				emp.setUsername(txtusername.getText());
				emp.setPassword(txtpassword.getText());
				emp.setRole(txtrole.getText());
				emp.setActive(Integer.parseInt(txtactive.getText()));

				if (!emp.getEmp_name().isBlank() && !emp.getEmp_phone().isBlank() && !emp.getEmp_email().isBlank()
						&& !emp.getEmp_address().isBlank() && !emp.getUsername().isBlank()
						&& !emp.getPassword().isBlank() && !emp.getRole().isBlank()
						&& !String.valueOf(emp.getActive()).isBlank()) {

					empService.createEmployee(emp);
					JOptionPane.showMessageDialog(null, "Save Successfully!!!");
					resetFormData();
					loadAllEmployees(Optional.empty());

				} else {
					JOptionPane.showMessageDialog(null, "Enter Required Field");
				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(47, 595, 95, 29);
		frameemp.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(446, 330, 165, 29);
		frameemp.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBackground(SystemColor.inactiveCaptionBorder);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String search = txtSearch.getText();

				loadAllEmployees(Optional.of(empList.stream()
						.filter(emp -> (emp.getEmp_name().toLowerCase().startsWith(search.toLowerCase()))
								|| (emp.getEmp_phone().equals(search)))
						.collect(Collectors.toList())));

			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(624, 330, 85, 29);
		frameemp.getContentPane().add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 381, 662, 203);
		frameemp.getContentPane().add(scrollPane);

		tblEmp = new JTable();
		tblEmp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblEmp);
		this.tblEmp.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblEmp.getSelectionModel().isSelectionEmpty()) {
				String id = tblEmp.getValueAt(tblEmp.getSelectedRow(), 0).toString();

				emp = empService.findEmployeeByid(id);
				// System.out.println(employee);

				txtName.setText(emp.getEmp_name());
				txtPhone.setText(emp.getEmp_phone());
				txtEmail.setText(emp.getEmp_email());
				txtAddress.setText(emp.getEmp_address());
				txtusername.setText(emp.getUsername());
				txtpassword.setText(emp.getPassword());
				txtrole.setText(emp.getRole());
				txtactive.setText(emp.getActive() + "");

			}
		});

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(47, 156, 85, 29);
		frameemp.getContentPane().add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAddress.setColumns(10);
		txtAddress.setBounds(125, 156, 193, 29);
		frameemp.getContentPane().add(txtAddress);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBackground(SystemColor.inactiveCaptionBorder);
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				emp.setEmp_name(txtName.getText());
				emp.setEmp_phone(txtPhone.getText());
				emp.setEmp_email(txtEmail.getText());
				emp.setEmp_address(txtAddress.getText());
				emp.setUsername(txtusername.getText());
				emp.setPassword(txtpassword.getText());
				emp.setRole(txtrole.getText());
				emp.setActive(Integer.parseInt(txtactive.getText()));

				if (!emp.getEmp_name().isBlank() && !emp.getEmp_phone().isBlank() && !emp.getEmp_email().isBlank()
						&& !emp.getEmp_address().isBlank() && !emp.getUsername().isBlank()
						&& !emp.getPassword().isBlank() && !emp.getRole().isBlank()
						&& !String.valueOf(emp.getActive()).isBlank()) {

					empService.updateEmployee(String.valueOf(emp.getId()), emp);

					JOptionPane.showMessageDialog(null, "Update Successfully!!!");
					resetFormData();
					loadAllEmployees(Optional.empty());
					emp = null;

				}

			}
		});

		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(164, 595, 95, 29);
		frameemp.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBackground(SystemColor.inactiveCaptionBorder);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				empService.deleteEmployee(String.valueOf(emp.getId()));
				
				resetFormData();
				loadAllEmployees(Optional.empty());
				emp = null;
			}

		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(283, 595, 95, 29);
		frameemp.getContentPane().add(btnDelete);

		JLabel lblNewLabel = new JLabel("Employee Record");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(324, 10, 202, 29);
		frameemp.getContentPane().add(lblNewLabel);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(47, 117, 74, 29);
		frameemp.getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(125, 117, 193, 29);
		frameemp.getContentPane().add(txtEmail);

		JButton btnBeck = new JButton("Home");
		btnBeck.setBackground(SystemColor.inactiveCaptionBorder);
		btnBeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameemp.dispose();
				SaleForm.main(null);
			}
		});
		btnBeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBeck.setBounds(413, 595, 91, 29);
		frameemp.getContentPane().add(btnBeck);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(47, 196, 85, 29);
		frameemp.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(47, 236, 85, 29);
		frameemp.getContentPane().add(lblPassword);

		JLabel lblRole = new JLabel("Role");
		lblRole.setHorizontalAlignment(SwingConstants.LEFT);
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRole.setBounds(47, 276, 85, 29);
		frameemp.getContentPane().add(lblRole);

		txtusername = new JTextField();
		txtusername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtusername.setColumns(10);
		txtusername.setBounds(125, 196, 193, 29);
		frameemp.getContentPane().add(txtusername);

		txtpassword = new JTextField();
		txtpassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpassword.setColumns(10);
		txtpassword.setBounds(125, 236, 193, 29);
		frameemp.getContentPane().add(txtpassword);

		txtrole = new JTextField();
		txtrole.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtrole.setColumns(10);
		txtrole.setBounds(125, 276, 193, 29);
		frameemp.getContentPane().add(txtrole);

		JLabel lblActive = new JLabel("Active");
		lblActive.setHorizontalAlignment(SwingConstants.LEFT);
		lblActive.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblActive.setBounds(47, 316, 85, 29);
		frameemp.getContentPane().add(lblActive);

		txtactive = new JTextField();
		txtactive.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtactive.setColumns(10);
		txtactive.setBounds(125, 316, 193, 29);
		frameemp.getContentPane().add(txtactive);
	}
}
