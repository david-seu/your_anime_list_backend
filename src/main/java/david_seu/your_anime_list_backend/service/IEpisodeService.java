package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.dto.EpisodeDto;
import david_seu.your_anime_list_backend.model.Anime;

import java.util.List;

public interface IEpisodeService {

    EpisodeDto createEpisode(EpisodeDto episodeDto);

    EpisodeDto getEpisodeById(Long episodeId);

    List<EpisodeDto> getAllEpisodes();

    EpisodeDto updateEpisode(Long episodeId, EpisodeDto updatedEpisode);

    void deleteEpisode(Long episodeId);

    List<EpisodeDto> getEpisodesByAnime(Anime anime);

}