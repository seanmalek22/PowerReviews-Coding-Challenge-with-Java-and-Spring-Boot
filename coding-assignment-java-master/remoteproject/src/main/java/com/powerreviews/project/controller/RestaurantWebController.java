package com.powerreviews.project.controller;

import com.powerreviews.project.persistence.RestaurantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestaurantWebController {
	
	private final RestaurantRepository restaurantRepository;
	
	public RestaurantWebController(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@RequestMapping("/restaurants/list")
	public String getRestaurants(Model model) {
		
		model.addAttribute("restaurants", restaurantRepository.findAll());
		
		return "index";
	}
}