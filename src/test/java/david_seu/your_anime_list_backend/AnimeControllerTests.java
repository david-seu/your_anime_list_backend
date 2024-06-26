//package david_seu.your_anime_list_backend;
//
//import david_seu.your_anime_list_backend.controller.AnimeController;
//import david_seu.your_anime_list_backend.model.User;
//import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
//import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
//import david_seu.your_anime_list_backend.payload.dto.UserDto;
//import david_seu.your_anime_list_backend.service.impl.AnimeService;
//import org.hibernate.mapping.Any;
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
//import static org.mockito.Mockito.*;
//
//public class AnimeControllerTests {
//
//    @InjectMocks
//    AnimeController animeController;
//
//    @Mock
//    AnimeService animeService;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllAnime() {
//        AnimeDto animeDto = new AnimeDto();
//        when(animeService.getAllAnime(0, "","")).thenReturn(List.of(animeDto));
//
//        ResponseEntity<List<?>> response = animeController.getAllAnime("","", 0);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
//        verify(animeService, times(1)).getAllAnime(0,"","");
//    }
//
//    @Test
//    public void testGetAllAnimeEmpty() {
//        when(animeService.getAllAnime(0,"","")).thenReturn(List.of());
//
//        ResponseEntity<List<?>> response = animeController.getAllAnime("","",0);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(animeService, times(1)).getAllAnime(0,"","");
//    }
//
//    @Test
//    public void testGetAnime() {
//        AnimeDto animeDto = new AnimeDto();
//        when(animeService.getAnimeById(anyLong())).thenReturn(animeDto);
//
//        ResponseEntity<?> response = animeController.getAnimeById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(animeService, times(1)).getAnimeById(anyLong());
//    }
//
//    @Test
//    public void testGetAnimeNotFound() {
//        when(animeService.getAnimeById(anyLong())).thenThrow(new ResourceNotFoundException("Not found"));
//
//        ResponseEntity<?> response = animeController.getAnimeById(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(animeService, times(1)).getAnimeById(anyLong());
//    }
//
//    @Test
//    public void testAddAnime() {
//        AnimeDto animeDto = new AnimeDto();
//        when(animeService.createAnime(any(AnimeDto.class))).thenReturn(animeDto);
//
//        ResponseEntity<?> response = animeController.addAnime(animeDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(animeService, times(1)).createAnime(any(AnimeDto.class));
//    }
//
//    @Test
//    public void testUpdateAnime() {
//        AnimeDto animeDto = new AnimeDto();
//        when(animeService.updateAnime(anyLong(), any(AnimeDto.class))).thenReturn(animeDto);
//
//        ResponseEntity<?> response = animeController.updateAnimeById(1L, animeDto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(animeService, times(1)).updateAnime(anyLong(), any(AnimeDto.class));
//    }
//
//    @Test
//    public void testUpdateAnimeNotFound() {
//        when(animeService.updateAnime(anyLong(), any(AnimeDto.class))).thenThrow(new ResourceNotFoundException("Not found"));
//
//        ResponseEntity<?> response = animeController.updateAnimeById(1L, new AnimeDto());
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(animeService, times(1)).updateAnime(anyLong(), any(AnimeDto.class));
//    }
//
//    @Test
//    public void testDeleteAnime() {
//        doNothing().when(animeService).deleteAnime(anyLong());
//
//        ResponseEntity<?> response = animeController.deleteAnimeById(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(animeService, times(1)).deleteAnime(anyLong());
//    }
//
//    @Test
//    public void testDeleteAnimeNotFound() {
//        doThrow(new ResourceNotFoundException("Not found")).when(animeService).deleteAnime(anyLong());
//
//        ResponseEntity<?> response = animeController.deleteAnimeById(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(animeService, times(1)).deleteAnime(anyLong());
//    }
//}