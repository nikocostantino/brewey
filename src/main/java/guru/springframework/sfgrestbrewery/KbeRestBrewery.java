package guru.springframework.sfgrestbrewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class KbeRestBrewery {

	public static void main(String[] args) {
		SpringApplication.run(KbeRestBrewery.class, args);
	}

}
