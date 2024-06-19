//package david_seu.your_anime_list_backend.mapper;
//
//import david_seu.your_anime_list_backend.payload.dto.EpisodeDto;
//import david_seu.your_anime_list_backend.model.Episode;
//
//public class EpisodeMapper {
//
//        public static EpisodeDto mapToEpisodeDto(Episode episode) {
//            return new EpisodeDto(episode.getId(), episode.getTitle(), episode.getNumber(), episode.getSeason(), episode.getScore(), episode.getWatched(), episode.getAnime().getTitle(), episode.getAnime());
//        }
//
//        public static Episode mapToEpisode(EpisodeDto episodeDto){
//            return new Episode(episodeDto.getId(), episodeDto.getTitle(), episodeDto.getNumber(), episodeDto.getSeason(), episodeDto.getScore(), episodeDto.getWatched(), episodeDto.getAnime());
//        }
//}
