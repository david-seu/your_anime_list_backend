package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Tag;
import david_seu.your_anime_list_backend.payload.dto.TagDto;

import java.util.HashSet;

public class TagMapper {

    public static TagDto mapToTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    public static Tag mapToTag(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName(), new HashSet<>());
    }
}
