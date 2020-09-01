package com.powerreviews.project.controller;

import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.CustomerEntity;
import com.powerreviews.project.persistence.RestaurantEntity;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AverageRatingController {
	
	private final RestaurantRepository restaurantRepository;
	
	public AverageRatingController(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@RequestMapping(value = "/restaurant/{id}/averagerating", method = RequestMethod.GET)
	public String getRestaurantsandComments(Model model, @PathVariable Integer id) {
		
    	Optional<RestaurantEntity> rest1 = restaurantRepository.findById(id);
    	if(rest1.isPresent()) {
    	    RestaurantEntity rest2 = rest1.get();
    		model.addAttribute("restaurant", rest2);

    	} else {
    		System.out.println("Couldn't find data for restaurants!");
    	}
    	
    	Double averageRating = CustomerEntity.getAverageRating().get(id-1);
    	model.addAttribute("avgRating", averageRating);
    	
		return "index1";
	}
}