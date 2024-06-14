package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
import david_seu.your_anime_list_backend.payload.dto.AnimeUserDto;
import david_seu.your_anime_list_backend.service.IAnimeUserService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animeUser")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeUserController {

    private IAnimeUserService animeUserService;

    private IUserService userService;

    @GetMapping("/getAll/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<?>> getAllAnimeUser(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "DESC") String sort, @RequestParam(required = false, defaultValue = "0") Integer page, @PathVariable Long userId) {
        try {
            List<AnimeUserDto> animeUserList = animeUserService.getAnimeUserByUserId(userId, title, sort, page);
            if (animeUserList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(animeUserList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addAnimeUser(@RequestBody AnimeUserDto animeUserDto) {
        try {
            AnimeUserDto animeUser = animeUserService.addAnimeUser(animeUserDto);
            return new ResponseEntity<>(animeUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{animeId}/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAnimeUserById(@PathVariable Long animeId, @PathVariable Long userId) {
        try {
            AnimeUserId animeUserId = new AnimeUserId(animeId, userId);
            AnimeUserDto animeUser = animeUserService.getAnimeUserById(animeUserId);
            return new ResponseEntity<>(animeUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{animeId}/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAnimeUser(@PathVariable Long animeId, @PathVariable Long userId, @RequestBody AnimeUserDto updatedAnimeUser) {
        try {
            AnimeUserId animeUserId = new AnimeUserId(animeId, userId);
            AnimeUserDto animeUser = animeUserService.updateAnimeUser(animeUserId, updatedAnimeUser);
            return new ResponseEntity<>(animeUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{animeId}/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAnimeUser(@PathVariable Long animeId, @PathVariable Long userId) {
        try {
            AnimeUserId animeUserId = new AnimeUserId(animeId, userId);
            animeUserService.deleteAnimeUser(animeUserId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
