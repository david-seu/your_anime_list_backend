//package david_seu.your_anime_list_backend.controller;
//
//import david_seu.your_anime_list_backend.mapper.UserMapper;
//import david_seu.your_anime_list_backend.model.User;
//import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
//import david_seu.your_anime_list_backend.payload.dto.EpisodeDto;
//import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
//import david_seu.your_anime_list_backend.mapper.AnimeMapper;
//import david_seu.your_anime_list_backend.payload.dto.UserDto;
//import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
//import david_seu.your_anime_list_backend.service.IAnimeService;
//import david_seu.your_anime_list_backend.service.IEpisodeService;
//import david_seu.your_anime_list_backend.service.IUserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/episode")
//@AllArgsConstructor
//@CrossOrigin(origins = "*")
//public class EpisodeController {
//
//    private IEpisodeService episodeService;
//    private IAnimeService animeService;
//    private IUserService userService;
//
//
//    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<EpisodeDto>> getAllEpisodes(@RequestParam(required = false, defaultValue = "DESC") String sort, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String title){
//        try{
//            List<EpisodeDto> episodeList = episodeService.getAllEpisodes(page, title, sort);
//            if(episodeList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(episodeList, HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//    @GetMapping("/get/{id}")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<EpisodeDto> getEpisodeById(@PathVariable("id") Long episodeId){
//        try{
//            EpisodeDto episodeDto = episodeService.getEpisodeById(episodeId);
//            return new ResponseEntity<>(episodeDto, HttpStatus.OK);
//        }
//        catch (ResourceNotFoundException e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping("/add")
//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<EpisodeDto> addEpisode(@RequestBody EpisodeDto episodeDto){
//        try {
//            AnimeDto animeDto = animeService.getAnimeByTitle(episodeDto.getAnimeTitle());
//            episodeDto.setAnime(AnimeMapper.mapToAnime(animeDto));
//            EpisodeDto saveEpisodeDto = episodeService.createEpisode(episodeDto);
//            return new ResponseEntity<>(saveEpisodeDto, HttpStatus.CREATED);
//        }
//        catch (ResourceNotFoundException e)
//        {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//    @PatchMapping("/update/{id}")
//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<EpisodeDto> updateEpisode(@PathVariable("id") Long episodeId, @RequestBody EpisodeDto updatedEpisode){
//        EpisodeDto episodeDto;
//        try{
//            AnimeDto animeDto = animeService.getAnimeByTitle(updatedEpisode.getAnimeTitle());
//            updatedEpisode.setAnime(AnimeMapper.mapToAnime(animeDto));
//            episodeDto = episodeService.updateEpisode(episodeId, updatedEpisode);
//        }
//        catch (ResourceNotFoundException e)
//        {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(episodeDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<HttpStatus> deleteEpisodeById(@PathVariable("id") Long episodeId){
//        try{
//            episodeService.deleteEpisode(episodeId);
//        }
//        catch (ResourceNotFoundException e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//}
