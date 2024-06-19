package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.model.Studio;
import david_seu.your_anime_list_backend.model.Tag;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.model.Anime;

import java.util.HashSet;
import java.util.List;
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
            HashSet<String> synonyms = anime.getSynonyms() == null  ? new HashSet<>() : new HashSet<>(anime.getSynonyms());
            animeDto.setSynonyms(synonyms);
            animeDto.setRelatedAnime(anime.getRelatedAnime());
            animeDto.setRecommendedAnime(anime.getRecommendedAnime());
            animeDto.setType(anime.getType());
            animeDto.setStatus(anime.getStatus());
            animeDto.setMalId(anime.getMalId());
            animeDto.setUser(anime.getUser());
            return animeDto;
    }
    public static Anime mapToAnime(AnimeDto animeDto){
        Anime anime = new Anime();
        anime.setTitle(animeDto.getTitle());
        anime.setSynopsis(animeDto.getSynopsis());
        anime.setScore(animeDto.getScore());
        anime.setPopularity(animeDto.getPopularity());
        anime.setNrEpisodes(animeDto.getNrEpisodes());
        anime.setPictureURL(animeDto.getPictureURL());
        anime.setThumbnailURL(animeDto.getThumbnailURL());
        anime.setStartDate(animeDto.getStartDate());
        anime.setEndDate(animeDto.getEndDate());
        anime.setWatching(animeDto.getWatching());
        anime.setCompleted(animeDto.getCompleted());
        anime.setOnHold(animeDto.getOnHold());
        anime.setDropped(animeDto.getDropped());
        anime.setPlanToWatch(animeDto.getPlanToWatch());
        anime.setType(animeDto.getType());
        anime.setStatus(animeDto.getStatus());
        anime.setUser(animeDto.getUser());
        return anime;
    }
}
