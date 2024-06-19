package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Studio;
import david_seu.your_anime_list_backend.payload.dto.StudioDto;

import java.util.HashSet;

public class StudioMapper {

    public static StudioDto mapToStudioDto(Studio studio) {
        return new StudioDto(studio.getId(), studio.getName());
    }

    public static Studio mapToStudio(StudioDto studioDto) {
        return new Studio(studioDto.getId(), studioDto.getName(), new HashSet<>());
    }
}
