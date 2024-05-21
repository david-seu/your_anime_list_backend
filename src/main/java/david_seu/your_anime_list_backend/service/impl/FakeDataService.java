package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.model.CustomFaker;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.service.IAnimeService;
import david_seu.your_anime_list_backend.service.IEpisodeService;
import david_seu.your_anime_list_backend.service.IFakeDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FakeDataService  implements IFakeDataService {

    IAnimeService animeService;
    IEpisodeService episodeService;
    @Override
    public void generateFakeData() {
        CustomFaker faker = new CustomFaker();
//        for (int i = 0; i < 15000; i++) {
//            String title = faker.anime().nextAnimeTitle();
//            Boolean watched = faker.bool().bool();
//            int score;
//            if(watched){
//                score= faker.number().numberBetween(5, 10);
//            } else {
//                score = -1;
//            }
//            AnimeDto animeDto = new AnimeDto();
//            animeDto.setTitle(title);
//            animeDto.setScore(score);
//            animeDto.setWatched(watched);
//            animeService.createAnime(animeDto);
//        }
//
//        for(AnimeDto animeDto : animeService.getAllAnime()) {
//            Random random = new Random();
//            int numberOfSeasons = random.nextInt(3) + 1;
//            int numberOfEpisodes = random.nextInt(0,1);
//            if(numberOfEpisodes == 0) {
//                numberOfEpisodes = 12;
//            }
//            else{
//                numberOfEpisodes = 24;
//            }
//            for(int i = 0; i < numberOfSeasons; i++) {
//                for (int j = 0; j < numberOfEpisodes; j++) {
//                    int episodeNumber = j + 1;
//                    int seasonNumber = i + 1;
//                    String animeTitle = animeDto.getTitle();
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
//                    episodeDto.setAnime(AnimeMapper.mapToAnime(animeDto));
//                    episodeService.createEpisode(episodeDto);
//                }
//            }
//        }
//
    }
}
