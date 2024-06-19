package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.TagDto;

import java.util.List;

public interface ITagsService {

    List<TagDto> getAllTags();
}
