package entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "review")
public class Review {

    private String id, text, carId, userId;

    public Review(String text, String carId, String userId) {
        this.text = text;
        this.carId = carId;
        this.userId = userId;
    }
}
