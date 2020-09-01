package com.powerreviews.project.controller;

import com.powerreviews.project.persistence.CustomerEntity;
import com.powerreviews.project.persistence.CustomerRepository;
import com.powerreviews.project.persistence.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    public CustomerController(@Autowired CustomerRepository customerRepository, RestaurantRepository restaurantRepository) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerEntity> get(@PathVariable Integer id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        return new ResponseEntity<>(customer, new HttpHeaders(), customer == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerEntity> post(@RequestBody CustomerEntity customer) {
    	if (customer.getId() > restaurantRepository.count()) {
    		System.out.println("You cannot add a customer review to a restaurant that's not in the database.");
    	}
    	else if ((customer.getComment().length() > 200) || (customer.getComment().toLowerCase().contains("lit")) || (customer.getComment().toLowerCase().contains("hella")) ||
    			(customer.getComment().toLowerCase().contains("chill")) || (customer.getComment().toLowerCase().contains("bro"))) {
    		System.out.println("Incorrect guidelines to submit a review! Must keep the comment 200 characters or less. Must also exclude using"
    				+ " the words 'lit', 'hella', 'chill', and 'bro'.");
    	} else if ((customer.getRating() < 1) || (customer.getRating() > 5)) {
    		System.out.println("Incorrect boundaries for your rating! Must keep the rating between 1 and 5.");	
    	} else if (customer.getDate().length() > 10) {
    		System.out.println("Your date should be formatted like 'MM/DD/YYYY'.");
    	} else if (customer.getTime().length() > 7) {
    		System.out.println("The time should be formatted like 'HH:MMam/pm'.");
    	} else if (customer.getUsername().length() > 18) {
    		System.out.println("The username should be less than 18 characters.");
    	} else {
    		int i = customer.getId();
        	CustomerEntity.getReviewlist().get(i-1).add(i-1, customer);
        	
        	CustomerEntity.getRatingSum().add(i-1, CustomerEntity.getRatingSum().get(i-1)+customer.getRating());
        	int sum = CustomerEntity.getRatingSum().get(i-1);
        	CustomerEntity.getNumReviews().add(i-1, CustomerEntity.getNumReviews().get(i-1)+1);
        	int numRatings = CustomerEntity.getNumReviews().get(i-1);
        	double holder = (double)sum/(double)numRatings;
        	holder = Math.round(holder * 100.0) / 100.0;
        	CustomerEntity.getAverageRating().add(i-1, holder);
        			
        	customerRepository.save(customer);
    	}
        return new ResponseEntity<>(customer, new HttpHeaders(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/review", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerEntity> put(@RequestBody CustomerEntity customer) {
        CustomerEntity updated = customerRepository.findById(customer.getId()).orElse(null);
        
    	if (customer.getId() > restaurantRepository.count()) {
    		System.out.println("You cannot add a customer review to a restaurant that's not in the database.");
    	}
    	else if ((customer.getComment().length() > 200) || (customer.getComment().toLowerCase().contains("lit")) || (customer.getComment().toLowerCase().contains("hella")) ||
    			(customer.getComment().toLowerCase().contains("chill")) || (customer.getComment().toLowerCase().contains("bro"))) {
    		System.out.println("Incorrect guidelines to submit a review! Must keep the comment 200 characters or less. Must also exclude using"
    				+ " the words 'lit', 'hella', 'chill', and 'bro'.");
    	} else if ((customer.getRating() < 1) || (customer.getRating() > 5)) {
    		System.out.println("Incorrect boundaries for your rating! Must keep the rating between 1 and 5.");	
    	} else if (customer.getDate().length() > 10) {
    		System.out.println("Your date should be formatted like 'MM/DD/YYYY'.");
    	} else if (customer.getTime().length() > 7) {
    		System.out.println("The time should be formatted like 'HH:MMam/pm'.");
    	} else if (customer.getUsername().length() > 18) {
    		System.out.println("The username should be less than 18 characters.");
    	} else if (updated == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } else {
	    	int i = customer.getId();
	    	Optional<CustomerEntity> test1 = customerRepository.findById(i);
	    	if(test1.isPresent()) {
	    	    CustomerEntity test2 = test1.get();
		    	String name = test2.getUsername();
		    	int deletedrate = 0;
		    	
		    	List<CustomerEntity> temp = CustomerEntity.reviewlist.get(i-1);
		    	int deleted = 0;
		    	for (CustomerEntity temp2 : temp) {
		    		if (temp2.getUsername().equals(name)) {
		    			deleted = temp.indexOf(temp2);
		    			deletedrate = temp2.getRating();
		    		}
		    	}
		    	temp.remove(deleted);
		    	CustomerEntity.getRatingSum().add(i-1, CustomerEntity.getRatingSum().get(i-1)-deletedrate);
            	CustomerEntity.getReviewlist().get(i-1).add(i-1, customer);            	
            	CustomerEntity.getRatingSum().add(i-1, CustomerEntity.getRatingSum().get(i-1)+customer.getRating());

            	int sum = CustomerEntity.getRatingSum().get(i-1);
            	int numRatings = CustomerEntity.getNumReviews().get(i-1);
            	double holder = (double)sum/(double)numRatings;
            	holder = Math.round(holder * 100.0) / 100.0;
            	CustomerEntity.getAverageRating().add(i-1, holder);
	    	}
	    	
	        updated.setRating(customer.getRating());
	        updated.setUsername(customer.getUsername());
	        updated.setComment(customer.getComment());
	        updated.setDate(customer.getDate());
	        updated.setTime(customer.getTime());
	        
	        customerRepository.save(updated);
    	}
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/review/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerEntity> delete(@PathVariable Integer id) {
    	
    	Optional<CustomerEntity> test1 = customerRepository.findById(id);
    	if (test1.isPresent()) {
    		CustomerEntity test2 = test1.get();
	    	String name = test2.getUsername();
	    	int deletedrate = 0;
    	
	    	List<CustomerEntity> temp = CustomerEntity.reviewlist.get(id-1);
	    	int deleted = 0;
	    	for (CustomerEntity temp2 : temp) {
	    		if (temp2.getUsername().equals(name)) {
	    			deleted = temp.indexOf(temp2);
	    			deletedrate = temp2.getRating();
	    		}
	    	}
	    	temp.remove(deleted);
	    	CustomerEntity.getRatingSum().add(id-1, CustomerEntity.getRatingSum().get(id-1)-deletedrate);
	    	CustomerEntity.getNumReviews().add(id-1, CustomerEntity.getNumReviews().get(id-1)-1);
	
	    	int sum = CustomerEntity.getRatingSum().get(id-1);
	    	int numRatings = CustomerEntity.getNumReviews().get(id-1);
	    	double holder = (double)sum/(double)numRatings;
	    	holder = Math.round(holder * 100.0) / 100.0;
	    	CustomerEntity.getAverageRating().add(id-1, holder);
    	}
    	
    	customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
