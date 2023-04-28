	package com.entlogics.hotelbookingsystem.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entlogics.hotelbookingsystem.dao.IHotelDAO;
import com.entlogics.hotelbookingsystem.entity.Customer;
import com.entlogics.hotelbookingsystem.entity.Hotel;

@Controller
public class HotelController {

	// create logger object
	private static final Logger logger = LogManager.getLogger(HotelController.class);

	@Autowired
	private IHotelDAO hotelDAO;

	// this method gets list of hotels
	@RequestMapping("/hotels")
	public String getListofHotels(Model theModel) {

		logger.info("Inside HotelController - getListofHotels()");

		// calling DAO method to get list of hotels
		List<Hotel> hotelList = hotelDAO.getAllHotels();

		// adding hotelList to the model
		theModel.addAttribute("hotelList", hotelList);

		return "hotels-list";
	}

	// this method gets one customer of a hotel

	@RequestMapping("/hotels/{hotel-id}/customers/{customer-id}")
	public String getOneCustomerOfOneHotel(@PathVariable("hotel-id") int hotelId,
			@PathVariable("customer-id") int customerId, Model theModel) {

		logger.info("Inside HotelController - getOneCustomerOfOneHotel()");

		// using DAO object to details of a single customer
		Customer customer = hotelDAO.getCustomerInformation(hotelId, customerId);

		// adding customer to the model
		theModel.addAttribute("customer", customer);

		return "view-hotel-customer";
	}

	// this method adds a new hotel

	@RequestMapping(value = "/hotels", method = RequestMethod.POST)
	public String addNewHotel(@RequestParam("hotel-name") String hotelName,
			@RequestParam("hotel-location") String hotelLocation, @RequestParam("hotel-phone") String hotelPhone,
			@RequestParam("hotel-email") String hotelEmail, @RequestParam("hotel-rating") String hotelRating,
			@RequestParam("pet-friendly") String hotelPetFriendly, Model theModel) {

		// create a hotel object
		Hotel hotel = new Hotel(hotelName, hotelLocation);
		hotel.setHotel_phone(Long.parseLong(hotelPhone));
		hotel.setHotel_email(hotelEmail);
		hotel.setHotel_rating(hotelRating);
		hotel.setPet_friendly(Boolean.parseBoolean(hotelPetFriendly));

		logger.info("Printing hotel object in the HotelController addNewHotel(): " + hotel);

		// call DAO method addNewHotel()
		boolean isAdded = hotelDAO.addNewHotel(hotel);
		String added = String.valueOf(isAdded);

		// send success message back to the client
		theModel.addAttribute("Success", added);

		return "add-hotel-success";
	}

	// this method gets details of one Hotel
	@RequestMapping(value = "/hotels/{hotel-id}", method = RequestMethod.GET)
	public String getOneHotel(@PathVariable("hotel-id") int hotelId, Model theModel) {

		logger.info("Inside HotelController - getOneHotel()");

		// using DAO object to get details of a single hotel
		Hotel hotel = hotelDAO.getHotelInformation(hotelId);

		// adding hotel to the model
		theModel.addAttribute("hotel", hotel);
		return "view-hotel";
	}

	// this method deletes a hotel record 
	@RequestMapping(value = "/hotels/{hotel_id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteOneHotel(@PathVariable("hotel_id") int hotelId, Model theModel) {

		logger.info("Inside HotelController - deleteOneHotel()");

		// using DAO object to delete a hotel
		boolean isDeleted = hotelDAO.deleteAHotel(hotelId);
		String deletedHotel = String.valueOf(isDeleted);
		
		logger.info("Inside deleteOneHotel Controller Hotel ID: "+hotelId);

		// adding deletedHotel to the model
		theModel.addAttribute("Success", deletedHotel);

		return "delete-hotel";
	}
	
	@RequestMapping("/hotels/deletehotelform")
	public String showFormToDeleteHotel(Model theModel, @RequestParam int hotel_id) {
		
		logger.info("Inside showFormToDeleteHotel Controller");
		
		Hotel hotel = new Hotel();
		
		hotel.setHotel_id(hotel_id);
		
		theModel.addAttribute("hotel", hotel);
		return "delete-hotel-form";
	}
}
