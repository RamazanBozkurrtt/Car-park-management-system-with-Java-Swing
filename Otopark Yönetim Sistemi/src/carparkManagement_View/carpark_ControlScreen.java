package carparkManagement_View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import carparkManagement_SQLTransactions.carparkM_SQLManagement;
import carparkManagement_SQLTransactions.carparkM_SQLTransactions_MySQL;
import carparkManagement_items.carpark_item_FloorButtons;
import carparkManagement_items.carpark_item_parkPlaces;

import java.awt.Color;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Panel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;



public class carpark_ControlScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WİDTH=1100,FRAME_HEİGHT=700;
	private static final int floors = 5;
	private static final float ratePerHour=15;
	private static JPanel contentPane;
	private static int floorNumber;
	private int mouseX,mouseY;
	private boolean isEnable;
	private carpark_item_FloorButtons[] floorButtons;
	private static carparkM_SQLManagement cpM_SQLManagement;
	private static ArrayList<carpark_item_parkPlaces> parkPlacesArrayList;
	private static JTextField phoneNumber_TextField;
	private static JTextField plateNumber_TextField;
	private static JTextField enterDate_TextField;
	private static JButton exit_Button;
	private static JButton openBarrier_Button;
	private static JButton newEntry_Button;
	private static JButton save_Button;
	private static int ID;
	private static JLabel currentParkPlaceName_Label;
	private boolean isBarrierOpen;
	
	

	/**
	 * Launch the application.
	 * 
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					carpark_ControlScreen frame = new carpark_ControlScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void set_CurrentParkPlaceLabelText(String parkPlaceName){
		currentParkPlaceName_Label.setText("      "+parkPlaceName);
	}
	
	
	
	private void calculateFee() {
		double totalFee=0;
		double totalHour=0;
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timestamp = System.currentTimeMillis();
		String _dateString = _sdf.format(new Date(timestamp));
		
		//Calculate Day
		if(Integer.parseInt(_dateString.substring(8,10))>Integer.parseInt(enterDate_TextField.getText().substring(8,10))) {
			
			totalHour = (Integer.parseInt(_dateString.substring(8,10))-Integer.parseInt(enterDate_TextField.getText().substring(8,10)))*24;
		}
		
		//Calculate Hour
		if(Integer.parseInt(_dateString.substring(11,13))>Integer.parseInt(enterDate_TextField.getText().substring(11,13))) {
			totalHour = totalHour + (Integer.parseInt(_dateString.substring(11,13))-Integer.parseInt(enterDate_TextField.getText().substring(11,13)));
			
		}else if(Integer.parseInt(_dateString.substring(11,13))<Integer.parseInt(enterDate_TextField.getText().substring(11,13))) {
			totalHour = totalHour - (Integer.parseInt(enterDate_TextField.getText().substring(11,13))-Integer.parseInt(_dateString.substring(11,13)));
		}
		
		//Calculate minute
		if(Float.valueOf(_dateString.substring(14,16))>Float.valueOf(enterDate_TextField.getText().substring(14,16))) {
			totalHour = totalHour + (((Float.valueOf(_dateString.substring(14,16))-Float.valueOf(enterDate_TextField.getText().substring(14,16)))/60));
			
		}else if(Float.valueOf(_dateString.substring(14,16))<Float.valueOf(enterDate_TextField.getText().substring(14,16))) {
			totalHour = totalHour - (((Float.valueOf(enterDate_TextField.getText().substring(14,16))-Float.valueOf(_dateString.substring(14,16)))/60));
		}
		
		totalFee = (double)totalHour*ratePerHour;
		
		DecimalFormat df = new DecimalFormat("#.##");
        String formatted_TotalFee = df.format(totalFee);
		
		int i = JOptionPane.showInternalConfirmDialog(null, "Toplam Ücret = "+formatted_TotalFee+" | Ödeme yapıldı mı ?", "Toplam Ücret", JOptionPane.WARNING_MESSAGE);

		if(i ==JOptionPane.YES_OPTION) {
			cpM_SQLManagement.updateParkPlace(ID);
			JOptionPane.showInternalMessageDialog(null, "İşlem Başarılı !","Başarılı !",JOptionPane.INFORMATION_MESSAGE);
			setParkPlaces(floorNumber);
		}else if(i ==JOptionPane.NO_OPTION) {
			JOptionPane.showInternalMessageDialog(null, "İşlem iptal Edildi !","İptal !",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void updateButtons(boolean _isFull) {
		if(!_isFull) {
			exit_Button.setEnabled(false);
			newEntry_Button.setEnabled(true);
		}else {
			exit_Button.setEnabled(true);
			newEntry_Button.setEnabled(false);
		}
		
		if(!newEntry_Button.isEnabled()) {
			save_Button.setEnabled(false);
		}
	}
	
	private void setFloorButtons() {
		floorButtons = new carpark_item_FloorButtons[floors];
		int buttonX = 0;
		int buttonY = 27;
		for(int i = 0; i<floorButtons.length;i++) {
			floorButtons[i] = new carpark_item_FloorButtons(i+1,buttonX,buttonY);
			contentPane.add(floorButtons[i]);
			buttonX +=150;
			
		}
		
		
	}
	public static void setParkPlaces(int _floorNumber) {
		floorNumber = _floorNumber;
		try {
			if(parkPlacesArrayList.get(2) != null) {
				for(int i = 0 ;i<10 ;i++) {
					contentPane.remove(parkPlacesArrayList.get(i));
				}
			}
		} catch (NullPointerException e) {
		}
		
		cpM_SQLManagement = new carparkM_SQLManagement(new carparkM_SQLTransactions_MySQL());
		parkPlacesArrayList = cpM_SQLManagement.loadParkPlaces(_floorNumber);
		for(int i = 0 ;i<10 ;i++) {
			contentPane.add(parkPlacesArrayList.get(i));
			parkPlacesArrayList.get(i).repaint();
		}
		
		updateScreen();
	}
	
	private static void updateScreen() {
		for(int i = 0 ;i<10 ;i++) {
			parkPlacesArrayList.get(i).repaint();
		}
	}
	
	public static void setPhoneText(String phoneText) {
		phoneNumber_TextField.setText(phoneText);
	}
	
	public static void setDateText(String dateText) {
		enterDate_TextField.setText(dateText);
	}
	
	public static void setPlateNo(String plateNoText) {
		plateNumber_TextField.setText(plateNoText);
	}
	
	public static void setSelected() {
		exit_Button.setEnabled(false);
		newEntry_Button.setEnabled(false);
		save_Button.setEnabled(false);
		
	}
	
	public static void getID(int _ID) {
		ID = _ID;
	}
	

	/**
	 * Create the frame.
	 */
	
	
	public carpark_ControlScreen() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, FRAME_WİDTH, FRAME_HEİGHT);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(85, 85, 85));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		JPanel appBar_panel = new JPanel();
		appBar_panel.setBounds(0, 0, 1100, 27);
		appBar_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		appBar_panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(getX()+e.getX()-mouseX,getY()+e.getY()-mouseY);
			}
		});
		contentPane.setLayout(null);
		appBar_panel.setBackground(new Color(51, 51, 51));
		contentPane.add(appBar_panel);
		appBar_panel.setLayout(null);
		
		Panel close_Panel = new Panel();
		close_Panel.setBackground(new Color(51, 51, 51));
		close_Panel.setBounds(1057, 0, 43, 27);
		appBar_panel.add(close_Panel);
		close_Panel.setLayout(null);
		setFloorButtons();
		
		JLabel close_Label = new JLabel(new ImageIcon("icons\\whiteClose.png"));
		close_Label.addMouseListener(new MouseAdapter() {
			boolean isİnside;
			@Override
			public void mouseEntered(MouseEvent e) {
				close_Panel.setBackground(Color.RED);
				isİnside = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				close_Panel.setBackground(new Color(51,51,51));
				isİnside = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				close_Panel.setBackground(new Color(157,0,4));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(isİnside) {
					dispose();
				}
			
			}
		});
		close_Label.setBounds(0, 0, 43, 27);
		close_Panel.add(close_Label);
		
		JLabel mainText_Label = new JLabel("Otopark Yönetim Sistemi | V0.1");
		mainText_Label.setBackground(new Color(51, 51, 51));
		mainText_Label.setFont(new Font("Tahoma", Font.BOLD, 11));
		mainText_Label.setForeground(new Color(255, 255, 255));
		mainText_Label.setBounds(10, 0, 193, 27);
		appBar_panel.add(mainText_Label);
		
		Panel toBack_Panel = new Panel();
		toBack_Panel.setBounds(1014, 0, 43, 27);
		appBar_panel.add(toBack_Panel);
		toBack_Panel.setLayout(null);
		toBack_Panel.setBackground(new Color(51, 51, 51));
		
		JLabel toBack_Label = new JLabel(new ImageIcon("icons\\altWhite.png"));
		toBack_Label.addMouseListener(new MouseAdapter() {
			boolean isİnside;
			@Override
			public void mouseEntered(MouseEvent e) {
				toBack_Panel.setBackground(new Color(62,62,62));
				isİnside = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				toBack_Panel.setBackground(new Color(51,51,51));
				isİnside = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				toBack_Panel.setBackground(new Color(78,78,78));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(isİnside) {
					toBack();
				}
				
			}
		});
		toBack_Label.setBounds(0, 0, 43, 27);
		toBack_Panel.add(toBack_Label);
		
		
		/*
		 * This part of code is the design of the view
		 * _________________________________________________________________________
		 * 																			\
		 */
		
		
		Panel leftEntranceSide_Panel = new Panel();
		leftEntranceSide_Panel.setBackground(new Color(51, 51, 51));
		leftEntranceSide_Panel.setBounds(421, 673, 10, 27);
		contentPane.add(leftEntranceSide_Panel);
		
		Panel rightEnterenceSide_Panel = new Panel();
		rightEnterenceSide_Panel.setBackground(new Color(51, 51, 51));
		rightEnterenceSide_Panel.setBounds(663, 673, 10, 27);
		contentPane.add(rightEnterenceSide_Panel);
		
		Panel yellowStrip_One_Panel = new Panel();
		yellowStrip_One_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_One_Panel.setBounds(768, 568, 296, 5);
		contentPane.add(yellowStrip_One_Panel);
		
		Panel yellowStrip_Two_Panel = new Panel();
		yellowStrip_Two_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Two_Panel.setBounds(768, 446, 296, 5);
		contentPane.add(yellowStrip_Two_Panel);
		
		Panel yellowStrip_Three_Panel = new Panel();
		yellowStrip_Three_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Three_Panel.setBounds(768, 315, 296, 6);
		contentPane.add(yellowStrip_Three_Panel);
		
		Panel yellowStrip_Four_Panel = new Panel();
		yellowStrip_Four_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Four_Panel.setBounds(768, 188, 296, 6);
		contentPane.add(yellowStrip_Four_Panel);
		
		Panel yellowStrip_Five_Panel = new Panel();
		yellowStrip_Five_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Five_Panel.setBounds(22, 188, 296, 5);
		contentPane.add(yellowStrip_Five_Panel);
		
		Panel yellowStrip_Six_Panel = new Panel();
		yellowStrip_Six_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Six_Panel.setBounds(22, 315, 296, 6);
		contentPane.add(yellowStrip_Six_Panel);
		
		Panel yellowStrip_Seven_Panel = new Panel();
		yellowStrip_Seven_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Seven_Panel.setBounds(22, 445, 296, 6);
		contentPane.add(yellowStrip_Seven_Panel);
		
		Panel yellowStrip_Eight_Panel = new Panel();
		yellowStrip_Eight_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Eight_Panel.setBounds(1058, 63, 6, 612);
		contentPane.add(yellowStrip_Eight_Panel);
		
		Panel yellowStrip_Nine_Panel = new Panel();
		yellowStrip_Nine_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Nine_Panel.setBounds(22, 63, 6, 614);
		contentPane.add(yellowStrip_Nine_Panel);
		
		Panel yellowStrip_Ten_Panel = new Panel();
		yellowStrip_Ten_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Ten_Panel.setBounds(22, 568, 296, 6);
		contentPane.add(yellowStrip_Ten_Panel);
		
		Panel yellowStrip_Eleven_Panel = new Panel();
		yellowStrip_Eleven_Panel.setBackground(new Color(216, 211, 33));
		yellowStrip_Eleven_Panel.setBounds(22, 63, 296, 5);
		contentPane.add(yellowStrip_Eleven_Panel);
		
		Panel yellowStrip_Eleven_Panel_1 = new Panel();
		yellowStrip_Eleven_Panel_1.setBackground(new Color(216, 211, 33));
		yellowStrip_Eleven_Panel_1.setBounds(768, 63, 296, 5);
		contentPane.add(yellowStrip_Eleven_Panel_1);
		
		Panel control_Panel = new Panel();
		control_Panel.setBackground(new Color(128, 128, 128));
		control_Panel.setBounds(399, 167, 296, 455);
		contentPane.add(control_Panel);
		control_Panel.setLayout(null);
		
		JLabel phoneNumber_Label = new JLabel("Telefon Numarası");
		phoneNumber_Label.setFont(new Font("Tahoma", Font.BOLD, 16));
		phoneNumber_Label.setBounds(72, 7, 159, 28);
		control_Panel.add(phoneNumber_Label);
		
		phoneNumber_TextField = new JTextField();
		phoneNumber_TextField.setEditable(false);
		phoneNumber_TextField.setBackground(new Color(192, 192, 192));
		phoneNumber_TextField.setBounds(45, 45, 198, 28);
		control_Panel.add(phoneNumber_TextField);
		phoneNumber_TextField.setColumns(10);
		
		JLabel plateNumber_Label = new JLabel("Plaka Numarası");
		plateNumber_Label.setFont(new Font("Tahoma", Font.BOLD, 16));
		plateNumber_Label.setBounds(72, 97, 159, 28);
		control_Panel.add(plateNumber_Label);
		
		plateNumber_TextField = new JTextField();
		plateNumber_TextField.setEditable(false);
		plateNumber_TextField.setColumns(10);
		plateNumber_TextField.setBackground(Color.LIGHT_GRAY);
		plateNumber_TextField.setBounds(45, 135, 198, 28);
		control_Panel.add(plateNumber_TextField);
		
		JLabel enterDate_Label = new JLabel("     Giriş Tarihi");
		enterDate_Label.setFont(new Font("Tahoma", Font.BOLD, 16));
		enterDate_Label.setBounds(72, 187, 159, 28);
		control_Panel.add(enterDate_Label);
		
		enterDate_TextField = new JTextField();
		enterDate_TextField.setEditable(false);
		enterDate_TextField.setColumns(10);
		enterDate_TextField.setBackground(Color.LIGHT_GRAY);
		enterDate_TextField.setBounds(45, 225, 198, 28);
		control_Panel.add(enterDate_TextField);
		
		newEntry_Button = new JButton("Yeni Kayıt Aç");
		newEntry_Button.setEnabled(false);
		newEntry_Button.setBackground(new Color(90, 90, 90));
		newEntry_Button.setForeground(new Color(194, 194, 194));
		newEntry_Button.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		newEntry_Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		newEntry_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isEnable) {
					String dateString ;
					SimpleDateFormat sdf;
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long timestamp = System.currentTimeMillis();
					dateString = sdf.format(new Date(timestamp));
					plateNumber_TextField.setEditable(true);
					phoneNumber_TextField.setEditable(true);
					enterDate_TextField.setText(dateString);
					save_Button.setEnabled(true);
					isEnable = true;
				}else {
					plateNumber_TextField.setEditable(false);
					phoneNumber_TextField.setEditable(false);
					enterDate_TextField.setText("");
					save_Button.setEnabled(false);
					isEnable = false;
				}
			}
		});
		newEntry_Button.setBounds(10, 298, 131, 36);
		control_Panel.add(newEntry_Button);
		
		exit_Button = new JButton("Çıkış Yap / Hesabı Kes");
		exit_Button.setForeground(new Color(194, 194, 194));
		exit_Button.setBackground(new Color(90, 90, 90));
		exit_Button.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		exit_Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		exit_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateFee();
			}
		});
		
		exit_Button.setBounds(72, 346, 170, 36);
		exit_Button.setEnabled(false);
		control_Panel.add(exit_Button);
		
		openBarrier_Button = new JButton("Bariyeri Kaldır");
		openBarrier_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isBarrierOpen) {
					openBarrier_Button.setText("Bariyeri Kapat");
					JOptionPane.showMessageDialog(null, "Bariye Açılıyor!","Dikkat!",JOptionPane.WARNING_MESSAGE);
					isBarrierOpen=false;
				}else {
					openBarrier_Button.setText("Bariyeri Aç");
					JOptionPane.showMessageDialog(null, "Bariye Kapanıyor!","Dikkat!",JOptionPane.WARNING_MESSAGE);
					isBarrierOpen=true;
				}
				
			} 
		});
		openBarrier_Button.setForeground(new Color(194, 194, 194));
		openBarrier_Button.setBackground(new Color(90, 90, 90));
		openBarrier_Button.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		openBarrier_Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		openBarrier_Button.setBounds(72, 396, 171, 36);
		control_Panel.add(openBarrier_Button);
		
		save_Button = new JButton("Kaydet !");
		save_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(phoneNumber_TextField.getText() != null&&plateNumber_TextField.getText() != null&&enterDate_TextField.getText() != null) {
					cpM_SQLManagement.updateParkPlace(ID, 1, phoneNumber_TextField.getText(), plateNumber_TextField.getText(), enterDate_TextField.getText());
					JOptionPane.showMessageDialog(null, "İşlem Başarılı","Başarılı !",JOptionPane.INFORMATION_MESSAGE);
					setParkPlaces(floorNumber);
				}else {
					JOptionPane.showMessageDialog(null, "Tüm Alanların Dolu Olması Gerekiyor!","Başarısız",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		save_Button.setForeground(new Color(194, 194, 194));
		save_Button.setBackground(new Color(90, 90, 90));
		save_Button.setEnabled(false);
		save_Button.setFont(new Font("Tahoma", Font.BOLD, 14));
		save_Button.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		save_Button.setBounds(155, 298, 131, 36);
		control_Panel.add(save_Button);
		
		Panel currentParkPlaceNumber_Panel = new Panel();
		currentParkPlaceNumber_Panel.setLayout(null);
		currentParkPlaceNumber_Panel.setBackground(Color.GRAY);
		currentParkPlaceNumber_Panel.setBounds(473, 120, 150, 35);
		contentPane.add(currentParkPlaceNumber_Panel);
		
		currentParkPlaceName_Label = new JLabel("");
		currentParkPlaceName_Label.setFont(new Font("Dialog", Font.BOLD, 28));
		currentParkPlaceName_Label.setBounds(12, 7, 124, 21);
		currentParkPlaceNumber_Panel.add(currentParkPlaceName_Label);
		
		
		
	}
}
