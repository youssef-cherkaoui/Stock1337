package stock1337.stock1337;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("stock1337.stock1337.model")
@EnableJpaRepositories("stock1337.stock1337.repository")
public class Stock1337Application {

	public static void main(String[] args) {
		System.out.println("hello world");
		SpringApplication.run(Stock1337Application.class, args);
	}

}
