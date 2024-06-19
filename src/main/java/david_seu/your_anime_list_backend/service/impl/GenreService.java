package david_seu.your_anime_list_backend.service.impl;


import david_seu.your_anime_list_backend.mapper.GenreMapper;
import david_seu.your_anime_list_backend.payload.dto.GenreDto;
import david_seu.your_anime_list_backend.repo.IGenreRepo;
import david_seu.your_anime_list_backend.service.IGenreService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService implements IGenreService {

    private IGenreRepo genreRepo;

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepo.findAll().stream().map(GenreMapper::mapToGenreDto).toList();
    }
}
