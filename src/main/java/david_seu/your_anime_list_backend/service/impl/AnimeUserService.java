package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.exception.InvalidAnimeException;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeUserMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.AnimeUser;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.AnimeUserDto;
import david_seu.your_anime_list_backend.repo.IAnimeRepo;
import david_seu.your_anime_list_backend.repo.IAnimeUserRepo;
import david_seu.your_anime_list_backend.repo.IUserRepo;
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
    private IUserRepo userRepo;
    private IAnimeRepo animeRepo;
    @Override
    public AnimeUserDto addAnimeUser(AnimeUserDto animeUserDto) {
        if(animeUserRepo.findByAnimeIdAndUserId(animeUserDto.getAnimeId(), animeUserDto.getUserId()) != null){
            throw new InvalidAnimeException("AnimeUser already exists with given anime id and user id: " + animeUserDto.getAnimeId() + " " + animeUserDto.getUserId());
        }
        if(animeUserDto.getScore() > 10 || animeUserDto.getScore() < 0){
            throw new InvalidAnimeException("Score must be between 0 and 10");
        }
        if(animeUserDto.getStartDate().after(animeUserDto.getEndDate())){
            throw new InvalidAnimeException("Start date must be before end date");
        }
        Anime anime = animeRepo.findById(animeUserDto.getAnimeId()).orElseThrow(() -> new InvalidAnimeException("Anime does not exist with given id: " + animeUserDto.getAnimeId()));
        User user = userRepo.findById(animeUserDto.getUserId()).orElseThrow(() -> new InvalidAnimeException("User does not exist with given id: " + animeUserDto.getUserId()));
        System.out.println(animeUserDto);
        AnimeUser animeUser = AnimeUserMapper.mapToAnimeUser(animeUserDto);
        animeUser.setAnime(anime);
        animeUser.setUser(user);
        System.out.println(animeUser);
        AnimeUser savedAnime = animeUserRepo.save(animeUser);
        return AnimeUserMapper.mapToAnimeUserDto(savedAnime);
    }

    @Override
    public AnimeUserDto getAnimeUserById(Long animeId, Long userId) {
        AnimeUser animeUser = animeUserRepo.findByAnimeIdAndUserId(animeId, userId);
        if(animeUser == null)
            throw new ResourceNotFoundException("AnimeUser does not exist with given anime id and user id: " + animeId + " " + userId);
        return AnimeUserMapper.mapToAnimeUserDto(animeUser);
    }

    @Override
    public AnimeUserDto updateAnimeUser(Long animeId, Long userId, AnimeUserDto updatedAnimeUser) {
        AnimeUser animeUser = animeUserRepo.findByAnimeIdAndUserId(animeId, userId);
        if(animeUser == null)
            throw new InvalidAnimeException("AnimeUser does not exist with given anime id and user id: " + animeId + " " + userId);
        animeUser.setScore(updatedAnimeUser.getScore());
        animeUser.setIsFavorite(updatedAnimeUser.getIsFavorite());
        animeUser.setStatus(updatedAnimeUser.getStatus());
        animeUser.setStartDate(updatedAnimeUser.getStartDate());
        animeUser.setEndDate(updatedAnimeUser.getEndDate());
        AnimeUser savedAnime = animeUserRepo.save(animeUser);
        return AnimeUserMapper.mapToAnimeUserDto(savedAnime);
    }

    @Override
    public void deleteAnimeUser(Long animeId, Long userId) {
        AnimeUser animeUser = animeUserRepo.findByAnimeIdAndUserId(animeId, userId);
        if(animeUser == null)
            throw new InvalidAnimeException("AnimeUser does not exist with given anime id and user id: " + animeId + " " + userId);
        animeUserRepo.delete(animeUser);
    }

    @Override
    public List<AnimeUserDto> getAnimeUserByUserId(Long userId, String title, String sort, int page) {

        List<AnimeUser> animeUserList;
        if(sort.equals("ASC"))
            animeUserList = animeUserRepo.findByAnimeTitleContainingIgnoreCaseOrderByScoreAsc(userId, title, PageRequest.of(page,50));
        else
            animeUserList = animeUserRepo.findByAnimeTitleContainingIgnoreCaseOrderByScoreDesc(userId, title, PageRequest.of(page,50));
        return animeUserList.stream().map(AnimeUserMapper::mapToAnimeUserDto).collect(Collectors.toList());
    }
}
