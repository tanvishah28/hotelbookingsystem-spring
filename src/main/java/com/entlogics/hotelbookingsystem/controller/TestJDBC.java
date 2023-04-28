/*
 * package com.entlogics.hotelbookingsystem.controller;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.entlogics.hotelbookingsystem.dao.IHotelDAO; import
 * com.entlogics.hotelbookingsystem.entity.Hotel;
 * 
 * @Controller //@RequestMapping("/api") public class TestJDBC {
 * 
 * @Autowired private IHotelDAO hotelDAO;
 * 
 * @RequestMapping("/hotels") public String getListofHotels(Model theModel) {
 * 
 * System.out.println("Inside  TestJDBC Controller"); List<Hotel> hotelList =
 * hotelDAO.getAllHotels(); theModel.addAttribute("hotelList",hotelList);
 * 
 * return "hotels-list"; } }
 */