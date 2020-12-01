package dk.mwn.cargateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
public class Controller {

    @Autowired
    private DiscoveryClient discoveryClient;
    Gson gson = new Gson();

    @GetMapping("/car")
    @HystrixCommand(fallbackMethod = "defaultCars")
    public ResponseEntity<String> getCars() {
        String response = getResponseFromService("car-catalog", "/car", HttpMethod.GET, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/car/{brand}/{year}/{km}")
    @HystrixCommand(fallbackMethod = "defaultCars")
    public ResponseEntity<String> addCar(@PathVariable String brand, @PathVariable int year, @PathVariable int km) {
        String endpoint = String.format("/car/%s/%s/%s", brand, year, km);
        String response = getResponseFromService("car-catalog", endpoint, HttpMethod.POST, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user")
    @HystrixCommand(fallbackMethod = "defaultUsers")
    public ResponseEntity<String> getUsers() {
        String response = getResponseFromService("user-catalog", "/user", HttpMethod.GET, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/{name}")
    @HystrixCommand(fallbackMethod = "defaultUsers")
    public ResponseEntity<String> addUser(@PathVariable String name) {
        String endpoint = String.format("/user/%s", name);
        String response = getResponseFromService("car-catalog", endpoint, HttpMethod.POST, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/addreview/{userId}/{carId}")
    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<String> addReview(@PathVariable String userId, @PathVariable String carId, @RequestBody String text) {
        String endpoint = String.format("/addreview/%s/%s", userId, carId);
        String response = getResponseFromService("user-catalog", endpoint, HttpMethod.POST, text);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        String reviewId = jsonObject.get("id").getAsString();

        endpoint = String.format("/car/addreview/%s/%s", carId, reviewId);
        getResponseFromService("car-catalog", endpoint, HttpMethod.POST, null);

        endpoint = String.format("/user/addreview/%s/%s", userId, reviewId);
        getResponseFromService("user-catalog", endpoint, HttpMethod.POST, null);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/review/user/{userId}")
    public ResponseEntity<String> getReviewByUserId(@PathVariable String userId) {
        String endpoint = String.format("/review/user/%s", userId);
        String response = getResponseFromService("user-catalog", endpoint, HttpMethod.GET, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    @GetMapping("/review/car/{carId}")
    public ResponseEntity<String> getReviewByCarId(@PathVariable String carId) {
        String endpoint = String.format("/review/car/%s", carId);
        String response = getResponseFromService("user-catalog", endpoint, HttpMethod.GET, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private String getResponseFromService(String service, String endpoint, HttpMethod method, String body) {
        List<ServiceInstance> services = discoveryClient.getInstances(service);
        String response = null;
        if (services.size() > 0) {
            String url = services.get(0).getUri().toString();
            url = url + endpoint;
            RestTemplate restTemplate = new RestTemplate();
            switch(method) {
                case GET:
                    response = restTemplate.getForEntity(url, String.class).getBody();
                    break;
                case POST:
                    response = restTemplate.postForEntity(url, body, String.class).getBody();
                    break;
            }

        }
        return response;
    }

    private ResponseEntity<String> defaultCars() {
        return new ResponseEntity<>("No service available", HttpStatus.ACCEPTED);
    }

    private ResponseEntity<String> defaultUsers() {
        return new ResponseEntity<>("No service available", HttpStatus.ACCEPTED);
    }

    private ResponseEntity<String> defaultReviews() {
        return new ResponseEntity<>("No service available", HttpStatus.ACCEPTED);
    }

}
