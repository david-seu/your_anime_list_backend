package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;

import java.util.List;
import java.util.Set;

public interface IAnimeService {
    AnimeDto createAnime(AnimeDto animeDto);

    AnimeDto getAnimeById(Long animeId);

    List<AnimeDto> getAllAnime(Integer page, String sortDirection, String title, String season, Integer year, Set<String> genres, Set<String> tags, Set<String> studios, Set<String> type, String status, String orderBy);


    AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime);

    void deleteAnime(Long animeId);

    AnimeDto createAnime();

    AnimeDto getAnimeByTitle(String title);

    List<Integer> getScoresCount();

//    List<AnimeDto> getAnimeByGenre(String genre);
}
