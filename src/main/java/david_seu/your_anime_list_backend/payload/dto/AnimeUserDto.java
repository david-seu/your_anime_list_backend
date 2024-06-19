package david_seu.your_anime_list_backend.payload.dto;

import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.model.utils.WatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnimeUserDto {

    private Long id;
    private Long animeId;
    private Long userId;
    private AnimeDto anime=null;
    private User user=null;

    private Double score;
    private Boolean isFavorite;

    private WatchStatus status;

    private Date startDate;
    private Date endDate;
}
