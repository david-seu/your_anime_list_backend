package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.model.faker.CustomFaker;
import david_seu.your_anime_list_backend.model.utils.AnimeStatus;
import david_seu.your_anime_list_backend.model.utils.Season;
import david_seu.your_anime_list_backend.model.utils.Type;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.mapper.AnimeMapper;
import david_seu.your_anime_list_backend.repo.*;
import david_seu.your_anime_list_backend.repo.util.AnimeSpecification;
import david_seu.your_anime_list_backend.service.IAnimeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AnimeService implements IAnimeService {

    private IAnimeRepo animeRepo;
    private IUserRepo userRepo;
    private IAnimeSeasonRepo animeSeasonRepo;
    private IGenreRepo genreRepo;
    private IStudioRepo studioRepo;
    private ITagRepo tagRepo;

    @Override
    public AnimeDto createAnime(AnimeDto animeDto) {
        Anime anime = AnimeMapper.mapToAnime(animeDto);
        fillAnime(animeDto, anime);
        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime);
    }

    private void fillAnime(AnimeDto animeDto, Anime anime)
    {
        System.out.println("AnimeDto: " + animeDto);
        AnimeSeason animeSeason = getAnimeSeason(animeDto.getAnimeSeason());
        if(animeSeason == null)
        {
            animeSeason = animeSeasonRepo.save(animeDto.getAnimeSeason());
            anime.setAnimeSeason(animeSeason);
        }
        else{
            anime.setAnimeSeason(animeSeason);
        }

        Set<Tag> tags = getTags(animeDto.getTags());
        Set<Genre> genres = getGenres(animeDto.getGenres());
        Set<Studio> studios = getStudios(animeDto.getStudios());

        anime.setTags(tags);
        anime.setGenres(genres);
        anime.setStudios(studios);
    }

    @Override
    public AnimeDto getAnimeById(Long animeId) {
        Anime anime = animeRepo.findById(animeId).
                orElseThrow(() ->
                        new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        return AnimeMapper.mapToAnimeDto(anime);
    }

    @Override
    public List<AnimeDto> getAllAnime(Integer page, String sortDirection, String title, String season, Integer year, Set<String> genres, Set<String> tags, Set<String> studios, Set<String> type, String status, String orderBy) {
        AnimeSeason animeSeason = null;
        if (season != null && year != null) {
            animeSeason = animeSeasonRepo.findByYearAndSeason(year, Season.valueOf(season));
            if (animeSeason == null) {
                throw new ResourceNotFoundException("Anime season does not exist with given year: " + year + " and season: " + season);
            }
        }

        Set<Type> typeObj = type == null ? null : type.stream().map(Type::valueOf).collect(Collectors.toSet());
        AnimeStatus statusObj = status == null ? null : AnimeStatus.valueOf(status);

        Set<Genre> genreSet = getGenres(genres);
        Set<Tag> tagSet = getTags(tags);
        Set<Studio> studioSet = getStudios(studios);

        orderBy = orderBy == null ? "score" : orderBy;

        try {
            List<Anime> animeList = animeRepo.findAll(AnimeSpecification.filterByAttributes(title, animeSeason, genreSet, tagSet, studioSet, typeObj, statusObj, orderBy, sortDirection.equals("asc")), PageRequest.of(page, 50)).getContent();
            return animeList.stream().map(AnimeMapper::mapToAnimeDto).collect(Collectors.toList());
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            throw new ResourceNotFoundException("No animes found");
        }
    }

    private Set<Genre> getGenres(Set<String> genres) {
        Set<Genre> genreSet = new HashSet<>();
        if (genres != null) {
            for (String genre : genres) {
                Genre genreObj = genreRepo.findByName(genre);
                if (genreObj == null) {
                    throw new ResourceNotFoundException("Genre does not exist with given name: " + genre);
                }
                genreSet.add(genreObj);
            }
        }
        return genreSet;
    }

    private AnimeSeason getAnimeSeason(AnimeSeason animeSeason)
    {
        return animeSeasonRepo.findByYearAndSeason(animeSeason.getYear(), animeSeason.getSeason());
    }

    private Set<Studio> getStudios(Set<String> studios) {
        Set<Studio> studioSet = new HashSet<>();
        if (studios != null) {
            for (String studio : studios) {
                Studio studioObj = studioRepo.findByName(studio);
                if (studioObj == null) {
                    throw new ResourceNotFoundException("Studio does not exist with given name: " + studio);
                }
                studioSet.add(studioObj);
            }
        }
        return studioSet;
    }

    private Set<Tag> getTags(Set<String> tags) {
        Set<Tag> tagSet = new HashSet<>();
        if (tags != null) {
            for (String tag : tags) {
                Tag tagObj = tagRepo.findByName(tag);
                if (tagObj == null) {
                    throw new ResourceNotFoundException("Tag does not exist with given name: " + tag);
                }
                tagSet.add(tagObj);
            }
        }
        return tagSet;
    }


    @Override
    public AnimeDto updateAnime(Long animeId, AnimeDto updatedAnime) {
        System.out.println("Updated anime: " + updatedAnime);
        Anime anime = animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));
        anime.setTitle(updatedAnime.getTitle());
        anime.setSynopsis(updatedAnime.getSynopsis());
        anime.setThumbnailURL(updatedAnime.getThumbnailURL());
        anime.setPictureURL(updatedAnime.getPictureURL());
        anime.setStartDate(updatedAnime.getStartDate());
        anime.setEndDate(updatedAnime.getEndDate());
        anime.setType(updatedAnime.getType());
        anime.setStatus(updatedAnime.getStatus());

        fillAnime(updatedAnime, anime);

        Anime updatedAnimeObj = animeRepo.save(anime);

        return AnimeMapper.mapToAnimeDto(updatedAnimeObj);
    }

    @Override
    public void deleteAnime(Long animeId) {
        animeRepo.findById(animeId).orElseThrow(() -> new ResourceNotFoundException("Anime does not exist with given id: " + animeId));

        animeRepo.deleteById(animeId);
    }

    @Override
    public AnimeDto createAnime() {
        User user = userRepo.findByUsername("zyk3l");
        CustomFaker faker = new CustomFaker();
        Anime anime = new Anime();
        anime.setTitle(faker.anime().nextAnimeTitle());
        anime.setScore((double) faker.number().numberBetween(1,10));
        anime.setUser(user);

        Anime savedAnime = animeRepo.save(anime);
        return AnimeMapper.mapToAnimeDto(savedAnime);
    }

    @Override
    public AnimeDto getAnimeByTitle(String title) {

        Anime anime = animeRepo.findByTitle(title);
        return AnimeMapper.mapToAnimeDto(anime);
    }

    @Override
    public List<Integer> getScoresCount() {
        return animeRepo.findAllGroupByScore();
    }

//    @Override
//    public List<AnimeDto> getAnimeByGenre(String genre) {
//        Type typeObj = genreRepo.findByName(genre);
//        if(typeObj == null){
//            throw new ResourceNotFoundException("Genre does not exist with given name: " + genre);
//        }
//        List<Anime> animeList = animeRepo.findByGenre(typeObj);
//        return animeList.stream().map((anime) -> {
//            Integer numEpisodes = episodeRepo.getEpisodesByAnime(anime).size();
//            return AnimeMapper.mapToAnimeDto(anime, numEpisodes);
//        }).collect(Collectors.toList());
//    }

//    @Override
//    public AnimeDto getRandomAnime() {
//        List<Anime> animeList = animeRepo.findAll();
//        if (animeList.isEmpty()) {
//            throw new ResourceNotFoundException("No animes found");
//        }
//        Anime randomAnime = animeList.get(new Random().nextInt(animeList.size()));
//        return AnimeMapper.mapToAnimeDto(randomAnime);
//    }


}
