package Complaint;

import Complaint.enums.ApprovalStatus;
import Complaint.enums.TransferRecallType;
import Complaint.model.*;
import Complaint.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;

import java.util.Date;

@SpringBootApplication
public class CompliantApplication {
	public static void main(String[] args) {
		SpringApplication.run(CompliantApplication.class, args);
	}
}
