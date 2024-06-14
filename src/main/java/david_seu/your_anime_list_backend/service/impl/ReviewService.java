package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.mapper.ReviewMapper;
import david_seu.your_anime_list_backend.model.AnimeUser;
import david_seu.your_anime_list_backend.model.Review;
import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
import david_seu.your_anime_list_backend.payload.dto.ReviewDto;
import david_seu.your_anime_list_backend.repo.IAnimeUserRepo;
import david_seu.your_anime_list_backend.repo.IReviewRepo;
import david_seu.your_anime_list_backend.service.IReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService implements IReviewService {

    private IReviewRepo reviewRepo;
    private IAnimeUserRepo animeUserRepo;
    @Override
    public ReviewDto addReview(ReviewDto reviewDto) {
        Review review = ReviewMapper.toReview(reviewDto);
        review.setAnimeUser(animeUserRepo.findById(reviewDto.getAnimeUserId()).orElseThrow(() -> new RuntimeException("Anime User does not exist with given id: " + reviewDto.getAnimeUserId())));
        review = reviewRepo.save(review);
        return ReviewMapper.toReviewDto(review);
    }

    @Override
    public ReviewDto getReviewById(Long review) {
        Review review1 = reviewRepo.findById(review).orElseThrow(() -> new RuntimeException("Review does not exist with given id: " + review));
        return ReviewMapper.toReviewDto(review1);
    }

    @Override
    public ReviewDto updateReview(Long reviewId, ReviewDto updatedReview) {
        Review review = reviewRepo.findById(reviewId).orElseThrow(() -> new RuntimeException("Review does not exist with given id: " + reviewId));
        review.setTitle(updatedReview.getTitle());
        review.setContent(updatedReview.getContent());
        review = reviewRepo.save(review);
        return ReviewMapper.toReviewDto(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepo.findById(reviewId).orElseThrow(() -> new RuntimeException("Review does not exist with given id: " + reviewId));
        reviewRepo.delete(review);
    }

    @Override
    public List<ReviewDto> getReviewByAnimeUserId(AnimeUserId animeUserId, int page) {
        AnimeUser animeUser = animeUserRepo.findById(animeUserId).orElseThrow(() -> new RuntimeException("Anime User does not exist with given id: " + animeUserId));
        return reviewRepo.findByAnimeUser(animeUser, PageRequest.of(page, 10)).stream().map(ReviewMapper::toReviewDto).toList();
    }
}
