package david_seu.your_anime_list_backend;

import david_seu.your_anime_list_backend.service.IDataService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YourAnimeListBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(YourAnimeListBackendApplication.class, args);
	}
//
//	@Bean
//	public ApplicationRunner applicationRunner(IDataService dataService) {
//        return args -> dataService.addAnimesFromJsonFileToDatabase();
//	}

}
