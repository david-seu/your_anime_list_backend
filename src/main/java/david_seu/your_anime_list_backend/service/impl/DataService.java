package david_seu.your_anime_list_backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.model.utils.AnimeStatus;
import david_seu.your_anime_list_backend.model.utils.ERole;
import david_seu.your_anime_list_backend.model.faker.CustomFaker;
import david_seu.your_anime_list_backend.model.utils.JsonAnime;
import david_seu.your_anime_list_backend.model.utils.Season;
import david_seu.your_anime_list_backend.model.utils.Type;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.repo.*;
import david_seu.your_anime_list_backend.service.IAnimeService;
import david_seu.your_anime_list_backend.service.IDataService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class DataService implements IDataService {

    IAnimeService animeService;
    IUserService userService;
    IAnimeRepo animeRepo;
    IUserRepo userRepo;
    IAnimeSeasonRepo animeSeasonRepo;
    ITagRepo tagRepo;
    IGenreRepo genreRepo;
    IStudioRepo studioRepo;

    @Override
    public void generateFakeData() throws IOException, URISyntaxException {


          generateFakeUsers();
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
            user.setJoinDate(new Date());
            int choice = random.nextInt(2);
            switch (choice) {
                case 0:
                    user.setRole(ERole.ROLE_USER);
                    break;
                case 2:
                    user.setRole(ERole.ROLE_MANAGER);
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
                int score;
                score = faker.number().numberBetween(5, 10);
                AnimeDto animeDto = new AnimeDto();
                animeDto.setTitle(title);
                animeDto.setScore((double) score);
                animeDto.setUser(user);
                animeService.createAnime(animeDto);
            }
        }
    }

