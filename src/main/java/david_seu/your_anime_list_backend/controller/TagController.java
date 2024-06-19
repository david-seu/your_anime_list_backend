package david_seu.your_anime_list_backend.controller;


import david_seu.your_anime_list_backend.service.ITagsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TagController {

    private final ITagsService tagService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
