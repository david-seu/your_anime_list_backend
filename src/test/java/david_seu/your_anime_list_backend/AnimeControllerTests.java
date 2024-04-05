package david_seu.your_anime_list_backend;

import david_seu.your_anime_list_backend.controller.AnimeController;
import david_seu.your_anime_list_backend.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.service.impl.AnimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AnimeControllerTests {

    @InjectMocks
    AnimeController animeController;

    @Mock
    AnimeService animeService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAnime() {
        AnimeDto animeDto = new AnimeDto();
        when(animeService.getAllAnime()).thenReturn(List.of(animeDto));

        ResponseEntity<List<AnimeDto>> response = animeController.getAllAnime();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        verify(animeService, times(1)).getAllAnime();
    }

    @Test
    public void testGetAllAnimeEmpty() {
        when(animeService.getAllAnime()).thenReturn(List.of());

        ResponseEntity<List<AnimeDto>> response = animeController.getAllAnime();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(animeService, times(1)).getAllAnime();
    }

    @Test
    public void testGetAnimeById() {
        AnimeDto animeDto = new AnimeDto();
        when(animeService.getAnimeById(anyLong())).thenReturn(animeDto);

        ResponseEntity<AnimeDto> response = animeController.getAnimeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(animeService, times(1)).getAnimeById(anyLong());
    }

    @Test
    public void testGetAnimeByIdNotFound() {
        when(animeService.getAnimeById(anyLong())).thenThrow(new ResourceNotFoundException("Not found"));

        ResponseEntity<AnimeDto> response = animeController.getAnimeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(animeService, times(1)).getAnimeById(anyLong());
    }

    @Test
    public void testAddAnime() {
        AnimeDto animeDto = new AnimeDto();
        when(animeService.createAnime(any(AnimeDto.class))).thenReturn(animeDto);

        ResponseEntity<AnimeDto> response = animeController.addAnime(animeDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(animeService, times(1)).createAnime(any(AnimeDto.class));
    }

    @Test
    public void testUpdateAnimeById() {
        AnimeDto animeDto = new AnimeDto();
        when(animeService.updateAnime(anyLong(), any(AnimeDto.class))).thenReturn(animeDto);

        ResponseEntity<AnimeDto> response = animeController.updateAnimeById(1L, animeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(animeService, times(1)).updateAnime(anyLong(), any(AnimeDto.class));
    }

    @Test
    public void testUpdateAnimeByIdNotFound() {
        when(animeService.updateAnime(anyLong(), any(AnimeDto.class))).thenThrow(new ResourceNotFoundException("Not found"));

        ResponseEntity<AnimeDto> response = animeController.updateAnimeById(1L, new AnimeDto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(animeService, times(1)).updateAnime(anyLong(), any(AnimeDto.class));
    }

    @Test
    public void testDeleteAnimeById() {
        doNothing().when(animeService).deleteAnime(anyLong());

        ResponseEntity<HttpStatus> response = animeController.deleteBookById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(animeService, times(1)).deleteAnime(anyLong());
    }

    @Test
    public void testDeleteAnimeByIdNotFound() {
        doThrow(new ResourceNotFoundException("Not found")).when(animeService).deleteAnime(anyLong());

        ResponseEntity<HttpStatus> response = animeController.deleteBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(animeService, times(1)).deleteAnime(anyLong());
    }
}