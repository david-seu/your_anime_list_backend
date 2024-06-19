package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.TagMapper;
import david_seu.your_anime_list_backend.payload.dto.TagDto;
import david_seu.your_anime_list_backend.repo.ITagRepo;
import david_seu.your_anime_list_backend.service.ITagsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagsService implements ITagsService {

    private final ITagRepo tagRepo;

    @Override
    public List<TagDto> getAllTags() {
        return tagRepo.findAll().stream().map(TagMapper::mapToTagDto).toList();
    }
}
