//package david_seu.your_anime_list_backend;
//
//import david_seu.your_anime_list_backend.controller.EpisodeController;
//import david_seu.your_anime_list_backend.model.User;
//import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
//import david_seu.your_anime_list_backend.payload.dto.EpisodeDto;
//import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
//import david_seu.your_anime_list_backend.payload.dto.UserDto;
//import david_seu.your_anime_list_backend.service.impl.AnimeService;
//import david_seu.your_anime_list_backend.service.impl.EpisodeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//public class EpisodeControllerTest {
//
//    @InjectMocks
//    EpisodeController episodeController;
//
//    @Mock
//    AnimeService animeService;
//
//    @Mock
//    EpisodeService episodeService;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllEpisodes() {
//        EpisodeDto episodeDto = new EpisodeDto();
//        when(episodeService.getAllEpisodes(0,"","")).thenReturn(List.of(episodeDto));
//
//        ResponseEntity<List<EpisodeDto>> response = episodeController.getAllEpisodes("",0,"");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
//        verify(episodeService, times(1)).getAllEpisodes(0,"","");
//    }
//
//    @Test
//    public void testGetAllEpisodesEmpty() {
//        when(episodeService.getAllEpisodes(0,"","")).thenReturn(List.of());
//
//        ResponseEntity<List<EpisodeDto>> response = episodeController.getAllEpisodes("",0,"");
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(episodeService, times(1)).getAllEpisodes(0,"","");
//    }
//
//    @Test
//    public void testGetEpisode() {
//        EpisodeDto episodeDto = new EpisodeDto();
//        when(episodeService.getEpisodeById(anyLong())).thenReturn(episodeDto);
//
//        ResponseEntity<EpisodeDto> response = episodeController.getEpisodeById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(episodeService, times(1)).getEpisodeById(anyLong());
//    }
//
//    @Test
//    public void testGetEpisodeNotFound() {
//        when(episodeService.getEpisodeById(anyLong())).thenThrow(new ResourceNotFoundException("Not found"));
//
//        ResponseEntity<EpisodeDto> response = episodeController.getEpisodeById(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(episodeService, times(1)).getEpisodeById(anyLong());
//    }
//
//    @Test
//    public void testAddEpisode() {
//        // Setup
//        EpisodeDto episodeDto = new EpisodeDto();
//        episodeDto.setAnimeTitle("Example Title");
//
//        AnimeDto animeDto = new AnimeDto();
//        animeDto.setTitle("Example Title");
//
//        when(animeService.getAnimeByTitle(episodeDto.getAnimeTitle())).thenReturn(animeDto);
//
//        EpisodeDto savedEpisodeDto = new EpisodeDto(); // Assuming you have a saved episode
//        when(episodeService.createEpisode(any(EpisodeDto.class))).thenReturn(savedEpisodeDto);
//
//        // Execution
//        ResponseEntity<EpisodeDto> response = episodeController.addEpisode(episodeDto);
//
//        // Assertion
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(animeService, times(1)).getAnimeByTitle(episodeDto.getAnimeTitle());
//        verify(episodeService, times(1)).createEpisode(any(EpisodeDto.class));
//    }
//
//    @Test
//    public void testAddEpisodeNotFound() {
//        // Setup
//        EpisodeDto episodeDto = new EpisodeDto();
//        episodeDto.setAnimeTitle("Nonexistent Title");
//
//        when(animeService.getAnimeByTitle(episodeDto.getAnimeTitle())).thenThrow(ResourceNotFoundException.class);
//
//        // Execution
//        ResponseEntity<EpisodeDto> response = episodeController.addEpisode(episodeDto);
//
//        // Assertion
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(animeService, times(1)).getAnimeByTitle(episodeDto.getAnimeTitle());
//        verify(episodeService, never()).createEpisode(any(EpisodeDto.class));
//    }
//
//    @Test
//    public void testUpdateEpisode() {
//        // Setup
//        Long episodeId = 1L;
//        EpisodeDto updatedEpisodeDto = new EpisodeDto();
//        updatedEpisodeDto.setId(episodeId);
//        updatedEpisodeDto.setAnimeTitle("Example Title");
//
//        AnimeDto animeDto = new AnimeDto();
//        animeDto.setTitle("Example Title");
//
//        when(animeService.getAnimeByTitle(updatedEpisodeDto.getAnimeTitle())).thenReturn(animeDto);
//
//        EpisodeDto updatedEpisode = new EpisodeDto(); // Assuming you have an updated episode
//        when(episodeService.updateEpisode(eq(episodeId), any(EpisodeDto.class))).thenReturn(updatedEpisode);
//
//        // Execution
//        ResponseEntity<EpisodeDto> response = episodeController.updateEpisode(episodeId, updatedEpisodeDto);
//
//        // Assertion
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(updatedEpisode, response.getBody());
//        verify(animeService, times(1)).getAnimeByTitle(updatedEpisodeDto.getAnimeTitle());
//        verify(episodeService, times(1)).updateEpisode(eq(episodeId), any(EpisodeDto.class));
//    }
//
//    @Test
//    public void testUpdateEpisodeNotFound() {
//        // Setup
//        Long episodeId = 1L;
//        EpisodeDto updatedEpisodeDto = new EpisodeDto();
//        updatedEpisodeDto.setAnimeTitle("Nonexistent Title");
//
//        when(animeService.getAnimeByTitle(updatedEpisodeDto.getAnimeTitle())).thenThrow(ResourceNotFoundException.class);
//
//        // Execution
//        ResponseEntity<EpisodeDto> response = episodeController.updateEpisode(episodeId, updatedEpisodeDto);
//
//        // Assertion
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(animeService, times(1)).getAnimeByTitle(updatedEpisodeDto.getAnimeTitle());
//        verify(episodeService, never()).updateEpisode(anyLong(), any(EpisodeDto.class));
//    }
//
//    @Test
//    public void testDeleteEpisode() {
//        // Setup
//        Long episodeId = 1L;
//
//        // Execution
//        ResponseEntity<HttpStatus> response = episodeController.deleteEpisodeById(episodeId);
//
//        // Assertion
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(episodeService, times(1)).deleteEpisode(episodeId);
//    }
//
//    @Test
//    public void testDeleteEpisodeNotFound() {
//        // Setup
//        Long episodeId = 1L;
//        doThrow(ResourceNotFoundException.class).when(episodeService).deleteEpisode(episodeId);
//
//        // Execution
//        ResponseEntity<HttpStatus> response = episodeController.deleteEpisodeById(episodeId);
//
//        // Assertion
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(episodeService, times(1)).deleteEpisode(episodeId);
//    }
//
//
//}
