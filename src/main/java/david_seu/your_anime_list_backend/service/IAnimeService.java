package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.AnimeDto;

import java.util.List;

public interface IAnimeService {
    AnimeDto createAnime(AnimeDto animeDto);

    AnimeDto getAnimeById(Long animeId);

    List<AnimeDto> getAllAnime(Integer page);

    AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime);

    void deleteAnime(Long animeId);

    AnimeDto createAnime();

    AnimeDto getAnimeByTitle(String title);
}
