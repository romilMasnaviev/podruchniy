package ru.xorochki.resSearch.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.xorochki.resSearch.model.Review;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T23:04:31+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ReviewConverterImpl implements ReviewConverter {

    @Override
    public ReviewResponse reviewConvertToReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse reviewResponse = new ReviewResponse();

        reviewResponse.setId( review.getId() );
        reviewResponse.setMark( review.getMark() );
        reviewResponse.setComment( review.getComment() );
        reviewResponse.setRestaurant( review.getRestaurant() );

        return reviewResponse;
    }

    @Override
    public List<ReviewResponse> reviewConvertToReviewResponse(List<Review> reviews) {
        if ( reviews == null ) {
            return null;
        }

        List<ReviewResponse> list = new ArrayList<ReviewResponse>( reviews.size() );
        for ( Review review : reviews ) {
            list.add( reviewConvertToReviewResponse( review ) );
        }

        return list;
    }
}
