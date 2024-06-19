package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.Review;
import david_seu.your_anime_list_backend.payload.dto.ReviewDto;

public class ReviewMapper {


    public static Review toReview(ReviewDto reviewDto) {
        return new Review(
                reviewDto.getId(),
                reviewDto.getTitle(),
                reviewDto.getContent(),
                reviewDto.getAnimeUser()
        );
    }

    public static ReviewDto toReviewDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getAnimeUser(),
                review.getAnimeUser().getAnime().getId(),
                review.getAnimeUser().getUser().getId()
        );
    }
}
