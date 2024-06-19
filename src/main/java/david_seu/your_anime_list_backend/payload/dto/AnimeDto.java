package david_seu.your_anime_list_backend.payload.dto;


import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.model.utils.AnimeStatus;
import david_seu.your_anime_list_backend.model.utils.Type;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnimeDto {


    private Long id;
    private String title;
    private String synopsis;
    private Double score;
    private Integer popularity;
    private Integer nrEpisodes;
    private String pictureURL;
    private String thumbnailURL;
    private String startDate;
    private String endDate;
    private Integer watching;
    private Integer completed;
    private Integer onHold;
    private Integer dropped;
    private Integer planToWatch;
    private Set<String> tags;
    private Set<String> genres;
    private Set<String> studios;
    private Set<String> synonyms;
    private Set<Anime> relatedAnime=null;
    private Set<Anime> recommendedAnime=null;
    private Type type;
    private Long userId=null;
    private User user=null;
    private AnimeStatus status;
    private AnimeSeason animeSeason;
    private Integer malId=null;

}
