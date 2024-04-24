package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.dto.EpisodeDto;
import david_seu.your_anime_list_backend.model.Anime;

import java.util.stream.Collectors;

public class AnimeMapper {

    public static AnimeDto mapToAnimeDto(Anime anime, Integer numEpisodes){
        return new AnimeDto(anime.getId(), anime.getTitle(), anime.getScore(), anime.getWatched(), numEpisodes);
    }

    public static Anime mapToAnime(AnimeDto animeDto){
        return new Anime(animeDto.getId(), animeDto.getTitle(), animeDto.getScore(), animeDto.getWatched());
    }
}
