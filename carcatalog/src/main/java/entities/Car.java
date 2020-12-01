package entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "car")
public class Car {

    private String brand, id;
    private int year, km;
    private List<String> carReviews = new ArrayList<>();

    public Car(String brand, int year, int km) {
        this.brand = brand;
        this.year = year;
        this.km = km;
    }

    public void addReview(String reviewId) {
        carReviews.add(reviewId);
    }
}
