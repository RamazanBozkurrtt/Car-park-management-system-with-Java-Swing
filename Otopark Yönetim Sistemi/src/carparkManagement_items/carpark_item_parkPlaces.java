package carparkManagement_items;

import javax.swing.JPanel;

import carparkManagement_View.carpark_ControlScreen;

import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class carpark_item_parkPlaces extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int PANEL_WİDTH=278,PANEL_HEİGHT=85;
	private final int positionX,positionY;
	private final int ID;
	private final String parkPhoneN,plateNum;
	private final String time;
	private final boolean isFull;
	private JLabel placeName_Label;
	private Color currColor;

	/**
	 * Create the panel.
	 */
	
	public carpark_item_parkPlaces(int _ID, String _parkPhoneN,String _plateNum, String _time, boolean _isFull,int _positionX, int _positionY,int floorNumber) {
		setBackground(new Color(0, 255, 0));
		this.plateNum = _plateNum;
		this.positionX = _positionX;
		this.positionY = _positionY;
		this.ID = _ID;
		this.parkPhoneN = _parkPhoneN;
		this.time = _time;
		this.isFull = _isFull;
		setBounds(positionX, positionY, PANEL_WİDTH, PANEL_HEİGHT);
		setLocation(_positionX, _positionY);
		setLayout(null);
		
		Panel parkSidePart_One_Panel = new Panel();
		parkSidePart_One_Panel.setBackground(new Color(29, 29, 29));
		parkSidePart_One_Panel.setBounds(5, 5, 268, 75);
		if(_isFull) {
			this.currColor = Color.green;
			this.setBackground(this.currColor);
		}else {
			this.currColor = Color.red;
			this.setBackground(this.currColor);
		}
		add(parkSidePart_One_Panel);
		parkSidePart_One_Panel.setLayout(null);
		
		placeName_Label = new JLabel();
		placeName_Label.addMouseListener(new MouseAdapter() {
			boolean isİnside;
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				parkSidePart_One_Panel.setBackground(new Color(37,37,37));
				
				isİnside = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				parkSidePart_One_Panel.setBackground(new Color(29,29,29));

				isİnside = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {

				parkSidePart_One_Panel.setBackground(new Color(46,46,46));	
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(isİnside) {
					parkSidePart_One_Panel.setBackground(new Color(37,37,37));
					//Set the TextField of carpark_ControlScreen
					carpark_ControlScreen.setPlateNo(_plateNum);
					carpark_ControlScreen.setPhoneText(_parkPhoneN);
					carpark_ControlScreen.setDateText(_time);
					
					//Set the buttons editable
					carpark_ControlScreen.updateButtons(isFull);
					
					carpark_ControlScreen.getID(_ID);
					
					carpark_ControlScreen.set_CurrentParkPlaceLabelText(placeName_Label.getText().replace(" ", ""));
				}
			}
		});
		placeName_Label.setForeground(new Color(255, 255, 255));
		placeName_Label.setFont(new Font("Tahoma", Font.BOLD, 26));
		placeName_Label.setBounds(0, 0, 268, 75);
		parkSidePart_One_Panel.add(placeName_Label);
		
		
		
	}
	
	public void setLabelText(String labelText) {
		placeName_Label.setText("              "+labelText);
	}
	
	public int getID() {
		
		return ID;
	}


	public String getPlateName() {
		
		return plateNum;
	}


	public String getTime() {
		
		return time;
	}


	public boolean isFull() {
		
		return isFull;
	}

	public String getParkPhoneN() {
		return parkPhoneN;
	}
}
