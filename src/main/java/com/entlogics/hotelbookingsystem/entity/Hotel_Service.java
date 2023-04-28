package com.entlogics.hotelbookingsystem.entity;

//Entity class for Hotel_Service - defining the fields, setters & getters and returning toString()

public class Hotel_Service {

	// Defining the fields
	private int hotel_service_id;
	
	private int hotel_id;
	
	private int service_id;
	
	private float service_price;

	public int getHotel_service_id() {
		return hotel_service_id;
	}

	public void setHotel_service_id(int hotel_service_id) {
		this.hotel_service_id = hotel_service_id;
	}

	public int getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public float getService_price() {
		return service_price;
	}

	public void setService_price(float service_price) {
		this.service_price = service_price;
	}

	@Override
	public String toString() {
		return "Hotel_Service [hotel_service_id=" + hotel_service_id + ", hotel_id=" + hotel_id + ", service_id="
				+ service_id + ", service_price=" + service_price + "]";
	}
}
