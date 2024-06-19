package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.AnimeSeasonDto;

import java.util.List;

public interface IAnimeSeasonService {

    List<AnimeSeasonDto> getAllAnimeSeasons();
}
