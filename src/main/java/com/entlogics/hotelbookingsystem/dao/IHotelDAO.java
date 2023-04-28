package com.entlogics.hotelbookingsystem.dao;

import java.util.List;

import com.entlogics.hotelbookingsystem.entity.Customer;
import com.entlogics.hotelbookingsystem.entity.Hotel;

public interface IHotelDAO {

	public List<Hotel> getAllHotels();
	
	public Customer getCustomerInformation(int hotelId, int customerId);
	
	public boolean addNewHotel(Hotel h);
	
	public Hotel getHotelInformation(int hotelId);	
	
	public boolean deleteAHotel(int hotelId);
}
