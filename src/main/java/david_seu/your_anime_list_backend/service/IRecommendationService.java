package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.AnimeDto;

import java.util.List;

public interface IRecommendationService {

    List<AnimeDto> getRecommendations(String title);
}
