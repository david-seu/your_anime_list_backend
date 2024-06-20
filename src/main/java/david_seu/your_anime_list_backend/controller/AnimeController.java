package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.exception.InvalidAnimeException;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
import david_seu.your_anime_list_backend.service.IAnimeService;
import david_seu.your_anime_list_backend.service.IRecommendationService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/anime")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeController {

    @NonNull
    private IAnimeService animeService;
    @NonNull
    private IUserService userService;
    @NonNull
    private SimpMessagingTemplate simpMessagingTemplate;
    @NonNull
    private final TaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledTask;

    @NonNull
    private IRecommendationService recommendationService;

    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendations(@RequestParam String title) {
        try {
            List<AnimeDto> recommendations = recommendationService.getRecommendations(title);
            return new ResponseEntity<>(recommendations, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAnime(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "desc") String sortDirection,
                                         @RequestParam(required = false, defaultValue = "") String title,
                                         @RequestParam(required = false) String season,
                                         @RequestParam(required = false) Integer year,
                                         @RequestParam(required = false) Set<String> genres,
                                         @RequestParam(required = false) Set<String> tags,
                                         @RequestParam(required = false) Set<String> studios,
                                         @RequestParam(required = false) Set<String>     type,
                                         @RequestParam(required = false) String status,
                                         @RequestParam(required = false, defaultValue = "score") String orderBy) {
        try {
            List<AnimeDto> animeList = animeService.getAllAnime(page, sortDirection, title, season, year, genres, tags, studios, type, status, orderBy);
            if (animeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(animeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/get/{id}")
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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

    @PostMapping("/startCreation")
    public ResponseEntity<?> startAnimeCreation() {
        if (scheduledTask == null || scheduledTask.isDone()) {
            PeriodicTrigger trigger = new PeriodicTrigger(15, TimeUnit.SECONDS);
            trigger.setFixedRate(true);
            scheduledTask = taskScheduler.schedule(this::createAnime, trigger);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/stopCreation")
    public ResponseEntity<?> stopAnimeCreation() {
        System.out.println("wtf");
        System.out.println(scheduledTask);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            System.out.println("Anime creation stopped");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/anime")
    @SendTo("/topic/anime")
    public AnimeDto broadcastAnime(AnimeDto animeDto) {
        return animeDto;
    }

    private ResponseEntity<AnimeDto> createAnime() {

        AnimeDto animeDto = animeService.createAnime();
        simpMessagingTemplate.convertAndSend("/topic/anime", animeDto);
        System.out.println("Anime created");
        return new ResponseEntity<>(animeDto, HttpStatus.CREATED);
    }
}
