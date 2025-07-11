package carparkManagement_SQLTransactions;

import java.util.ArrayList;

import carparkManagement_items.carpark_item_parkPlaces;

public interface IParkingDal {
	
	
	//User Login
	public boolean adminLogin(String name,String password);
	
	//Update park places
	public void updateParkPlace(int id,int isFull,String name,String plateNumber,String time);
	
	//Set park places to null
	public void updateParkPlace(int _id);
	
	
	public ArrayList<carpark_item_parkPlaces> loadParkPlaces(int _floorNumber);
	
	
	
	
	
	
	
	
	
	

}
