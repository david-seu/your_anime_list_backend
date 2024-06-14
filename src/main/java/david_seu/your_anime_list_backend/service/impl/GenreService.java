package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.GenreMapper;
import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.payload.dto.GenreDto;
import david_seu.your_anime_list_backend.repo.IGenreRepo;
import david_seu.your_anime_list_backend.service.IGenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService implements IGenreService {

    private IGenreRepo genreRepo;
    @Override
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre = GenreMapper.toGenre(genreDto);
        if(genreRepo.findByName(genre.getName()) != null) {
            throw new RuntimeException("Genre already exists");
        }
        genre = genreRepo.save(genre);
        return GenreMapper.toGenreDto(genre);
    }

    @Override
    public GenreDto getGenreById(Long genreId) {
        Genre genre = genreRepo.findById(genreId).orElseThrow(() -> new RuntimeException("Genre does not exist with given id: " + genreId));
        return GenreMapper.toGenreDto(genre);
    }

    @Override
    public GenreDto updateGenre(Long genreId, GenreDto updatedGenre) {
        Genre genre = genreRepo.findById(genreId).orElseThrow(() -> new RuntimeException("Genre does not exist with given id: " + genreId));
        genre.setName(updatedGenre.getName());
        genre = genreRepo.save(genre);
        return GenreMapper.toGenreDto(genre);
    }

    @Override
    public void deleteGenre(Long genreId) {
        Genre genre = genreRepo.findById(genreId).orElseThrow(() -> new RuntimeException("Genre does not exist with given id: " + genreId));
        genreRepo.delete(genre);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepo.findAll().stream().map(GenreMapper::toGenreDto).toList();
    }
}
