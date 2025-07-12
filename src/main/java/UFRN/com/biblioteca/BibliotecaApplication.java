package UFRN.com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import UFRN.com.biblioteca.config.BibliotecaProperties;

@SpringBootApplication
@EnableConfigurationProperties(BibliotecaProperties.class)
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}
