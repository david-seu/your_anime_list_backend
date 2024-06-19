package david_seu.your_anime_list_backend.model.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JsonAnimeSeason {

    private Integer year = -1;
    private String season;
}
