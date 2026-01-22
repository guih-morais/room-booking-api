package classroom.scheduler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassroomSchedulerApplication{
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ClassroomSchedulerApplication.class);
		application.run(args);
	}
}
