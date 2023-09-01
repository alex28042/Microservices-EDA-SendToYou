package send.toyou.schedulertaskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import send.toyou.schedulertaskmanager.application.service.impl.LaunchServiceImpl;

@SpringBootApplication
public class SchedulerTaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerTaskManagerApplication.class, args)
				.getBean(LaunchServiceImpl.class).launchForgottenTasks().subscribe();
	}

}
