package controllers;

import com.google.gson.Gson;
import entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.CarRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    @Autowired
    CarRepository carRepository;
    Gson gson = new Gson();

    @GetMapping("/car")
    public ResponseEntity<String> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return new ResponseEntity<>(gson.toJson(cars), HttpStatus.ACCEPTED);
    }

    @PostMapping("/car/{brand}/{year}/{km}")
    public ResponseEntity<String> saveCar(@PathVariable String brand, @PathVariable int year, @PathVariable int km) {
        Car car = carRepository.save(new Car(brand, year, km));
        return new ResponseEntity<>(gson.toJson(car), HttpStatus.ACCEPTED);
    }

    @PostMapping("/car/addreview/{carId}/{reviewId}")
    public ResponseEntity<String> addreviewToCar(@PathVariable String carId, @PathVariable String reviewId) {
        Optional<Car> car = carRepository.findById(carId);
        if(car.isPresent()) {
            Car carPojo = car.get();
            carPojo.addReview(reviewId);
            carRepository.save(carPojo);
        }
        return new ResponseEntity<>(gson.toJson(car), HttpStatus.ACCEPTED);
    }
}
