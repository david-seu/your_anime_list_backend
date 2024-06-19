package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.StudioMapper;
import david_seu.your_anime_list_backend.payload.dto.StudioDto;
import david_seu.your_anime_list_backend.repo.IStudioRepo;
import david_seu.your_anime_list_backend.service.IStudioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudioService implements IStudioService {

    private IStudioRepo studioRepo;
    @Override
    public List<StudioDto> getAllStudios() {
        return studioRepo.findAll().stream().map(StudioMapper::mapToStudioDto).toList();
    }
}
