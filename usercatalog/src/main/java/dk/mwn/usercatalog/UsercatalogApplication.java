package dk.mwn.usercatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "controllers")
@EnableMongoRepositories(basePackages = "repositories")
public class UsercatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsercatalogApplication.class, args);
    }

}
