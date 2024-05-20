package david_seu.your_anime_list_backend.payload.dto;

import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto {

    private Long id;
    private String title;
    private Integer number;
    private Integer season;
    private Integer score;
    private Boolean watched;
    private String animeTitle;
    private Anime anime=null;
    private Long userId;
    private User user=null;
}
