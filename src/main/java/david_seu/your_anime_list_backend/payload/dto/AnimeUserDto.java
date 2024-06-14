package david_seu.your_anime_list_backend.payload.dto;

import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
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

    private AnimeUserId id;
    private Anime anime;
    private User user;

    private Integer score;
    private Boolean isFavorite;

    private String status;

    private Date startDate;
    private Date endDate;
}
