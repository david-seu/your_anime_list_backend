package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.service.impl.AnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/anime")
@AllArgsConstructor
public class AnimeController {

    private AnimeService animeService;
    @GetMapping("/getAllAnime")
    public ResponseEntity<List<AnimeDto>> getAllAnime(){
        try{
            List<AnimeDto> animeList = animeService.getAllAnime();
            if(animeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(animeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAnimeById/{id}")
    public ResponseEntity<AnimeDto> getAnimeById(@PathVariable("id") Long animeId){
        AnimeDto animeDto = animeService.getAnimeById(animeId);
        return new ResponseEntity<>(animeDto, HttpStatus.OK);
    }

    @PostMapping("/addAnime")
    public ResponseEntity<AnimeDto> addAnime(@RequestBody AnimeDto animeDto)
    {
        AnimeDto saveAnimeDto = animeService.createAnime(animeDto);
        return new ResponseEntity<>(saveAnimeDto, HttpStatus.CREATED);
    }

    @PatchMapping("/updateAnimeById/{id}")
    public ResponseEntity<AnimeDto> updateAnimeById(@PathVariable("id") Long animeId, @RequestBody AnimeDto updatedAnime)
    {
        AnimeDto animeDto = animeService.updateAnime(animeId, updatedAnime);
        return new ResponseEntity<>(animeDto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAnimeById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("id") Long animeId)
    {
        animeService.deleteAnime(animeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
