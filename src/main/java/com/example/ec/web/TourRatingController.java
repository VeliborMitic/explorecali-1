package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repository.TourRatingRepository;
import com.example.ec.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRepository tourRepository;
    private TourRatingRepository tourRatingRepository;

    protected TourRatingController() {
    }

    @Autowired
    public TourRatingController(TourRepository tourRepository, TourRatingRepository tourRatingRepository) {
        this.tourRepository = tourRepository;
        this.tourRatingRepository = tourRatingRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        Optional<Tour> tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour.get(), ratingDto.getCustomerId()), ratingDto.getScore(), ratingDto.getComment()));
    }

    private Optional<Tour> verifyTour(int tourId) throws NoSuchElementException {
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (!tour.isPresent())
            throw  new NoSuchElementException("Tour does not exist: " + tourId);
        return tour;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException exception) {
        return exception.getMessage();
    }
}
