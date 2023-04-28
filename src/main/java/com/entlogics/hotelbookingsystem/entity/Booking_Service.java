package com.entlogics.hotelbookingsystem.entity;

//Entity class for Booking_Services - defining the fields, setters & getters and returning toString()

public class Booking_Service {

	// Defining the fields
	private int booking_id;
	
	private Hotel_Service hotel_service_id;
	
	private float service_rating;

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public Hotel_Service getHotel_service_id() {
		return hotel_service_id;
	}

	public void setHotel_service_id(Hotel_Service hotel_service_id) {
		this.hotel_service_id = hotel_service_id;
	}

	public float getService_rating() {
		return service_rating;
	}

	public void setService_rating(float service_rating) {
		this.service_rating = service_rating;
	}

	@Override
	public String toString() {
		return "Booking_Services [booking_id=" + booking_id + ", hotel_service_id=" + hotel_service_id
				+ ", service_rating=" + service_rating + "]";
	}
}
