package com.entlogics.hotelbookingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.entlogics.hotelbookingsystem.entity.Bill;
import com.entlogics.hotelbookingsystem.entity.Booking;
import com.entlogics.hotelbookingsystem.entity.Customer;
import com.entlogics.hotelbookingsystem.entity.Employee;
import com.entlogics.hotelbookingsystem.entity.Hotel;
import com.entlogics.hotelbookingsystem.entity.Hotel_Customer;
import com.entlogics.hotelbookingsystem.entity.Hotel_Service;
import com.entlogics.hotelbookingsystem.entity.Room;

@Component
public class HotelDAOImpl implements IHotelDAO {

	// Defining properties

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatement1 = null;

	// create logger object
	private static final Logger logger = LogManager.getLogger(HotelDAOImpl.class);

	// defining field dataSource of type DataSource
	private DataSource dataSource;

	// setter method for dataSource - setter injection
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// returns a list of Hotel objects from DB
	@Override
	public List<Hotel> getAllHotels() {

		logger.info("Inside getAllHotels() in DAO");

		// SQL query
		String sql = "SELECT hotel_id, hotel_name, hotel_location, hotel_phone, hotel_email, hotel_rating, pet_friendly FROM hotel";

		// creating empty list
		List<Hotel> listHotel = new ArrayList<>();

		// Hotel object
		Hotel hotel = null;

		try {
			// Connect to DB using dataSource
			connection = dataSource.getConnection();

			logger.info("Connection in getAllHotels() DAO : " + connection);

			// Fetch all hotel records - Create a statement & Execute the sql query
			statement = connection.createStatement();

			resultSet = statement.executeQuery(sql);

			logger.info("ResultSet: " + resultSet);

			// iterate through resultSet using while loop
			while (resultSet.next()) {

				// initializing the hotel object
				hotel = new Hotel();

				// setting data from database to hotel object
				hotel.setHotel_id(resultSet.getInt("hotel_id"));
				hotel.setHotel_name(resultSet.getString("hotel_name"));
				hotel.setHotel_location(resultSet.getString("hotel_location"));
				hotel.setHotel_phone(resultSet.getLong("hotel_phone"));
				hotel.setHotel_email(resultSet.getString("hotel_email"));
				hotel.setHotel_rating(resultSet.getString("hotel_rating"));
				hotel.setPet_friendly(resultSet.getBoolean("pet_friendly"));

				// add them to a List
				listHotel.add(hotel);
				logger.info("Hotel object in getAllHotels() : " + hotel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the List
		return listHotel;
	}

	// returns a single Customer object from the db
	@Override
	public Customer getCustomerInformation(int hotelId, int customerId) {

		logger.info("Inside HotelDAO getCustomerInformation()");

		// SQL Query
		String sql = "SELECT c.customer_id, c.customer_name, c.customer_address,c.customer_phone,c.customer_email,c.preferences,c.special_needs,b.booking_id,b.bill_id "
				+ "FROM customer c " + "JOIN hotel_customer hc " + "USING (customer_id) " + "JOIN booking b "
				+ "USING (customer_id) " + "WHERE c.customer_id =" + customerId + " AND hc.hotel_id = " + hotelId;

		// customer object
		Customer customer = null;

		// initializing the customer object
		customer = new Customer();

		// Initializing lists
		List<Booking> listBooking = new ArrayList<>();
		List<Bill> listBill = new ArrayList<>();

		try {
			// Connect to DB using dataSource
			connection = dataSource.getConnection();

			// Fetch a customer record - Create a statement & Execute the sql query
			statement = connection.createStatement();

			resultSet = statement.executeQuery(sql);

			logger.info("Size of resultSet: " + resultSet.getFetchSize());

			int rowCtr = 0;

			// Iterate over the resultSet
			while (resultSet.next()) {

				logger.info("Inside while loop");

				int id = customer.getCustomer_id();

				logger.info("ID outside if : " + id);

				rowCtr++;
				if (id == 0) {
					logger.info("ID inside if : " + id);
					// For each record in the resultSet
					customer.setCustomer_id(resultSet.getInt("customer_id"));
					customer.setCustomer_name(resultSet.getString("customer_name"));
					customer.setCustomer_address(resultSet.getString("customer_address"));
					customer.setCustomer_phone(resultSet.getLong("customer_phone"));
					customer.setCustomer_email(resultSet.getString("customer_email"));
					customer.setPreferences(resultSet.getString("preferences"));
					customer.setSpecial_needs(resultSet.getString("special_needs"));
				}

				// create booking object and set id , add booking obj in the list
				Booking booking = new Booking();
				booking.setBooking_id(resultSet.getInt("booking_id"));
				listBooking.add(booking);

				logger.info("Booking object in getCustomerInformation(): " + booking);

				// create bill object and set id , add bill obj in the list
				Bill bill = new Bill();
				bill.setBill_id(resultSet.getInt("bill_id"));
				listBill.add(bill);

				logger.info("Bill object in getCustomerInformation(): " + bill);
			}

			// setting the list of booking & bill in customer object
			customer.setBookings(listBooking);
			customer.setBills(listBill);

			logger.info("Customer object in getCustomerInformation(): " + customer);

			logger.info("Total number of rows in ResultSet object: " + rowCtr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the object
		return customer;
	}

	// adds a new hotel record to the db
	@Override
	public boolean addNewHotel(Hotel h) {

		logger.info("Inside HotelDAO addNewHotel()");

		boolean isSuccess = false;

		// Get the Hotel object from Controller
		String hotelName = h.getHotel_name();
		String hotelLocation = h.getHotel_location();
		long hotelPhone = h.getHotel_phone();
		String hotelRating = h.getHotel_rating();
		String hotelEmail = h.getHotel_email();
		boolean hotelPetFriendly = h.isPet_friendly();

		logger.info("Printing hotel object in the HotelDAO addNewHotel()" + h);

		try {
			// Connect to DB using dataSource
			connection = dataSource.getConnection();

			// using connection object create preparedStatement , passing sql query for
			// inserting data as an arg
			preparedStatement = connection.prepareStatement(
					"INSERT INTO hotel(hotel_name, hotel_location, hotel_phone, hotel_email, hotel_rating, pet_friendly) VALUES (?,?,?,?,?,?)");

			// setting the values for variables using preparedStatement object
			preparedStatement.setString(1, hotelName);
			preparedStatement.setString(2, hotelLocation);
			preparedStatement.setLong(3, hotelPhone);
			preparedStatement.setString(4, hotelEmail);
			preparedStatement.setString(5, hotelRating);
			preparedStatement.setBoolean(6, hotelPetFriendly);

			// executing the preparedStatement object
			preparedStatement.executeUpdate();

			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send a success message back to Controller
		return isSuccess;
	}

	// returns a single Hotel object from the db
	@Override
	public Hotel getHotelInformation(int hotelId) {

		logger.info("Inside HotelDAO getHotelInformation()");

		// SQL Query
		String sql = "SELECT h.hotel_id, h.hotel_name, h.hotel_location, h.hotel_phone, h.hotel_email, h.hotel_rating, h.pet_friendly, hc.customer_id, r.room_id, \r\n"
				+ "e.emp_id, hs.service_id, b.booking_id \r\n" + "FROM hotel h JOIN hotel_customer hc \r\n"
				+ "USING (hotel_id)\r\n" + "JOIN room r\r\n" + "USING (hotel_id)\r\n" + "JOIN employee e\r\n"
				+ "USING (hotel_id)\r\n" + "JOIN hotel_service hs\r\n" + "USING (hotel_id)\r\n" + "JOIN booking b\r\n"
				+ "USING (hotel_id)\r\n" + "WHERE h.hotel_id = " + hotelId;

		// hotel object
		Hotel hotel = null;

		// initializing the hotel object
		hotel = new Hotel();

		// Initializing lists
		List<Hotel_Customer> listHotelCustomer = new ArrayList<>();
		List<Room> listRoom = new ArrayList<>();
		List<Employee> listEmployee = new ArrayList<>();
		List<Hotel_Service> listHotelService = new ArrayList<>();
		List<Booking> listBooking = new ArrayList<>();

		try {
			// Connect to DB using dataSource
			connection = dataSource.getConnection();

			logger.info("Connection in getHotelInformation() DAO : " + connection);

			// Fetch a hotel record - Create a statement & Execute the sql query
			statement = connection.createStatement();

			resultSet = statement.executeQuery(sql);

			logger.info("Size of resultSet: " + resultSet.getFetchSize());

			int rowCtr = 0;

			// Iterate over the resultSet
			while (resultSet.next()) {

				logger.info("Inside while loop");

				int id = hotel.getHotel_id();

				logger.info("ID outside if : " + id);

				rowCtr++;
				if (id == 0) {
					logger.info("ID inside if : " + id);
					// For each record in the resultSet
					hotel.setHotel_id(resultSet.getInt("hotel_id"));
					hotel.setHotel_name(resultSet.getString("hotel_name"));
					hotel.setHotel_location(resultSet.getString("hotel_location"));
					hotel.setHotel_phone(resultSet.getLong("hotel_phone"));
					hotel.setHotel_rating(resultSet.getString("hotel_rating"));
					hotel.setHotel_email(resultSet.getString("hotel_email"));
					hotel.setPet_friendly(resultSet.getBoolean("pet_friendly"));
				}

				// create hotelCustomer object and set id , add hotelCustomer obj in the list
				Hotel_Customer hotelCustomer = new Hotel_Customer();
				hotelCustomer.setCustomer_id(resultSet.getInt("customer_id"));
				listHotelCustomer.add(hotelCustomer);

				logger.info("Customer object in getHotelInformation(): " + hotelCustomer);

				// create room object and set id , add room obj in the list
				Room room = new Room();
				room.setRoom_id(resultSet.getInt("room_id"));
				listRoom.add(room);

				logger.info("Room object in getHotelInformation(): " + room);

				// create Employee object and set id , add emp obj in the list
				Employee emp = new Employee();
				emp.setEmp_id(resultSet.getInt("emp_id"));
				listEmployee.add(emp);

				logger.info("Employee object in getHotelInformation(): " + emp);

				// create Hotel_Service object and set id , add hotelService obj in the list
				Hotel_Service hotelService = new Hotel_Service();
				hotelService.setService_id(resultSet.getInt("service_id"));
				listHotelService.add(hotelService);

				logger.info("Hotel_Service object in getHotelInformation(): " + hotelService);

				// create booking object and set id , add booking obj in the list
				Booking booking = new Booking();
				booking.setBooking_id(resultSet.getInt("booking_id"));
				listBooking.add(booking);

				logger.info("Booking object in getHotelInformation(): " + booking);
			}

			// setting the list of customer, room, emp, services & booking in hotel object
			hotel.setCustomers(listHotelCustomer);
			hotel.setRooms(listRoom);
			hotel.setEmployees(listEmployee);
			hotel.setServices(listHotelService);
			hotel.setBookings(listBooking);

			logger.info("Hotel object in getHotelInformation(): " + hotel);

			logger.info("Total number of rows in ResultSet object: " + rowCtr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the object
		return hotel;
	}

	// deletes a hotel record from the db
	@Override
	public boolean deleteAHotel(int hotelId) {

		logger.info("Inside HotelDAO deleteAHotel()");

		boolean isSuccess = false;

		// SQL Query
		String sql = "DELETE FROM hotel WHERE hotel_id="+hotelId;

		logger.info("Inside deleteAHotel DAO Hotel ID: " + hotelId);

		try {
			// Connect to DB using dataSource
			connection = dataSource.getConnection();
			logger.info("Inside deleteAHotel DAO connection: " + connection);

			// using connection object create preparedStatement , passing sql query for
			// inserting data as an arg
			preparedStatement = connection.prepareStatement(sql);
			logger.info("SQL in deleteAHotel dao: " + sql);

			// setting the values for variables using preparedStatement object
			// preparedStatement.setInt(0, hotelId);

			// executing the preparedStatement object
			preparedStatement.executeUpdate();

			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
}
