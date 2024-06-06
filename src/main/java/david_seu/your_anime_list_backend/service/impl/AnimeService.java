package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.exception.InvalidAnimeException;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.repo.IEpisodeRepo;
import david_seu.your_anime_list_backend.repo.IUserRepo;
import david_seu.your_anime_list_backend.service.IAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AnimeService implements IAnimeService {

    private IAnimeRepo animeRepo;
    private IEpisodeRepo episodeRepo;
    private IUserRepo userRepo;

    @Override
    public AnimeDto createAnime(AnimeDto animeDto) {
        if(animeDto.getScore() < 0 || animeDto.getScore() > 10){
            throw new InvalidAnimeException("Score must be between 0 and 10");
        }
        Anime anime = AnimeMapper.mapToAnime(animeDto);
        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime, 0);
    }

    @Override
    public AnimeDto getAnimeById(Long animeId) {
        Anime anime = animeRepo.findById(animeId).
                orElseThrow(() ->
                        new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();
        return AnimeMapper.mapToAnimeDto(anime, numEpisodes);
    }

    @Override
    public List<AnimeDto> getAllAnime(Integer page, String title, String sort){
        System.out.println(title);
        List<Anime> animeList;
        if(sort.equals("ASC"))
            animeList = animeRepo.findByTitleContainingIgnoreCaseOrderByIdAsc(title, PageRequest.of(page,10));
        else
            animeList = animeRepo.findByTitleContainingIgnoreCaseOrderByIdDesc(title, PageRequest.of(page,10));
        System.out.println(animeList);
        return animeList.stream().map((anime) -> {
            Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();
            return AnimeMapper.mapToAnimeDto(anime, numEpisodes);
        }).collect(Collectors.toList());
    }


    @Override
    public AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime) {
        Anime anime = animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        anime.setTitle(updatedAnime.getTitle());
        anime.setScore(updatedAnime.getScore());
        anime.setWatched(updatedAnime.getWatched());

        Anime updatedAnimeObj = animeRepo.save(anime);

        Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();

        return AnimeMapper.mapToAnimeDto(updatedAnimeObj, numEpisodes);
    }

    @Override
    public void deleteAnime(Long animeId) {
        animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));

        animeRepo.deleteById(animeId);
    }

    @Override
    public AnimeDto createAnime() {
        Anime anime = new Anime();
        anime.setWatched(false);
        anime.setTitle("New Anime");
        anime.setScore(10);
        anime.setUser(userRepo.findByUsername("zyk3l"));

        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime, 0);
    }

    @Override
    public AnimeDto getAnimeByTitle(String title) {
        Anime anime = animeRepo.findByTitle(title);
        Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();
        return AnimeMapper.mapToAnimeDto(anime, numEpisodes);
    }

    @Override
    public List<Integer> getScoresCount() {
        return animeRepo.findAllGroupByScore();
    }

//    @Override
//    public AnimeDto getRandomAnime() {
//        List<Anime> animeList = animeRepo.findAll();
//        if (animeList.isEmpty()) {
//            throw new ResourceNotFoundException("No animes found");
//        }
//        Anime randomAnime = animeList.get(new Random().nextInt(animeList.size()));
//        return AnimeMapper.mapToAnimeDto(randomAnime);
//    }


}
