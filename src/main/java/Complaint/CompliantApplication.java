package Complaint;

import Complaint.model.BranchUser;
import Complaint.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
