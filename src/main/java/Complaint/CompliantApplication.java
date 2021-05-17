package Complaint;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CompliantApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CompliantApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Stared Here and Now .....");
	}
}
