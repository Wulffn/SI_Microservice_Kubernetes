package repositories;

import entities.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Review save(Review r);
    List<Review> findAllByCarId(String carId);
    List<Review> findAllByUserId(String userId);
}
