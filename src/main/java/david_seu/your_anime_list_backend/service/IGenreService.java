package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.GenreDto;

import java.util.List;

public interface IGenreService {

    List<GenreDto> getAllGenres();
}
