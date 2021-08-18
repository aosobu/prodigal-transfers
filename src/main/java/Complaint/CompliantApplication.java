package Complaint;

import Complaint.service.PerformCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CompliantApplication implements CommandLineRunner{

	public PerformCommandService performCommandService;

	public static void main(String[] args) {
		SpringApplication.run(CompliantApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        //performCommandService.executeCode(" Digicore Engineering Team!!!");
    }

    @Autowired
    public void setPerformCommandService(PerformCommandService performCommandService) {
        this.performCommandService = performCommandService;
    }
}
