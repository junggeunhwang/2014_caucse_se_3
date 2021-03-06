package view;


import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class About extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Icon informationIcon = new ImageIcon("resource\\information.png"); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setType(Type.POPUP);
		setResizable(false);
		setTitle("About Titan");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 231);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitan = new JLabel("Titan");
		lblTitan.setBounds(122, 10, 57, 15);
		contentPane.add(lblTitan);
		
		JLabel lblVersion = new JLabel("Version 1.0");
		lblVersion.setBounds(122, 35, 87, 15);
		contentPane.add(lblVersion);
		
		JLabel lblCopyrightcCaucse = new JLabel("Copyright(c) 2014, CAUCSE \r\nSoftware Engineering Team3");
		lblCopyrightcCaucse.setBounds(12, 165, 331, 28);
		contentPane.add(lblCopyrightcCaucse);
		
		JLabel lblInformationIcon = new JLabel("");
		lblInformationIcon.setBounds(23, 35, 80, 80);
		
		lblInformationIcon.setIcon(informationIcon);
		contentPane.add(lblInformationIcon);
		
		JLabel lblTeamMember = new JLabel("Team Member");
		lblTeamMember.setBounds(122, 60, 108, 15);
		contentPane.add(lblTeamMember);
		
		JLabel label = new JLabel("20101486 \uACBD\uC138\uC900");
		label.setFont(new Font("Gulim", Font.PLAIN, 12));
		label.setBounds(145, 85, 113, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("20101534 \uBC30\uC218\uC5F4");
		label_1.setFont(new Font("Gulim", Font.PLAIN, 12));
		label_1.setBounds(291, 85, 113, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("20101541 \uC2E0\uC6B0\uADFC");
		label_2.setFont(new Font("Gulim", Font.PLAIN, 12));
		label_2.setBounds(145, 110, 113, 15);
		contentPane.add(label_2);
		
		JLabel lblNewLabel = new JLabel("20101571 \uC774\uCC3D\uC218");
		lblNewLabel.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblNewLabel.setBounds(291, 110, 113, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("20101598 \uD669\uC815\uADFC");
		lblNewLabel_1.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(145, 135, 113, 15);
		contentPane.add(lblNewLabel_1);
	}
}
