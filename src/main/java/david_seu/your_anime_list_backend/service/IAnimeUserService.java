package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.AnimeUserDto;

import java.util.List;

public interface IAnimeUserService {

    AnimeUserDto addAnimeUser(AnimeUserDto animeUserDto);

    AnimeUserDto getAnimeUserById(Long animeId, Long userId);

    AnimeUserDto updateAnimeUser(Long animeId, Long userId, AnimeUserDto updatedAnimeUser);

    void deleteAnimeUser(Long userId, Long animeId);

    List<AnimeUserDto> getAnimeUserByUserId(Long userId, String title, String sort, int page);
}
