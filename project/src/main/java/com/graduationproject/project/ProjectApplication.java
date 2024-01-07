package com.graduationproject.project;








import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cache.annotation.EnableCaching;

import com.graduationproject.project.tfa.TfaService;

import lombok.RequiredArgsConstructor;



@SpringBootApplication
// @EnableCaching
@RequiredArgsConstructor
public class ProjectApplication implements CommandLineRunner
{
	private final TfaService tfaService;
	public static void main(String[] args) {
	SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		

		System.out.println(tfaService.emailOTP(tfaService.generateNewSecret()));
	}

}
