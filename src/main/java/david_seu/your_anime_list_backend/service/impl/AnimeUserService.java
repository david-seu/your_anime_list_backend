package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.exception.InvalidAnimeException;
import david_seu.your_anime_list_backend.mapper.AnimeUserMapper;
import david_seu.your_anime_list_backend.model.AnimeUser;
import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
import david_seu.your_anime_list_backend.payload.dto.AnimeUserDto;
import david_seu.your_anime_list_backend.repo.IAnimeUserRepo;
import david_seu.your_anime_list_backend.service.IAnimeUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimeUserService implements IAnimeUserService {

    private IAnimeUserRepo animeUserRepo;
    @Override
    public AnimeUserDto addAnimeUser(AnimeUserDto animeUserDto) {
        if(animeUserRepo.findById(animeUserDto.getId()).isPresent()){
            throw new InvalidAnimeException("AnimeUser already exists with given anime id and user id: " + animeUserDto.getId().getAnimeId() + " " + animeUserDto.getId().getUserId());
        }
        if(animeUserDto.getScore() > 10 || animeUserDto.getScore() < 0){
            throw new InvalidAnimeException("Score must be between 0 and 10");
        }
        if(animeUserDto.getStartDate().before(animeUserDto.getEndDate())){
            throw new InvalidAnimeException("Start date must be before end date");
        }
        AnimeUser animeUser = AnimeUserMapper.mapToAnimeUser(animeUserDto);
        AnimeUser savedAnime = animeUserRepo.save(animeUser);
        return AnimeUserMapper.mapToAnimeUserDto(savedAnime);
    }

    @Override
    public AnimeUserDto getAnimeUserById(AnimeUserId animeUserId) {
        AnimeUser animeUser = animeUserRepo.findById(animeUserId).orElseThrow(() -> new InvalidAnimeException("AnimeUser does not exist with given anime id and user id: " + animeUserId.getAnimeId() + " " + animeUserId.getUserId()));
        return AnimeUserMapper.mapToAnimeUserDto(animeUser);
    }

    @Override
    public AnimeUserDto updateAnimeUser(AnimeUserId animeUserId, AnimeUserDto updatedAnimeUser) {
        AnimeUser animeUser = animeUserRepo.findById(animeUserId).orElseThrow(() -> new InvalidAnimeException("AnimeUser does not exist with given anime id and user id: " + animeUserId.getAnimeId() + " " + animeUserId.getUserId()));
        animeUser.setScore(updatedAnimeUser.getScore());
        animeUser.setIsFavorite(updatedAnimeUser.getIsFavorite());
        animeUser.setStatus(updatedAnimeUser.getStatus());
        animeUser.setStartDate(updatedAnimeUser.getStartDate());
        animeUser.setEndDate(updatedAnimeUser.getEndDate());
        AnimeUser savedAnime = animeUserRepo.save(animeUser);
        return AnimeUserMapper.mapToAnimeUserDto(savedAnime);
    }

    @Override
    public void deleteAnimeUser(AnimeUserId animeUserId) {
        animeUserRepo.findById(animeUserId).orElseThrow(() -> new InvalidAnimeException("AnimeUser does not exist with given anime id and user id: " + animeUserId.getAnimeId() + " " + animeUserId.getUserId()));
        animeUserRepo.deleteById(animeUserId);
    }

    @Override
    public List<AnimeUserDto> getAnimeUserByUserId(Long userId, String title, String sort, int page) {

        List<AnimeUser> animeUserList;
        if(sort.equals("ASC"))
            animeUserList = animeUserRepo.findByAnimeTitleContainingIgnoreCaseOrderByScoreAsc(userId, title, PageRequest.of(page,40));
        else
            animeUserList = animeUserRepo.findByAnimeTitleContainingIgnoreCaseOrderByScoreDesc(userId, title, PageRequest.of(page,40));
        return animeUserList.stream().map(AnimeUserMapper::mapToAnimeUserDto).collect(Collectors.toList());
    }
}
