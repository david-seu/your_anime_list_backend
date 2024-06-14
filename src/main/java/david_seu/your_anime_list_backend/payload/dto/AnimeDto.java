package david_seu.your_anime_list_backend.payload.dto;


import david_seu.your_anime_list_backend.model.Genre;
import david_seu.your_anime_list_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimeDto {


    private Long id;
    private String title;
    private Integer score;
    private Integer numEpisodes;
    private Long userId;
    private User user=null;
    private Date releaseDate;
    private List<Genre> genres = new ArrayList<>();

}
