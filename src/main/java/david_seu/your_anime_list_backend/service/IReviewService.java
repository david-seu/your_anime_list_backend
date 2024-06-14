package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
import david_seu.your_anime_list_backend.payload.dto.ReviewDto;

import java.util.List;

public interface IReviewService {

    ReviewDto addReview(ReviewDto reviewDto);

    ReviewDto getReviewById(Long review);

    ReviewDto updateReview(Long reviewId, ReviewDto updatedReview);

    void deleteReview(Long reviewId);

    List<ReviewDto> getReviewByAnimeUserId(AnimeUserId animeUserId, int page);

}
