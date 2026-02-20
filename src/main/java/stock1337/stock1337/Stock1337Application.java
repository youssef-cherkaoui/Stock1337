package stock1337.stock1337;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("stock1337.stock1337.Model")
public class Stock1337Application {

	public static void main(String[] args) {
		SpringApplication.run(Stock1337Application.class, args);
	}

}