//    private void generateFakeEpisodes() {
//        CustomFaker faker = new CustomFaker();
//        for(Anime anime : animeRepo.findAll()) {
//            System.out.println("Generating episodes for anime with user: " + anime.getUser().getUsername());
//            Random random = new Random();
//            int numberOfSeasons = random.nextInt(2) + 1;
//            int numberOfEpisodes = random.nextInt(0,1);
//            if(numberOfEpisodes == 0) {
//                numberOfEpisodes = 12;
//            }
//            else{
//                numberOfEpisodes = 24;
//            }
//
//            for(int i = 0; i < numberOfSeasons; i++) {
//                for (int j = 0; j < numberOfEpisodes; j++) {
//                    int episodeNumber = j + 1;
//                    int seasonNumber = i + 1;
//                    String animeTitle = anime.getTitle();
//                    String title = animeTitle + " " + seasonNumber + "_" + episodeNumber;
//                    Boolean watched = faker.bool().bool();
//                    int score;
//                    if (watched) {
//                        score = faker.number().numberBetween(5, 10);
//                    } else {
//                        score = -1;
//                    }
//                    EpisodeDto episodeDto = new EpisodeDto();
//                    episodeDto.setTitle(title);
//                    episodeDto.setNumber(episodeNumber);
//                    episodeDto.setSeason(seasonNumber);
//                    episodeDto.setScore(score);
//                    episodeDto.setWatched(watched);
//                    episodeDto.setAnime(anime);
//                    episodeService.createEpisode(episodeDto);
//                }
//            }
//        }
//    }

    public void addAnimesFromJsonFileToDatabase() {
        User user = userRepo.findByUsername("admin");
        if(user == null) {
            addUser();
            user = userRepo.findByUsername("admin");
        }
        String jsonFilePath = "/anime-database.json";

        // Create Maps for faster lookup
        Map<String, Tag> tagMap = tagRepo.findAll().stream().collect(Collectors.toMap(Tag::getName, Function.identity()));
        Map<String, Genre> genreMap = genreRepo.findAll().stream().collect(Collectors.toMap(Genre::getName, Function.identity()));
        Map<String, Studio> studioMap = studioRepo.findAll().stream().collect(Collectors.toMap(Studio::getName, Function.identity()));
        Map<Integer, Anime> animeMap = animeRepo.findAll().stream().collect(Collectors.toMap(Anime::getMalId, Function.identity()));
        Map<String, AnimeSeason> animeSeasonMap = animeSeasonRepo.findAll().stream().collect(Collectors.toMap(a -> a.getYear() + "_" + a.getSeason(), Function.identity()));

        // Create lists for batch saving
        List<Tag> tagsToSave = new ArrayList<>();
        List<Genre> genresToSave = new ArrayList<>();
        List<Studio> studiosToSave = new ArrayList<>();
        List<AnimeSeason> animeSeasonsToSave = new ArrayList<>();
        List<Anime> animesToSave = new ArrayList<>();

        try {
            // Create ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Read JSON file into list of JsonAnime objects
            InputStream inputStream = getClass().getResourceAsStream(jsonFilePath);
            JsonNode jsonNode = mapper.readTree(inputStream);
            List<JsonAnime> jsonAnimes = mapper.readValue(jsonNode.toString(), new TypeReference<>() {});

            // Convert each JsonAnime to Anime and save to database
            Integer counter = 0;
            for (JsonAnime jsonAnime : jsonAnimes) {
                Anime anime = animeMap.getOrDefault(jsonAnime.getId(), new Anime());
                if(anime.getMalId() != null) {
                    continue;
                }
                animeMap.put(jsonAnime.getId(), anime);
                anime.setMalId(jsonAnime.getId());
                if(jsonAnime.getTitle().equals("(Title to be Announced)")) {
                    if(!jsonAnime.getSynonyms().isEmpty()) {
                        anime.setTitle(jsonAnime.getSynonyms().get(0));
                    }
                }
                else {
                    anime.setTitle(jsonAnime.getTitle());
                }

                anime.setSynopsis(jsonAnime.getSynopsis());
                anime.setScore(jsonAnime.getMean());
                anime.setStartDate(jsonAnime.getStartDate());
                anime.setEndDate(jsonAnime.getEndDate());
                anime.setWatching(jsonAnime.getWatching());
                anime.setCompleted(jsonAnime.getCompleted());
                anime.setOnHold(jsonAnime.getOnHold());
                anime.setDropped(jsonAnime.getDropped());
                anime.setPlanToWatch(jsonAnime.getPlanToWatch());
                anime.setPopularity(jsonAnime.getPopularity());
                anime.setType(Type.valueOf(jsonAnime.getType().toUpperCase()));
                anime.setStatus(AnimeStatus.valueOf(jsonAnime.getStatus().toUpperCase()));
                anime.setNrEpisodes(jsonAnime.getEpisodes());
                anime.setPictureURL(jsonAnime.getPicture());
                anime.setThumbnailURL(jsonAnime.getThumbnail());

                // Handle tags
                for (String tagName : jsonAnime.getTags()) {
                    Tag tag = tagMap.getOrDefault(tagName, new Tag());
                    if (tag.getName() == null) {
                        tag.setName(tagName);
                        tagsToSave.add(tag);
                        tagMap.put(tagName, tag);
                    }
                    anime.getTags().add(tag);
                }

                // Handle genres
                for (String genreName : jsonAnime.getGenre()) {
                    Genre genre = genreMap.getOrDefault(genreName, new Genre());
                    if (genre.getName() == null) {
                        genre.setName(genreName);
                        genresToSave.add(genre);
                        genreMap.put(genreName, genre);
                    }
                    anime.getGenres().add(genre);
                }

                // Handle studios
                for (String studioName : jsonAnime.getStudios()) {
                    Studio studio = studioMap.getOrDefault(studioName, new Studio());
                    if (studio.getName() == null) {
                        studio.setName(studioName);
                        studiosToSave.add(studio);
                        studioMap.put(studioName, studio);
                    }
                    anime.getStudios().add(studio);
                }

//                // Handle relatedAnime
//                for (Integer relatedAnimeId : jsonAnime.getRelatedAnime()) {
//                    Anime relatedAnime = animeMap.getOrDefault(relatedAnimeId, new Anime());
//                    if (relatedAnime.getMalId() == null) {
//                        relatedAnime.setMalId(relatedAnimeId);
//                        animeMap.put(relatedAnimeId, relatedAnime);
//                        animeRepo.save(relatedAnime);
//                    }
//                    anime.getRelatedAnime().add(relatedAnime);
//                }
//
//                // Handle recommendations
//                for (Integer recommendedAnimeId : jsonAnime.getRecommendations()) {
//                    Anime recommendedAnime = animeMap.getOrDefault(recommendedAnimeId, new Anime());
//                    if (recommendedAnime.getMalId() == null) {
//                        recommendedAnime.setMalId(recommendedAnimeId);
//                        animeMap.put(recommendedAnimeId, recommendedAnime);
//                        animeRepo.save(recommendedAnime);
//                    }
//                    anime.getRecommendedAnime().add(recommendedAnime);
//                }

                // Check if AnimeSeason already exists
                Season season = Season.valueOf(jsonAnime.getSeason().getSeason().toUpperCase());
                Integer year = jsonAnime.getSeason().getYear();
                String seasonKey = year + "_" + season;
                AnimeSeason animeSeason = animeSeasonMap.getOrDefault(seasonKey, new AnimeSeason());
                if (animeSeason.getYear() == null) {
                    animeSeason.setSeason(season);
                    animeSeason.setYear(year);
                    animeSeasonsToSave.add(animeSeason);
                    animeSeasonMap.put(seasonKey, animeSeason);
                }
                anime.setAnimeSeason(animeSeason);

                anime.setUser(user);

                // Add anime to list
                animesToSave.add(anime);

                counter++;

                // Save in batches of 100
                if(animesToSave.size() % 100 == 0) {
                    tagRepo.saveAll(tagsToSave);
                    genreRepo.saveAll(genresToSave);
                    studioRepo.saveAll(studiosToSave);
                    animeSeasonRepo.saveAll(animeSeasonsToSave);
                    animeRepo.saveAll(animesToSave);
                    System.out.println("Added " + counter + " anime to the database");
                    tagsToSave.clear();
                    genresToSave.clear();
                    studiosToSave.clear();
                    animeSeasonsToSave.clear();
                    animesToSave.clear();
                }
            }

            // Save remaining animes
            if(!animesToSave.isEmpty()) {
                animeRepo.saveAll(animesToSave);
                System.out.println("Added " + counter + " anime to the database");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addUser()
    {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("seu21@outlook.com");
        user.setPassword("admin");
        user.setEnabled(true);
        user.setRole(ERole.ROLE_ADMIN);
        userRepo.save(user);
    }

    public void addRecommendedAnimeAndRelatedAnime()
    {
        String jsonFilePath = "/anime-database.json";

        // Create lists for batch saving
        List<Anime> animesToSave = new ArrayList<>();
        Map<Integer, Anime> animeMap = animeRepo.findAll().stream().collect(Collectors.toMap(Anime::getMalId, Function.identity()));

        try {
            // Create ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Read JSON file into list of JsonAnime objects
            InputStream inputStream = getClass().getResourceAsStream(jsonFilePath);
            JsonNode jsonNode = mapper.readTree(inputStream);
            List<JsonAnime> jsonAnimes = mapper.readValue(jsonNode.toString(), new TypeReference<>() {});


            // Convert each JsonAnime to Anime and save to database
            Integer counter = 0;
            for (JsonAnime jsonAnime : jsonAnimes) {
                Anime anime = animeMap.get(jsonAnime.getId());
                // Handle relatedAnime
                for (Integer relatedAnimeId : jsonAnime.getRelatedAnime()) {
                    Anime relatedAnime = animeMap.get(relatedAnimeId);
                    if(relatedAnime == null) {
                        System.out.println("Related anime with mal_id " + relatedAnimeId + " not found");
                        continue;
                    }
                    anime.getRelatedAnime().add(relatedAnime);
                }

                // Handle recommendations
                for (Integer recommendedAnimeId : jsonAnime.getRecommendations()) {
                    Anime recommendedAnime = animeMap.get(recommendedAnimeId);
                    if(recommendedAnime == null) {
                        System.out.println("Recommended anime with mal_id " + recommendedAnimeId + " not found");
                        continue;
                    }
                    anime.getRecommendedAnime().add(recommendedAnime);
                }

                // Add anime to list
                animesToSave.add(anime);

                counter++;

                // Save in batches of 100
                if(animesToSave.size() % 100 == 0) {
                    animeRepo.saveAll(animesToSave);
                    System.out.println("Added " + counter + " anime to the database");
                    animesToSave.clear();
                }
            }

            // Save remaining animes
            if(!animesToSave.isEmpty()) {
                animeRepo.saveAll(animesToSave);
                System.out.println("Added " + counter + " anime to the database");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
