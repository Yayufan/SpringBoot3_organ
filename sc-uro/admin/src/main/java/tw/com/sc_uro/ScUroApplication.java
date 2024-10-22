package tw.com.sc_uro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("tw.com.sc_uro")
@SpringBootApplication
public class ScUroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScUroApplication.class, args);
	}

}
