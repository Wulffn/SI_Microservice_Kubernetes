package entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@ToString
@Document(collection = "user")
public class User {

    private String id;
    private String name;
    private List<String> reviews = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }


    public void addReview(String reviewId) {
        reviews.add(reviewId);
    }
}
