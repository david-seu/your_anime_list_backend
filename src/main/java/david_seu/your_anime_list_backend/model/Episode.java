//package david_seu.your_anime_list_backend.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import java.util.Date;
//
//@Entity
//@Table(
//        indexes = {
//                @Index(name = "episode_title_index", columnList = "title"),
//                @Index(name = "episode_season_index", columnList = "season"),
//                @Index(name = "episode_anime_id_index", columnList = "anime_id")
//        }
//
//)
//@NoArgsConstructor
//@AllArgsConstructor
//@Setter
//@Getter
//@ToString
//public class Episode {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long id;
//        private String title;
//        private Integer number;
//        private Integer season;
//        private Integer score = -1;
//
//        @ManyToOne
//        @JoinColumn(name = "anime_id", nullable = false, referencedColumnName = "id")
//        @OnDelete(action = OnDeleteAction.CASCADE)
//        private Anime anime;
//}
