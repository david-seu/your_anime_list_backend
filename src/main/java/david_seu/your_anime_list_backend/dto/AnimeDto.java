package david_seu.your_anime_list_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
