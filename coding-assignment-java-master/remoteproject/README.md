# PowerReviews Project
## Overview
Thank you for your interest in PowerReviews and for taking the time to complete this assignment.  We have provided a Java, Spring-Boot application that implements initial functionality to allow us to save, update, and view a restaurant.  We would like you to review the project as it currently stands to extend the application to allow us to collect and retrieve restaurant reviews.
## Code Enhancements
### Background
As a SaaS company, I would like to have a web application that can return ratings and reviews for restaurants. A review contains a user's name, comments, a rating and a date. The responses from this application will be consumed by either front-end or server applications.
### Requirements
Provided in this repository are JSON files to represent an initial set of restaurants and users, and code to import these into an in-memory database (H2 has been provided but feel free to use another one if you like).  We would like to add these endpoints to our application:

   - Endpoint to add a review to a restaurant.  
   - Endpoint to return all reviews for a restaurant.
   - Extend endpont returning a specific restaurant to also return its average review rating (no need to return the reviews for that restaurant).

Reviews should consist of a rating, comments, user name, and time that the review was submitted. 

Perform data validation on the reviews that are received. Error messages from the application should clearly identify any data constraint/integrity issues:
 - Review comments should not exceed 200 characters and cannot contain any of the following words:
    - lit, hella, chill, bro
 - Ratings should be between 1-5.
 - Restaurants must exist in the database.
 
## Runtime
The application should support curl requests against any of the API endpoints. Please provide example curl requests with 
the data you used to test/run the application.
Example:

```
bash
curl -X POST "http://localhost:8080/restaurant/1/reviews" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"comments\": \"this is a test review\", \"rating\": 5, \"user\": \"ryan\"}""
curl -X GET "http://localhost:8080/reviews" -H "accept: application/json"
```

Note - we have enabled swagger to auto-generate a testing page.  Assuming the application has been restarted with your changes in place, this can be accessed via http://localhost:8080/swagger-ui.html.

## How to return the project to us
Please commit your code directly to your gitlab repository - it is a fork of the original assessment.