package com.graduationproject.project;





import org.springframework.boot.CommandLineRunner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ProjectApplication 
implements CommandLineRunner
{
// private final UserRepository userRepository;
// private final ProjectRepository projectRepository;
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// 	User user = User.builder()
		// 	.firstName("Waleed")
		// 	.lastName("Osailan")
		// 	.username("Tu4107673")
		// 	.email("TU4107673@Taibahu.edu.sa")// This can be checked with a Regex
		// 	.premium(null)// If a user doesn't have Premium this will null otherwise the expiration date  
		// 	.role(Role.CLIENT)// This will be the divider between Clients and Admins
		// 	.whenCreated(new Date())// This will be excuted once(a new account created)
		// 	.password("Anything")// This will Hashed later
		// 	.build();
		// 	userRepository.save(user);
		// User user = 
		// Project project=Project
		// .builder()
		// .query("جدن")
		// .response("جداً")
		// .whenMade(new Date())
		// .user(user)
		// .build();
		// projectRepository.save(project);

		
	}




}
