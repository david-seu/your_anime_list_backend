package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.service.IAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AnimeService implements IAnimeService {

    private IAnimeRepo animeRepo;

    @Override
    public AnimeDto createAnime(AnimeDto animeDto) {
        Anime anime = AnimeMapper.mapToAnime(animeDto);
        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime);
    }

    @Override
    public AnimeDto getAnimeById(Long animeId) {
        Anime anime = animeRepo.findById(animeId).
                orElseThrow(() ->
                        new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        return AnimeMapper.mapToAnimeDto(anime);
    }

    @Override
    public List<AnimeDto> getAllAnime() {
        List<Anime> animeList = animeRepo.findAll();
        return animeList.stream().map(AnimeMapper::mapToAnimeDto).sorted(Comparator.comparing(AnimeDto::getScore)).collect(Collectors.toList());
    }


    @Override
    public AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime) {
        Anime anime = animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));

        anime.setTitle(updatedAnime.getTitle());
        anime.setScore(updatedAnime.getScore());
        anime.setWatched(updatedAnime.getWatched());

        Anime updatedAnimeObj = animeRepo.save(anime);

        return AnimeMapper.mapToAnimeDto(updatedAnimeObj);
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
        return AnimeMapper.mapToAnimeDto(savedAnime);
    }

    @Override
    public AnimeDto getAnimeByTitle(String title) {
        List<Anime> animeList = animeRepo.findAll();
        for (Anime anime : animeList) {
            if (anime.getTitle().equals(title)) {
                return AnimeMapper.mapToAnimeDto(anime);
            }
        }
        throw new ResourceNotFoundException("Anime does not exist with given title: " + title);
    }


}
