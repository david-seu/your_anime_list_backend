package david_seu.your_anime_list_backend.model;

import david_seu.your_anime_list_backend.model.utils.AnimeStatus;
import david_seu.your_anime_list_backend.model.utils.Type;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        indexes = {
                @Index(name = "anime_title_index", columnList = "title"),
                @Index(name = "anime_score_index", columnList = "score"),
                @Index(name = "anime_user_id_index", columnList = "user_id")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Anime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(length = 100000)
    private String synopsis;

    private Double score = -1.0;

    private Integer popularity = -1;

    private Integer nrEpisodes = 0;

    @Column(length = 500)
    private String pictureURL;

    @Column(length = 500)
    private String thumbnailURL;

    private String startDate;

    private String endDate;

    private Integer watching;

    private Integer completed;

    private Integer onHold;

    private Integer dropped;

    private Integer planToWatch;

    private Integer malId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "anime_genre",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "anime_studio",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Studio> studios = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "anime_tag",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Tag> tags = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 5000)
    private List<String> synonyms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "anime_related_anime",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "related_anime_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Anime> relatedAnime = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "anime_recommended_anime",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "recommended_anime_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Anime> recommendedAnime = new HashSet<>();



    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Enumerated(EnumType.STRING)
    private AnimeStatus status;

    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnimeSeason animeSeason;
}
