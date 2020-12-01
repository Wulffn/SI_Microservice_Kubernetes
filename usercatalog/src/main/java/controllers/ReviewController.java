package controllers;

import com.google.gson.Gson;
import entities.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.ReviewRepository;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;
    Gson gson = new Gson();

    @PostMapping("/addreview/{userId}/{carId}")
    public ResponseEntity<String> addReview(@PathVariable String userId, @PathVariable String carId, @RequestBody String text) {
        Review review = reviewRepository.save(new Review(text, carId, userId));
        return new ResponseEntity<>(gson.toJson(review), HttpStatus.ACCEPTED);
    }

    @GetMapping("/review/user/{userId}")
    public ResponseEntity<String> getReviewByUserId(@PathVariable String userId) {
        List<Review> reviews = reviewRepository.findAllByUserId(userId);
        return new ResponseEntity<>(gson.toJson(reviews), HttpStatus.ACCEPTED);
    }


    @GetMapping("/review/car/{carId}")
    public ResponseEntity<String> getReviewByCarId(@PathVariable String carId) {
        List<Review> reviews = reviewRepository.findAllByCarId(carId);
        return new ResponseEntity<>(gson.toJson(reviews), HttpStatus.ACCEPTED);
    }


}
