package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.service.impl.AnimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeController {

    private AnimeService animeService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(){
        return new ResponseEntity<>("Running", HttpStatus.OK);
    }

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

    @PatchMapping("/updateAnimeById/{id}")
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

    @DeleteMapping("/deleteAnimeById/{id}")
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

    @MessageMapping("/anime")
    @SendTo("/topic/anime")
    public AnimeDto broadcastAnime(AnimeDto animeDto) {
        return animeDto;
    }

    @PostMapping("/createAnime")
    @Scheduled(fixedRate = 15000)
    public ResponseEntity<AnimeDto> createAnime(){
        AnimeDto animeDto = animeService.createAnime();
        simpMessagingTemplate.convertAndSend("/topic/anime", animeDto);
        return new ResponseEntity<>(animeDto, HttpStatus.CREATED);
    }
}
