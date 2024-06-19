package david_seu.your_anime_list_backend.payload.dto;

import david_seu.your_anime_list_backend.model.AnimeUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {

    private Long Id;

    private String title;

    private String content;

    private AnimeUser animeUser;

    private Long animeId;

    private Long userId;

}
