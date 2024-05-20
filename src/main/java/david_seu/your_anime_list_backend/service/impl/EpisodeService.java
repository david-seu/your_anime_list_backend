package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.EpisodeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.EpisodeMapper;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.Episode;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.repo.IEpisodeRepo;
import david_seu.your_anime_list_backend.service.IEpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EpisodeService implements IEpisodeService {

    private final IEpisodeRepo episodeRepo;
    @Override
    public EpisodeDto createEpisode(EpisodeDto episodeDto) {
        Episode episode = EpisodeMapper.mapToEpisode(episodeDto);
        episode = episodeRepo.save(episode);
        return EpisodeMapper.mapToEpisodeDto(episode);
    }

    @Override
    public EpisodeDto getEpisodeById(Long episodeId) {
        Episode episode = episodeRepo.findById(episodeId).
                orElseThrow(() -> new ResourceNotFoundException("Episode does not exist with given id: " + episodeId));
        return EpisodeMapper.mapToEpisodeDto(episode);
    }

    @Override
    public List<EpisodeDto> getAllEpisodes(Integer page, User user) {
        int size =  episodeRepo.findAllByUser(user).size();
        if(size == 0)
        {
            return new ArrayList<>();
        }
        Integer totalPages = size / 10;
        if(page < 0){
            page = totalPages - page;
        }
        int pageToGet = page % totalPages;
        List<Episode> episodeList = episodeRepo.findAllByUserOrderByScore(user, PageRequest.of(pageToGet,10));
        return episodeList.stream().map(EpisodeMapper::mapToEpisodeDto).collect(Collectors.toList());
    }

    @Override
    public EpisodeDto updateEpisode(Long episodeId, EpisodeDto updatedEpisode) {
        Episode episode = episodeRepo.findById(episodeId).
                orElseThrow(() -> new ResourceNotFoundException("Episode does not exist with given id: " + episodeId));

        episode.setTitle(updatedEpisode.getTitle());
        episode.setNumber(updatedEpisode.getNumber());
        episode.setSeason(updatedEpisode.getSeason());
        episode.setScore(updatedEpisode.getScore());
        episode.setWatched(updatedEpisode.getWatched());
        episode.setAnime(updatedEpisode.getAnime());

        Episode updatedEpisodeObj = episodeRepo.save(episode);

        return EpisodeMapper.mapToEpisodeDto(updatedEpisodeObj);
    }

    @Override
    public void deleteEpisode(Long episodeId) {
        episodeRepo.findById(episodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Episode does not exist with given id: " + episodeId));

        episodeRepo.deleteById(episodeId);
    }

    @Override
    public List<EpisodeDto> getEpisodesByAnime(Anime anime) {
        return episodeRepo.getEpisodesByAnime(anime).stream().map(EpisodeMapper::mapToEpisodeDto).collect(Collectors.toList());
    }

}
