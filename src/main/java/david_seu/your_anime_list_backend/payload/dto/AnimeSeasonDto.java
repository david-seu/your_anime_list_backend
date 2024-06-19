package david_seu.your_anime_list_backend.payload.dto;

import david_seu.your_anime_list_backend.model.utils.Season;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnimeSeasonDto {

    public Long id;

    public Season season;

    public Integer year;
}
