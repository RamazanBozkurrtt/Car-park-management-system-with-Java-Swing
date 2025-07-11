package carparkManagement_SQLTransactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import carparkManagement_items.carpark_item_parkPlaces;


 public class carparkM_SQLTransactions_MySQL implements IParkingDal {
	
	public static String selectedFloor;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private String parkplaceNames[];
	private final static Character[] letters = {'A','B','C','D','E',
												'F','G','H','I','J',};
	private int parkPlaceNumbers[];
	private int parkPlaceCoord[][];
	
	
	//SQL server informations
	private static final String 
			dbURL ="jdbc:mysql://localhost:3425/carparkmanagement",
			USERNAME = "root",
			PASSWORD = "15651068rB";
	
	
	
	//This method is for setting connection
	private void setConnection() {
		try {
			connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
	}
	
	//This method is for set PreparedStatement. This method can not be used before setConnection() method.
	private void setPreparedStatemen(String query) {
		try {
			preparedStatement = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//This function is for ADMİN login transaction
		public boolean adminLogin(String name,String password) {
			String queryName,queryPassword;
			setConnection();
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM adminlogin");
				while(resultSet.next()) {
					queryName = resultSet.getString("adminL_username");
					queryPassword = resultSet.getString("adminL_password");
					if(queryName.equals(name)&&queryPassword.equals(password)) {
						return true;
					}
				}
			}catch(SQLException exception) {
				System.out.println(exception.getMessage());
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return false;
		}
		
		
	@Override
	public void updateParkPlace(int _id) {
			setConnection();
			String qryString = "update parkplace set parkP_date=?,parkP_isFull=?,parkP_phoneN=?,parkP_plateNumber=? where parP_id=?";
			
			setPreparedStatemen(qryString);
			try {
				preparedStatement.setNull(1, java.sql.Types.NULL);
				preparedStatement.setNull(2, java.sql.Types.INTEGER);
				preparedStatement.setNull(3, java.sql.Types.NULL);
				preparedStatement.setNull(4, java.sql.Types.NULL);
				preparedStatement.setInt(5, _id);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
				}
	}
		
		
	//This Function is for update park place
	@Override
	public void updateParkPlace(int id,int isFull,String phoneN,String plateNumber,String time) {

		
		setConnection();
		String qryString = "update parkplace set parkP_date=?,parkP_isFull=?,parkP_phoneN=?,parkP_plateNumber=? where parP_id=?";
		setPreparedStatemen(qryString);
		try {
			preparedStatement.setString(1, time);
			preparedStatement.setInt(2, isFull);
			preparedStatement.setString(3, phoneN);
			preparedStatement.setString(4, plateNumber);
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			}
	}
		
	//This method sets the coordinates that predetermined of park places
	private void setParkPlaceCoords() {
		@SuppressWarnings("unused")
		int x;
		int y = 86;
		this.parkPlaceCoord = new int[2][5];
		for(int i = 0 ; i<2 ; i++ ) {
			if(i == 0) {
				x = 40;
			}else if(i == 1){
				x = 768;
			}
			y = 86;
			for(int j = 0 ; j<5 ;j++ ) {
				this.parkPlaceCoord[i][j] = y;
				y += 127;
			}
		}
	}
	
	//This method set numbers and names of floor
	private void setParkPlaceNumbers(int _floorNumber) {
		int parkPlace = 10*_floorNumber;
		this.parkPlaceNumbers = new int[10];
		if(parkPlace>10) {
			for(int i = 0; i < 10 ; i++ ) {
				this.parkPlaceNumbers[i] = parkPlace-(10-i)+1;
			}
		}else {
			for(int i=0 ; i<10 ; i++) {
				this.parkPlaceNumbers[i] = i+1;
			}
		}
	}

	@Override
	public ArrayList<carpark_item_parkPlaces> loadParkPlaces(int _floorNumber) {
		ArrayList<carpark_item_parkPlaces> parkplaceİnformations = new ArrayList<carpark_item_parkPlaces>();
		int x = 40;
		boolean isFull;
		setParkPlaceNumbers(_floorNumber);
		setConnection();
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM parkplace");
			int in = 0;
			int j = 0;
			setParkPlaceCoords();
			while(resultSet.next()) {
				
				if(resultSet.getInt("parkP_isFull") == 1) {
					isFull = true;
				}else {
					isFull = false;
				}
				
				if(resultSet.getInt("parP_id")>=this.parkPlaceNumbers[0]&&resultSet.getInt("parP_id")<=this.parkPlaceNumbers[9]) {
					if(j == 5) {
						j =0;
						in = 0;
						x = 768; 
					}
					
					parkplaceİnformations.add(new carpark_item_parkPlaces(
						resultSet.getInt("parP_id"),
						resultSet.getString("parkP_phoneN"),
						resultSet.getString("parkP_plateNumber"),
						resultSet.getString("parkP_date"),
						isFull,
						x,
						this.parkPlaceCoord[in][j],
						_floorNumber
						));
					j++;
				}
			}
			
			this.parkplaceNames = new String[10];
			for(int i = 0; i < 10;i++) {
				parkplaceNames[i] = String.valueOf(carparkM_SQLTransactions_MySQL.letters[_floorNumber-1])+String.valueOf(i+1);
			}
			
			for(int count = 0; count<10;count++) {
				parkplaceİnformations.get(count).setLabelText(this.parkplaceNames[count]);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return parkplaceİnformations;
	}

}
