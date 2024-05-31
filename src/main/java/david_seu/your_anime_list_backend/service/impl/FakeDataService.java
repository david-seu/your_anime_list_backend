package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.ERole;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.model.faker.CustomFaker;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.payload.dto.EpisodeDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.repo.IUserRepo;
import david_seu.your_anime_list_backend.service.IAnimeService;
import david_seu.your_anime_list_backend.service.IEpisodeService;
import david_seu.your_anime_list_backend.service.IFakeDataService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;


@Service
@AllArgsConstructor
public class FakeDataService  implements IFakeDataService {

    IAnimeService animeService;
    IEpisodeService episodeService;
    IUserService userService;
    IAnimeRepo animeRepo;
    IUserRepo userRepo;

    @Override
    public void generateFakeData() throws IOException, URISyntaxException {


//        generateFakeUsers();
//        generateFakeAnime();
//        generateFakeEpisodes();

    }

    private void generateFakeUsers() throws IOException {
        CustomFaker faker = new CustomFaker();
        Random random = new Random();
        FileWriter fileWriter = new FileWriter(Paths.get("src/main/resources/passwords.txt").toString());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("Username: Password");
        bufferedWriter.newLine();

        for (int i = 0; i < 100; i++) {
            if((i+1) % 10 == 0) {
                System.out.println("Generated " + (i+1) + " users");
            }
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();

            bufferedWriter.write(username + " " + password);
            bufferedWriter.newLine();

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setEnabled(true);
            int choice = random.nextInt(2);
            switch (choice) {
                case 0:
                    user.setRole(ERole.ROLE_MANAGER);
                    break;
                case 1:
                    user.setRole(ERole.ROLE_ADMIN);
                    break;
            }
            UserDto userDto = UserMapper.mapToUserDto(user);
            userService.addUser(userDto);
        }
        bufferedWriter.close();

    }

    private void generateFakeAnime()
    {
        CustomFaker faker = new CustomFaker();
        List<User> users = userRepo.findAll();
        int counter = 0;
        for(User user : users) {
            System.out.println(counter + ": Generating anime for user " + user.getUsername());
            for(int i = 0; i < 1000; i++) {
                if((i+1) % 100 == 0) {
                    System.out.println("Generated " + (i+1) + " anime");
                }
                String title = faker.anime().nextAnimeTitle();
                Boolean watched = faker.bool().bool();
                int score;
                if (watched) {
                    score = faker.number().numberBetween(5, 10);
                } else {
                    score = -1;
                }
                AnimeDto animeDto = new AnimeDto();
                animeDto.setTitle(title);
                animeDto.setScore(score);
                animeDto.setWatched(watched);
                animeDto.setUser(user);
                animeService.createAnime(animeDto);
            }
        }
    }

    private void generateFakeEpisodes() {
        CustomFaker faker = new CustomFaker();
        for(Anime anime : animeRepo.findAll()) {
            System.out.println("Generating episodes for anime with user: " + anime.getUser().getUsername());
            Random random = new Random();
            int numberOfSeasons = random.nextInt(2) + 1;
            int numberOfEpisodes = random.nextInt(0,1);
            if(numberOfEpisodes == 0) {
                numberOfEpisodes = 12;
            }
            else{
                numberOfEpisodes = 24;
            }

            for(int i = 0; i < numberOfSeasons; i++) {
                for (int j = 0; j < numberOfEpisodes; j++) {
                    int episodeNumber = j + 1;
                    int seasonNumber = i + 1;
                    String animeTitle = anime.getTitle();
                    String title = animeTitle + " " + seasonNumber + "_" + episodeNumber;
                    Boolean watched = faker.bool().bool();
                    int score;
                    if (watched) {
                        score = faker.number().numberBetween(5, 10);
                    } else {
                        score = -1;
                    }
                    EpisodeDto episodeDto = new EpisodeDto();
                    episodeDto.setTitle(title);
                    episodeDto.setNumber(episodeNumber);
                    episodeDto.setSeason(seasonNumber);
                    episodeDto.setScore(score);
                    episodeDto.setWatched(watched);
                    episodeDto.setAnime(anime);
                    episodeService.createEpisode(episodeDto);
                }
            }
        }
    }
}
