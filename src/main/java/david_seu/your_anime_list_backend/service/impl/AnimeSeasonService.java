package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.AnimeSeasonMapper;
import david_seu.your_anime_list_backend.payload.dto.AnimeSeasonDto;
import david_seu.your_anime_list_backend.repo.IAnimeSeasonRepo;
import david_seu.your_anime_list_backend.service.IAnimeSeasonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnimeSeasonService implements IAnimeSeasonService {

    private IAnimeSeasonRepo animeSeasonRepo;

    @Override
    public List<AnimeSeasonDto> getAllAnimeSeasons() {
        return animeSeasonRepo.findAll().stream().map(AnimeSeasonMapper::mapToAnimeSeasonDto).toList();
    }
}
