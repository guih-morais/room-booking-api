package classroom.scheduler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClassroomSchedulerApplication{
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ClassroomSchedulerApplication.class);
		application.run(args);
	}
}
