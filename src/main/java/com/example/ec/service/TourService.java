package com.example.ec.service;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.domain.Tour;
import com.example.ec.domain.TourPackage;
import com.example.ec.repository.TourPackageRepository;
import com.example.ec.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TourService {
    private TourRepository repository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository repository, TourPackageRepository tourPackageRepository) {
        this.repository = repository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour create(String title, String description, String blurb, Integer price, String duration, String bullets, String keywords, String code, Difficulty difficulty, Region region) {
        Optional<TourPackage> tourPackage = tourPackageRepository.findById(code);
        if (!tourPackage.isPresent())
            throw new RuntimeException("Tour package does not exist:" + code);

        return repository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage.get(), difficulty, region));
    }

    public Iterable<Tour> lookUp() {
        return repository.findAll();
    }

    public long total() {
        return repository.count();
    }
}
