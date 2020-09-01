package com.powerreviews.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerreviews.project.persistence.*;

@SpringBootApplication()
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	CommandLineRunner runner(RestaurantRepository restaurantRepository) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();

			TypeReference<List<RestaurantEntity>> restaurantTypeReference = new TypeReference<List<RestaurantEntity>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/restaurants.json");
			try {
				List<RestaurantEntity> restaurants = mapper.readValue(inputStream, restaurantTypeReference);
				//save restaurants to the database
				restaurantRepository.saveAll(restaurants);
			} catch (IOException e){
				System.out.println("Unable to save restaurants: " + e.getMessage());
			}

		};
	}
	
	@Bean
	CommandLineRunner runner2(CustomerRepository customerRepository) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();

			TypeReference<List<CustomerEntity>> customerTypeReference = new TypeReference<List<CustomerEntity>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/customers.json");
			
			try {
				List<CustomerEntity> customers = mapper.readValue(inputStream, customerTypeReference);

				for (int i = 0; i < customers.size(); i++) {
					List<CustomerEntity> temp = new ArrayList<CustomerEntity>();
					temp.add(customers.get(i));
					CustomerEntity.reviewlist.add(i, temp);
					
					CustomerEntity.averageRating.add(i, (double) temp.get(0).getRating());
					CustomerEntity.numReviews.add(i, 1);
					CustomerEntity.ratingSum.add(i, temp.get(0).getRating());
				}
				
				//save customers to the database
				customerRepository.saveAll(customers);
				
			} catch (IOException e){
				System.out.println("Unable to save customers: " + e.getMessage());
			}

		};
	}
}
