package forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
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
import models.Category;
import services.CategoryService;

public class CategoryForm {

	public JFrame frameCategory;
	private JTextField txtName;
	private JTextField txtSearch;
	private JTable tblCategory;
	private Category category;
	private CategoryService categoryService;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<Category> categoryList = new ArrayList();
	private List<Category> filterCategoryList = new ArrayList();

	private final DBConfig dbConfig = new DBConfig();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryForm window = new CategoryForm();
					window.frameCategory.setVisible(true);
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
	 * @wbp.parser.entryPoint
	 */
	public CategoryForm(){
		initialize();
		this.initializeDependency();
		this.setTableDesign();
		this.loadAllCategories(Optional.empty());
	}

	private void initializeDependency() {
		this.categoryService = new CategoryService();
	}

	private void setTableDesign() {
		dtm.addColumn("ID");
		dtm.addColumn("Category Name");
		this.tblCategory.setModel(dtm);
	}

	private void loadAllCategories(Optional<List<Category>> optionalCategory) {

		this.dtm = (DefaultTableModel) this.tblCategory.getModel();
		this.dtm.getDataVector().removeAllElements();
		this.dtm.fireTableDataChanged();

		this.categoryList = this.categoryService.findAllCategories();

		this.filterCategoryList = optionalCategory.orElseGet(() -> this.categoryList).stream().collect(Collectors.toList());

		filterCategoryList.forEach(e -> {
			Object[] row = new Object[2];
			row[0] = e.getCategory_id();
			row[1] = e.getCategoryName();
			dtm.addRow(row);
		});

		this.tblCategory.setModel(dtm);
	}

	private void resetFormData() {
		txtName.setText("");

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameCategory = new JFrame();
		frameCategory.getContentPane().setBackground(SystemColor.activeCaption);
		frameCategory.setTitle("Category Entry");
		frameCategory.setBounds(100, 100, 857, 500);
		frameCategory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameCategory.getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Category Name");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(42, 65, 118, 29);
		frameCategory.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(162, 65, 193, 29);
		frameCategory.getContentPane().add(txtName);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setBackground(SystemColor.inactiveCaptionBorder);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Category category = new Category();
				category.setCategoryName(txtName.getText());

				if (!category.getCategoryName().isBlank()) {

					categoryService.createCategory(category);
					JOptionPane.showMessageDialog(null, "Save Successfully!!!");
					resetFormData();
					loadAllCategories(Optional.empty());

				} else {
					JOptionPane.showMessageDialog(null, "Enter Required Field");
				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(30, 410, 104, 29);
		frameCategory.getContentPane().add(btnSave);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("");
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.setColumns(10);
		txtSearch.setBounds(538, 65, 165, 29);
		frameCategory.getContentPane().add(txtSearch);

		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBackground(SystemColor.inactiveCaptionBorder);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String search = txtSearch.getText();

				loadAllCategories(Optional.of(categoryList.stream()
						.filter(category -> category.getCategoryName().toLowerCase().startsWith(search.toLowerCase()))
						.collect(Collectors.toList())));

			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(715, 65, 85, 29);
		frameCategory.getContentPane().add(btnSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 104, 770, 296);
		frameCategory.getContentPane().add(scrollPane);

		tblCategory = new JTable();
		tblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblCategory);
		this.tblCategory.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
			if (!tblCategory.getSelectionModel().isSelectionEmpty()) {
				String id = tblCategory.getValueAt(tblCategory.getSelectedRow(), 0).toString();

				category = categoryService.findCategoryId(id);
				// System.out.println(employee);

				txtName.setText(category.getCategoryName());

			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBackground(SystemColor.inactiveCaptionBorder);
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				category.setCategoryName(txtName.getText());

				if (!category.getCategoryName().isBlank()) {

					categoryService.updateCategory(String.valueOf(category.getCategory_id()), category);
					JOptionPane.showMessageDialog(null, "Update Successfully!!!");
					resetFormData();
					loadAllCategories(Optional.empty());
					category = null;

				}
			}
		});

		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(159, 410, 104, 29);
		frameCategory.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBackground(SystemColor.inactiveCaptionBorder);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				categoryService.deleteCategory(String.valueOf(category.getCategory_id()));
				//JOptionPane.showMessageDialog(null, "Delete Successfully!!!");
				resetFormData();
				loadAllCategories(Optional.empty());
				category = null;

			}

		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(288, 410, 111, 29);
		frameCategory.getContentPane().add(btnDelete);

		JLabel lblNewLabel = new JLabel("Category Record");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(359, 10, 202, 29);
		frameCategory.getContentPane().add(lblNewLabel);
		
		JButton btnBack = new JButton("Home");
		btnBack.setBackground(SystemColor.inactiveCaptionBorder);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCategory.dispose();
				SaleForm.main(null);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds(435, 410, 91, 29);
		frameCategory.getContentPane().add(btnBack);
	}
}
