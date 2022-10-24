package com.example.myproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MyprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyprojectApplication.class, args);
	}

	@Bean
	CommandLineRunner run(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address("Vietnam", "TP.HCM", "0555");
			String email = "abcde@gmail.com";
			Student student = new Student("Bao", "Nguyen", "abcde@gmail.com", Gender.MALE, address, List.of("Math","English"), BigDecimal.TEN,LocalDateTime.now());

			//usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);
			studentRepository.findStudentByEmail(email)
					.ifPresentOrElse(s -> System.out.println(s + " already existed!"),
							() -> {
								studentRepository.insert(student);
								System.out.println("Inserted " + student);
							});

		};
	}

	private static void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.isEmpty()) {
			studentRepository.insert(student);
			System.out.println("Inserted" + student);
		}
		else {
			System.out.println(student + " already existed!");
		}
	}
}
