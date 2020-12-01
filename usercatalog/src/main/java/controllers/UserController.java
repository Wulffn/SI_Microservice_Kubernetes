package controllers;

import com.google.gson.Gson;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    Gson gson = new Gson();

    @GetMapping("/user")
    public ResponseEntity<String> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(gson.toJson(users), HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/{name}")
    public ResponseEntity<String> saveUser(@PathVariable String name) {
        User user = userRepository.save(new User(name));
        return new ResponseEntity<>(gson.toJson(user), HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/addreview/{userId}/{reviewId}")
    public ResponseEntity<String> addreviewToCar(@PathVariable String userId, @PathVariable String reviewId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User userPojo = user.get();
            userPojo.addReview(reviewId);
            userRepository.save(userPojo);
        }
        return new ResponseEntity<>(gson.toJson(user), HttpStatus.ACCEPTED);
    }
}
