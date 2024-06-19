package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.AnimeUser;
import david_seu.your_anime_list_backend.payload.dto.AnimeUserDto;

public class AnimeUserMapper {

    public static AnimeUserDto mapToAnimeUserDto(AnimeUser animeUser){
        return new AnimeUserDto(animeUser.getId(), animeUser.getAnime().getId(), animeUser.getUser().getId(),  AnimeMapper.mapToAnimeDto(animeUser.getAnime()), animeUser.getUser(), animeUser.getScore(), animeUser.getIsFavorite(), animeUser.getStatus(), animeUser.getStartDate(), animeUser.getEndDate());
    }

    public static AnimeUser mapToAnimeUser(AnimeUserDto animeUserDto){
        return new AnimeUser(animeUserDto.getId(), animeUserDto.getUser(), AnimeMapper.mapToAnime(animeUserDto.getAnime()), animeUserDto.getScore(), animeUserDto.getIsFavorite(), animeUserDto.getStatus(), animeUserDto.getStartDate(), animeUserDto.getEndDate());
    }
}
