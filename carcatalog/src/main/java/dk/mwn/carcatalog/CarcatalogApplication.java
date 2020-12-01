package dk.mwn.carcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "controllers")
@EnableMongoRepositories(basePackages = "repositories")
public class CarcatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarcatalogApplication.class, args);
    }

}
