package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.payload.dto.GenreDto;

import java.util.HashSet;

public class GenreMapper {

    public static GenreDto mapToGenreDto(Genre genre) {
        return new GenreDto(genre.getName());
    }

    public static Genre mapToGenre(GenreDto genreDto) {
        return new Genre(0L,genreDto.getName(), new HashSet<>());
    }
}
