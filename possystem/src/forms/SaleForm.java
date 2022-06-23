package forms;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class SaleForm {

	private JFrame frameSale;
	private ImageIcon image1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaleForm window = new SaleForm();
					window.frameSale.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SaleForm() {
		initialize();
		// imageTest();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	/*
	 * private void imageTest() { frame.setLayout(new FlowLayout()); image1 = new
	 * ImageIcon(getClass().getResource("/images/cat.jpg"));
	 * 
	 * label1 = new JLabel(image1); frame.add(label1); }
	 */
	private void initialize() {
		frameSale = new JFrame();
		frameSale.setTitle("Sale System");
		frameSale.setBounds(100, 100, 472, 445);
		frameSale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		image1 = new ImageIcon(getClass().getResource("/images/cat.jpg"));
		frameSale.getContentPane().setLayout(null);

		JButton btnbrand = new JButton("Brand");
		btnbrand.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnbrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frameSale.dispose();
				
				BrandForm.main(null);

			}
		});
		btnbrand.setBounds(114, 224, 91, 51);
		frameSale.getContentPane().add(btnbrand);

		JButton btnCategory = new JButton("Category");
		btnCategory.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameSale.dispose();

				CategoryForm.main(null);
			}

		});
		btnCategory.setBounds(246, 229, 91, 46);
		frameSale.getContentPane().add(btnCategory);

		JButton btnSupplier = new JButton("Supplier");
		btnSupplier.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frameSale.dispose();

				SupplierForm.main(null);
			}
		});
		btnSupplier.setBounds(246, 86, 91, 46);
		frameSale.getContentPane().add(btnSupplier);

		JLabel lblNewLabel = new JLabel("Sale Management System");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblNewLabel.setBounds(64, 0, 229, 23);
		frameSale.getContentPane().add(lblNewLabel);

		JButton btnemployee = new JButton("Employee");
		btnemployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frameSale.dispose();
				EmployeeForm.main(null);
			}
		});
		btnemployee.setBounds(116, 82, 89, 56);
		frameSale.getContentPane().add(btnemployee);

		JLabel lblNewLabel_1 = new JLabel("Item");
		lblNewLabel_1.setBounds(27, 254, 46, 14);
		frameSale.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("People");
		lblNewLabel_2.setBounds(27, 103, 46, 14);
		frameSale.getContentPane().add(lblNewLabel_2);

	}

	public void show() {
		frameSale.show();
	}
}
