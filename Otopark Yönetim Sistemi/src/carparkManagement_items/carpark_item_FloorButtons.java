package carparkManagement_items;

import javax.swing.JPanel;

import carparkManagement_View.carpark_ControlScreen;

import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class carpark_item_FloorButtons extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int PANEL_WİDTH = 150,PANEL_HEİGHT=25;
	private final int floorID;
	/**
	 * Create the panel.
	 */
	public carpark_item_FloorButtons(int _floorID, int _positionX, int _positionY) {
		
		this.floorID = _floorID;
		setSize(PANEL_WİDTH, PANEL_HEİGHT);
		setBackground(new Color(173,173,167));
		setLocation(_positionX, _positionY);
		setLayout(null);
		
		Panel katbir_Panel = new Panel();
		katbir_Panel.setBounds(2, 2, 146, 21);
		katbir_Panel.setLayout(null);
		katbir_Panel.setBackground(new Color(45, 47, 66));
		add(katbir_Panel);
		
		JLabel katbir_Label = new JLabel("          KAT "+this.floorID);
		
		katbir_Label.setForeground(new Color(173, 173, 167));
		katbir_Label.setFont(new Font("Tahoma", Font.BOLD, 16));
		katbir_Label.setBounds(0, 0, 146, 21);
		katbir_Panel.add(katbir_Label);
		
		katbir_Label.addMouseListener(new MouseListener() {
			boolean isİnside;
			@Override
			public void mouseReleased(MouseEvent e) {
				katbir_Panel.setBackground(new Color(64, 67, 94));
				if(isİnside) {
					carpark_ControlScreen.setSelected();
					carpark_ControlScreen.setParkPlaces(_floorID);
				}else {
					katbir_Panel.setBackground(new Color(45, 47, 66));
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				katbir_Panel.setBackground(new Color(79, 83, 117));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				katbir_Panel.setBackground(new Color(45, 47, 66));
				isİnside = false;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				katbir_Panel.setBackground(new Color(64, 67, 94));
				isİnside = true;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
			
		});
		
		
	}
}
