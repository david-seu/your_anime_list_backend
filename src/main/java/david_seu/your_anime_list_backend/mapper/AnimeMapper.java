package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.model.Studio;
import david_seu.your_anime_list_backend.model.Tag;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.model.Anime;

import java.util.HashSet;
import java.util.stream.Collectors;

public class AnimeMapper {

    public static AnimeDto mapToAnimeDto(Anime anime) {
         AnimeDto animeDto = new AnimeDto();
         animeDto.setId(anime.getId());
         animeDto.setTitle(anime.getTitle());
            animeDto.setSynopsis(anime.getSynopsis());
            animeDto.setScore(anime.getScore());
            animeDto.setPopularity(anime.getPopularity());
            animeDto.setNrEpisodes(anime.getNrEpisodes());
            animeDto.setPictureURL(anime.getPictureURL());
            animeDto.setThumbnailURL(anime.getThumbnailURL());
            animeDto.setStartDate(anime.getStartDate());
            animeDto.setEndDate(anime.getEndDate());
            animeDto.setWatching(anime.getWatching());
            animeDto.setCompleted(anime.getCompleted());
            animeDto.setOnHold(anime.getOnHold());
            animeDto.setDropped(anime.getDropped());
            animeDto.setPlanToWatch(anime.getPlanToWatch());
            animeDto.setAnimeSeason(anime.getAnimeSeason());
            animeDto.setGenres(anime.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
            animeDto.setStudios(anime.getStudios().stream().map(Studio::getName).collect(Collectors.toSet()));
            animeDto.setTags(anime.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
            animeDto.setSynonyms(new HashSet<>(anime.getSynonyms()));
            animeDto.setRelatedAnime(anime.getRelatedAnime());
            animeDto.setRecommendedAnime(anime.getRecommendedAnime());
            animeDto.setType(anime.getType());
            animeDto.setStatus(anime.getStatus());
            animeDto.setMalId(anime.getMalId());
            animeDto.setUser(anime.getUser());
            return animeDto;
    }
    public static Anime mapToAnime(AnimeDto animeDto){
//        return new Anime(animeDto.getId(), animeDto.getTitle(), animeDto.getSynopsis(), animeDto.getScore(), animeDto.getPopularity(), animeDto.getNrEpisodes(), animeDto.getPictureURL(), animeDto.getThumbnailURL(), animeDto.getStartDate(), animeDto.getEndDate(), animeDto.getWatching(), animeDto.getCompleted(), animeDto.getOnHold(), animeDto.getDropped(), animeDto.getPlanToWatch(), animeDto.getMalId(), animeDto.getGenres(), animeDto.getStudios(), animeDto.getTags(), animeDto.getSynonyms(), animeDto.getRelatedAnime(), animeDto.getRecommendedAnime(), animeDto.getType(), animeDto.getUser(), animeDto.getStatus(), animeDto.getAnimeSeason());
        return new Anime();
    }
}
