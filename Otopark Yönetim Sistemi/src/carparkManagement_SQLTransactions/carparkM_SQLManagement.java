package carparkManagement_SQLTransactions;

import java.util.ArrayList;

import carparkManagement_items.carpark_item_parkPlaces;

public class carparkM_SQLManagement {
	
	private final IParkingDal parkingDal;
	
	
	//Constructor method
	public carparkM_SQLManagement(IParkingDal _parkingDal) {
		this.parkingDal = _parkingDal;
	}
	
	public boolean adminLogin(String name,String password) {
		return parkingDal.adminLogin(name, password);
	}
	
	
	public void updateParkPlace(int id,int isFull,String name,String plateNumber,String time) {
		parkingDal.updateParkPlace(id, isFull, name, plateNumber,time);
	}
	
	
	public ArrayList<carpark_item_parkPlaces> loadParkPlaces(int _floorNumber){
		return parkingDal.loadParkPlaces(_floorNumber);
	}
	
	public void updateParkPlace(int _id) {
		parkingDal.updateParkPlace(_id);
	}

	
	

}
