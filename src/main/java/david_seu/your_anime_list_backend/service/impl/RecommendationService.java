package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.service.IRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@Service
public class RecommendationService implements IRecommendationService {

    public final RestTemplate restTemplate;

    private final IAnimeRepo animeRepo;


    @Override
    public List<AnimeDto> getRecommendations(String title) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://anime-recommendation-api.onrender.com/recommend")
                .queryParam("title", title)
                .build()
                .encode()
                .toUri();
    
        String[] recommendations = restTemplate.getForObject(uri, String[].class);

        return animeRepo.findByTitleIn(recommendations).stream().map(AnimeMapper::mapToAnimeDto).toList();
    }
}
