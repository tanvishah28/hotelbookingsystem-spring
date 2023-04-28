package com.entlogics.hotelbookingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HotelLoadFormController {

	@RequestMapping("/addHotelForm")
	public String addHotelForm() {
		return "add-hotel";
	}
}
