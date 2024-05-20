package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.payload.dto.EpisodeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
import david_seu.your_anime_list_backend.service.IAnimeService;
import david_seu.your_anime_list_backend.service.IEpisodeService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/episode")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class EpisodeController {

    private IEpisodeService episodeService;
    private IAnimeService animeService;
    private IUserService userService;


    @GetMapping("/getAllEpisodes")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EpisodeDto>> getAllEpisodes(@RequestParam(required = false, defaultValue = "0") Integer page){
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = userService.getUserById(userDetails.getId());
            List<EpisodeDto> episodeList = episodeService.getAllEpisodes(page, user);
            if(episodeList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(episodeList, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getEpisode/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<EpisodeDto> getEpisodeById(@PathVariable("id") Long episodeId){
        try{
            EpisodeDto episodeDto = episodeService.getEpisodeById(episodeId);
            return new ResponseEntity<>(episodeDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        @PostMapping("/addEpisode")
        @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
        public ResponseEntity<EpisodeDto> addEpisode(@RequestBody EpisodeDto episodeDto){
            try {
                UserDetailsImpl userDetails =
                        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                AnimeDto animeDto = animeService.getAnimeByTitle(episodeDto.getAnimeTitle());
                episodeDto.setAnime(AnimeMapper.mapToAnime(animeDto));
                User user = userService.getUserById(userDetails.getId());
                episodeDto.setUser(user);
                EpisodeDto saveEpisodeDto = episodeService.createEpisode(episodeDto);
                return new ResponseEntity<>(saveEpisodeDto, HttpStatus.CREATED);
            }
            catch (ResourceNotFoundException e)
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

    @PatchMapping("/updateEpisode/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<EpisodeDto> updateEpisode(@PathVariable("id") Long episodeId, @RequestBody EpisodeDto updatedEpisode){
        EpisodeDto episodeDto;
        try{
            AnimeDto animeDto = animeService.getAnimeByTitle(updatedEpisode.getAnimeTitle());
            updatedEpisode.setAnime(AnimeMapper.mapToAnime(animeDto));
            UserDetailsImpl userDetails =
                    (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            updatedEpisode.setUser(user);
            episodeDto = episodeService.updateEpisode(episodeId, updatedEpisode);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(episodeDto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEpisode/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteEpisode(@PathVariable("id") Long episodeId){
        try{
            episodeService.deleteEpisode(episodeId);
        }
        catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
