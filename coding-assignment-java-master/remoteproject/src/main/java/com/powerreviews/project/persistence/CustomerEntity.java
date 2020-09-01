package com.powerreviews.project.persistence;

import java.util.*;
import javax.persistence.*;

@Entity(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private Integer rating; //must be 1-5

    @Column
    private String username;

    @Column
    private String comment;
    
    @Column
    private String date;
    
    @Column
    private String time;
    
    @Column
	public static List<List<CustomerEntity>> reviewlist = new ArrayList<List<CustomerEntity>>();
    
    @Column
    public static List<Double> averageRating = new ArrayList<Double>();
    
    @Column
    public static List<Integer> numReviews = new ArrayList<Integer>();
    
    @Column
    public static List<Integer> ratingSum = new ArrayList<Integer>();

    public CustomerEntity(){}

    public CustomerEntity(Integer id, Integer rating, String username, String comment, String date, String time) {
        this.id = id;
        this.rating = rating;
        this.username = username;
        this.comment = comment;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
    	this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
    	this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getTime() {
    	return time;
    }
    
    public void setTime(String time) {
    	this.time = time;
    }
    
    public static List<List<CustomerEntity>> getReviewlist() {
		return reviewlist;
	}

	public static void setReviewlist(List<List<CustomerEntity>> reviewlist) {
		CustomerEntity.reviewlist = reviewlist;
	}
	
	public static List<Double> getAverageRating() {
		return averageRating;
	}
	
	public static void setAverageRating(List<Double> averageRating) {
		CustomerEntity.averageRating = averageRating;
	}
	
	public static List<Integer> getNumReviews() {
		return numReviews;
	}
	
	public static void setNumReviews(List<Integer> numReviews) {
		CustomerEntity.numReviews = numReviews;
	}
	
	public static List<Integer> getRatingSum() {
		return ratingSum;
	}
	
	public static void setRatingSum(List<Integer> ratingSum) {
		CustomerEntity.ratingSum = ratingSum;
	}

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                "rating='" + rating + '\'' +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}
    	if (o == null || getClass() != o.getClass()) {
    		return false;
    	}
    	
    	CustomerEntity customer = (CustomerEntity) o;
    	
    	return id != null ? id.equals(customer.id) : customer.id == null;
    }
    
    @Override
    public int hashCode() {
    	return id != null ? id.hashCode() : 0;
    }
}