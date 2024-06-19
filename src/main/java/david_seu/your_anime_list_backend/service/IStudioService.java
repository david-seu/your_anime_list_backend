package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.StudioDto;

import java.util.List;

public interface IStudioService {

    List<StudioDto> getAllStudios();
}
