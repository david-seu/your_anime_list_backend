package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.payload.dto.GenreDto;
import david_seu.your_anime_list_backend.service.IGenreService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genre")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class GenreController {

    private final IGenreService genreService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }
}
