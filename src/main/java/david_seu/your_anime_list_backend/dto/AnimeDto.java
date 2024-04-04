package david_seu.your_anime_list_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimeDto {


    private Long id;
    private String title;

    private Integer score;

    private Boolean watched;

}
