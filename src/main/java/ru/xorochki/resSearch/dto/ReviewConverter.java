package ru.xorochki.resSearch.dto;

import org.mapstruct.Mapper;
import ru.xorochki.resSearch.model.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewConverter {
    ReviewResponse reviewConvertToReviewResponse(Review review);

    List<ReviewResponse> reviewConvertToReviewResponse(List<Review> reviews);
}
