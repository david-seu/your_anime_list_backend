package david_seu.your_anime_list_backend.model.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"rank"})
public class JsonAnime {
    private Integer id;
    private String title;
    private String type;
    private Integer episodes;
    private String status;
    @JsonProperty("animeSeason")
    private JsonAnimeSeason season;
    private String picture;
    private String thumbnail;
    private String startDate;
    private String endDate;
    private Integer popularity;
    private Integer completed;
    private Integer watching;
    @JsonProperty("on_hold")
    private Integer onHold;
    private Integer dropped;
    @JsonProperty("plan_to_watch")
    private Integer planToWatch;
    private List<String> genre;
    private List<String> studios;
    private List<String> tags;
    private List<String> synonyms;
    private List<Integer> relatedAnime;
    private List<Integer> recommendations;
    private Double mean;
    private String synopsis;
}
