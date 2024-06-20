package david_seu.your_anime_list_backend.repo.util;

import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.model.utils.AnimeStatus;
import david_seu.your_anime_list_backend.model.utils.Type;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class AnimeSpecification {

    public static Specification<Anime> filterByAttributes(
            String title,
            Set<AnimeSeason> animeSeason,
            Set<Genre> genres,
            Set<Tag> tags,
            Set<Studio> studios,
            Set<Type> type,
            AnimeStatus status,
            String sortBy,
            boolean asc) {

        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            if (title != null && !title.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (animeSeason != null && !animeSeason.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.and(root.get("animeSeason").in(animeSeason)));
            }

            if (genres != null && !genres.isEmpty()) {
                Join<Anime, Genre> genreJoin = root.join("genres", JoinType.LEFT);
                predicate = criteriaBuilder.and(predicate, genreJoin.in(genres));
            }

            if (tags != null && !tags.isEmpty()) {
                Join<Anime, Tag> tagJoin = root.join("tags", JoinType.LEFT);
                predicate = criteriaBuilder.and(predicate, tagJoin.in(tags));
            }

            if (studios != null && !studios.isEmpty()) {
                Join<Anime, Studio> studioJoin = root.join("studios", JoinType.LEFT);
                predicate = criteriaBuilder.and(predicate, studioJoin.in(studios));
            }

            if (type != null) {
                predicate = criteriaBuilder.and(predicate, root.get("type").in(type));
            }

            if (status != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
            }

            // Custom sorting with nulls last
            if (sortBy != null) {
                Expression<?> sortExpression = root.get(sortBy);
                if (sortBy.equals("score")) {
                    Predicate isNotNull = criteriaBuilder.isNotNull(sortExpression);
                    if (asc) {
                        query.orderBy(criteriaBuilder.asc(criteriaBuilder.selectCase()
                                .when(isNotNull, sortExpression)
                                .otherwise(criteriaBuilder.literal(Integer.MAX_VALUE))));
                    } else {
                        query.orderBy(criteriaBuilder.desc(criteriaBuilder.selectCase()
                                .when(isNotNull, sortExpression)
                                .otherwise(criteriaBuilder.literal(Integer.MIN_VALUE))));
                    }
                } else {
                    if (asc) {
                        query.orderBy(
                                criteriaBuilder.asc(
                                        criteriaBuilder.selectCase()
                                                .when(criteriaBuilder.equal(sortExpression, 0), 1)
                                                .otherwise(0)
                                ),
                                criteriaBuilder.asc(sortExpression)
                        );
                    } else {
                        query.orderBy(
                                criteriaBuilder.asc(
                                        criteriaBuilder.selectCase()
                                                .when(criteriaBuilder.equal(sortExpression, 0), 0)
                                                .otherwise(1)
                                ),
                                criteriaBuilder.desc(sortExpression)
                        );
                    }
                }
            }
            return predicate;
        };
    }
}