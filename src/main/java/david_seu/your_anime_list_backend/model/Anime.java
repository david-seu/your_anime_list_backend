package david_seu.your_anime_list_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Anime")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Anime {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer score;
    private Boolean watched;
}
