package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
import david_seu.your_anime_list_backend.payload.dto.AnimeUserDto;

import java.util.List;

public interface IAnimeUserService {

    AnimeUserDto addAnimeUser(AnimeUserDto animeUserDto);

    AnimeUserDto getAnimeUserById(AnimeUserId animeUserId);

    AnimeUserDto updateAnimeUser(AnimeUserId animeUserId, AnimeUserDto updatedAnimeUser);

    void deleteAnimeUser(AnimeUserId animeUserId);

    List<AnimeUserDto> getAnimeUserByUserId(Long userId, String title, String sort, int page);
}
