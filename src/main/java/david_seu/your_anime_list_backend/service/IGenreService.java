package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.GenreDto;

import java.util.List;

public interface IGenreService {

    GenreDto addGenre(GenreDto genreDto);

    GenreDto getGenreById(Long genreId);

    GenreDto updateGenre(Long genreId, GenreDto updatedGenre);

    void deleteGenre(Long genreId);

    List<GenreDto> getAllGenres();
}
