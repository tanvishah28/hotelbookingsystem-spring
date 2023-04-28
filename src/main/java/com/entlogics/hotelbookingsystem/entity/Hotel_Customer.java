package com.entlogics.hotelbookingsystem.entity;

//Entity class for Hotel_Customer - defining the fields, setters & getters and returning toString()

public class Hotel_Customer {

	// Defining the fields
	private int hotel_id;
	
	private int customer_id;
	
	private String customer_type;
	
	private float customer_reward_points;

	public int getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public float getCustomer_reward_points() {
		return customer_reward_points;
	}

	public void setCustomer_reward_points(float customer_reward_points) {
		this.customer_reward_points = customer_reward_points;
	}

	@Override
	public String toString() {
		return "Hotel_Customer [hotel_id=" + hotel_id + ", customer_id=" + customer_id + ", customer_type="
				+ customer_type + ", customer_reward_points=" + customer_reward_points + "]";
	}
}
