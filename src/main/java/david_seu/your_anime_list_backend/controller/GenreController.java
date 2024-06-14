package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.payload.dto.GenreDto;
import david_seu.your_anime_list_backend.service.IGenreService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GenreController {

    private IGenreService genreService;

    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getAllGenres() {
        try {
            List<GenreDto> genreList = genreService.getAllGenres();
            if (genreList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(genreList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{genreId}")
    public ResponseEntity<?> getGenreById(@PathVariable Long genreId) {
        try {
            GenreDto genre = genreService.getGenreById(genreId);
            return new ResponseEntity<>(genre, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGenre(@RequestBody GenreDto genreDto) {
        try {
            GenreDto genre = genreService.addGenre(genreDto);
            return new ResponseEntity<>(genre, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{genreId}")
    public ResponseEntity<?> updateGenre(@PathVariable Long genreId, @RequestBody GenreDto updatedGenre) {
        try {
            GenreDto genre = genreService.updateGenre(genreId, updatedGenre);
            return new ResponseEntity<>(genre, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long genreId) {
        try {
            genreService.deleteGenre(genreId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
