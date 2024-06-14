package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.payload.dto.GenreDto;

public class GenreMapper {

    public static GenreDto toGenreDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre toGenre(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }
}
