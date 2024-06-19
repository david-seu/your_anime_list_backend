package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.payload.dto.AnimeSeasonDto;
import david_seu.your_anime_list_backend.service.IAnimeSeasonService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anime-season")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeSeasonController {

    private final IAnimeSeasonService animeSeasonService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAnimeSeasons() {
        List<AnimeSeasonDto> animeSeasonList = animeSeasonService.getAllAnimeSeasons();
        return ResponseEntity.ok(animeSeasonList.stream().map(AnimeSeasonDto::getYear).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
    }
}
