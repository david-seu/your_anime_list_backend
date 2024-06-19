package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.AnimeSeason;
import david_seu.your_anime_list_backend.payload.dto.AnimeSeasonDto;

public class AnimeSeasonMapper {

    public static AnimeSeasonDto mapToAnimeSeasonDto(AnimeSeason animeSeason) {
        return new AnimeSeasonDto(animeSeason.getId(), animeSeason.getSeason(), animeSeason.getYear());
    }

    public static AnimeSeason mapToAnimeSeason(AnimeSeasonDto animeSeasonDto) {
        return new AnimeSeason(animeSeasonDto.getId(), animeSeasonDto.getYear(), animeSeasonDto.getSeason());
    }
}
