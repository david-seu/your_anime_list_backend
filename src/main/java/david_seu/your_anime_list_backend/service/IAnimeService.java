package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;

import java.util.List;
import java.util.Map;

public interface IAnimeService {
    AnimeDto createAnime(AnimeDto animeDto);

    AnimeDto getAnimeById(Long animeId);

    List<AnimeDto> getAllAnime(Integer page,  String Title, String sort);

    AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime);

    void deleteAnime(Long animeId);

    AnimeDto createAnime();

    AnimeDto getAnimeByTitle(String title);

    List<Integer> getScoresCount();
}
