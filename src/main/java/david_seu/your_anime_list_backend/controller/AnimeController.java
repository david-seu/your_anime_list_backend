package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.exception.InvalidAnimeException;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
import david_seu.your_anime_list_backend.service.IAnimeService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/anime")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeController {

    private IAnimeService animeService;
    private IUserService userService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<?>> getAllAnime(@RequestParam(required = false, defaultValue = "DESC") String sort, @RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "0") Integer page) {
        try {
            List<AnimeDto> animeList = animeService.getAllAnime(page, title, sort);
            if (animeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(animeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAnimeById(@PathVariable("id") Long animeId){
        try {

            AnimeDto animeDto = animeService.getAnimeById(animeId);
            return new ResponseEntity<>(animeDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addAnime(@RequestBody AnimeDto animeDto)
    {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = UserMapper.mapToUser(userService.getUserById(userDetails.getId()));
        animeDto.setUser(user);
        try {
            AnimeDto saveAnimeDto = animeService.createAnime(animeDto);
            return new ResponseEntity<>(saveAnimeDto, HttpStatus.CREATED);
        }
        catch (InvalidAnimeException e)
        {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAnimeById(@PathVariable("id") Long animeId, @RequestBody AnimeDto updatedAnime)
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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAnimeById(@PathVariable("id") Long animeId)
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

    @GetMapping("/scoresCount")
    public ResponseEntity<?> getScoresCount() {
        try {
            List<Integer> scoresCount = animeService.getScoresCount();
            return new ResponseEntity<>(scoresCount, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        System.out.println("Anime created");
        return new ResponseEntity<>(animeDto, HttpStatus.CREATED);
    }
}
