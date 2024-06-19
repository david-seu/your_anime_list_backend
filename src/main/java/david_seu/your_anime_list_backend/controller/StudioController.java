package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.service.IStudioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studio")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class StudioController {

    private IStudioService studioService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudios() {
        return ResponseEntity.ok(studioService.getAllStudios());
    }
}
