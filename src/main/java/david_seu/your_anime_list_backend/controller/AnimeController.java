package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.service.IAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeController {

    private IAnimeService animeService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/getAllAnime")
    public ResponseEntity<List<AnimeDto>> getAllAnime(@RequestParam(required = false) String sort, @RequestParam(required = false, defaultValue = "0") Integer page){
        try {
            List<AnimeDto> animeList = animeService.getAllAnime(page);
            if (animeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            if(sort.equals("DESC"))
            {
                animeList = animeList.reversed();
            }

            return new ResponseEntity<>(animeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAnime/{id}")
    public ResponseEntity<AnimeDto> getAnimeById(@PathVariable("id") Long animeId){
        try {
            AnimeDto animeDto = animeService.getAnimeById(animeId);
            return new ResponseEntity<>(animeDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addAnime")
    public ResponseEntity<AnimeDto> addAnime(@RequestBody AnimeDto animeDto)
    {
        AnimeDto saveAnimeDto = animeService.createAnime(animeDto);
        return new ResponseEntity<>(saveAnimeDto, HttpStatus.CREATED);
    }

    @PatchMapping("/updateAnime/{id}")
    public ResponseEntity<AnimeDto> updateAnimeById(@PathVariable("id") Long animeId, @RequestBody AnimeDto updatedAnime)
    {
        AnimeDto animeDto;
        try {
            animeDto = animeService.updateAnime(animeId, updatedAnime);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(animeDto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAnime/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("id") Long animeId)
    {
        try {
            animeService.deleteAnime(animeId);
        }
        catch(ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//
//    @MessageMapping("/anime")
//    @SendTo("/topic/anime")
//    public AnimeDto broadcastAnime(AnimeDto animeDto) {
//        return animeDto;
//    }
//
//    @PostMapping("/createAnime")
//    @Scheduled(fixedRate = 15000)
//    public ResponseEntity<AnimeDto> createAnime(){
//        AnimeDto animeDto = animeService.createAnime();
//        simpMessagingTemplate.convertAndSend("/topic/anime", animeDto);
//        return new ResponseEntity<>(animeDto, HttpStatus.CREATED);
//    }
}
