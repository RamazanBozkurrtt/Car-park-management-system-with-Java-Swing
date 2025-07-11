package carparkManagement_View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import carparkManagement_SQLTransactions.carparkM_SQLManagement;
import carparkManagement_SQLTransactions.carparkM_SQLTransactions_MySQL;

public class carpark_LoginScreen extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private final static int FRAME_WİTDH=350,FRAME_HEİGHT=500;
	private Color exitedColor;
	private int mouseX,mouseY;
	private JTextField textField;
	private JPasswordField passwordField;
	private boolean hideShowKey;
	private final JLabel lblNewLabel_5 = new JLabel("Otopark Yönetim");
	private JButton btnNewButton;
	private carparkM_SQLManagement cM_SQLManagement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					carpark_LoginScreen frame = new carpark_LoginScreen();
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
	public carpark_LoginScreen() {
		setHideShowKey(false);
		exitedColor = new Color(46,46,46);
		cM_SQLManagement = new carparkM_SQLManagement(new carparkM_SQLTransactions_MySQL());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(490, 130, FRAME_WİTDH, FRAME_HEİGHT);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(91, 91, 91));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setUndecorated(true);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(getX()+e.getX()-mouseX,getY()+e.getY()-mouseY);
				
				
			}
		});
		panel.setBackground(new Color(46, 46, 46));
		panel.setBounds(0, 0, 350, 28);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_1.setBackground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_1.setBackground(exitedColor);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		panel_1.setBackground(new Color(46, 46, 46));
		panel_1.setBounds(300, 0, 50, 28);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_1.setBackground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_1.setBackground(exitedColor);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		lblNewLabel.setBounds(15, 0, 35, 28);
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("icons\\closeWhiteSmall.png"));
		
		textField = new JTextField();
		textField.setBounds(82, 199, 186, 40);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnNewButton.doClick();
				}
			}
		});
		passwordField.setBounds(82, 300, 186, 40);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("icons\\logo2.png"));
		lblNewLabel_1.setBounds(112, 78, 172, 99);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(":");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setIcon(new ImageIcon("icons\\login.png"));
		lblNewLabel_2.setBounds(19, 197, 55, 40);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(":");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setIcon(new ImageIcon("icons\\key.png"));
		lblNewLabel_3.setBounds(19, 300, 55, 40);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Hided");
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isHideShowKey()) {
					lblNewLabel_4.setIcon(new ImageIcon("icons\\hide.png"));
					passwordField.setEchoChar('*');
					lblNewLabel_4.setText("Hided");
					setHideShowKey(false);
				}
				else {
					lblNewLabel_4.setIcon(new ImageIcon("icons\\show.png"));
					passwordField.setEchoChar((char) 0);
					lblNewLabel_4.setText("Shown");
					setHideShowKey(true);
				}
			}
		});
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setIcon(new ImageIcon("icons\\hide.png"));
		lblNewLabel_4.setBounds(275, 300, 75, 40);
		contentPane.add(lblNewLabel_4);
		
		btnNewButton = new JButton("Giriş");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = new String(passwordField.getPassword());
				
				if(cM_SQLManagement.adminLogin(textField.getText(), pass)) {
					dispose();
					carpark_ControlScreen.main(null);
					
				}else {
					JOptionPane.showMessageDialog(null, "Kullanıcı Adı Veya Şifre Yanlış", "Hata", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnNewButton.setBounds(115, 400, 120, 32);
		contentPane.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(68, 68, 68));
		panel_2.setBounds(0, 25, 350, 42);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setFont(new Font("Ink Free", Font.BOLD, 25));
		lblNewLabel_5.setBounds(80, 0, 198, 42);
		panel_2.add(lblNewLabel_5);
	}
	
	private boolean isHideShowKey() {
		return hideShowKey;
	}

	private void setHideShowKey(boolean hideShowKey) {
		this.hideShowKey = hideShowKey;
	}

}
