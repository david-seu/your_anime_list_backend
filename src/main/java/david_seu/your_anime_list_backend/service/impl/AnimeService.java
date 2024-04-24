package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.CustomFaker;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.repo.IEpisodeRepo;
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

    @Override
    public AnimeDto createAnime(AnimeDto animeDto) {
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
    public List<AnimeDto> getAllAnime(Integer page){
        Integer totalPages = animeRepo.findAll(PageRequest.of(0,10)).getTotalPages();
        if(page < 0){
            page = totalPages - page;
        }
        int pageToGet = page % totalPages;
        List<Anime> animeList = animeRepo.findAll(PageRequest.of(pageToGet,10)).getContent();
//        return animeList.stream().map(AnimeMapper::mapToAnimeDto).sorted(Comparator.comparing(AnimeDto::getScore)).collect(Collectors.toList());
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
        Anime anime = new Anime(null,
                "Naruto",
                8,
                true
        );

        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime, 0);
    }

    @Override
    public AnimeDto getAnimeByTitle(String title) {
        List<Anime> animeList = animeRepo.findAll();
        for (Anime anime : animeList) {
            if (anime.getTitle().toLowerCase().strip().equals(title.toLowerCase().strip())) {
                return AnimeMapper.mapToAnimeDto(anime, 0);
            }
        }
        throw new ResourceNotFoundException("Anime does not exist with given title: " + title);
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
