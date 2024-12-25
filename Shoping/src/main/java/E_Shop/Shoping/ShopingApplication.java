package E_Shop.Shoping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopingApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopingApplication.class, args);
	}

}
