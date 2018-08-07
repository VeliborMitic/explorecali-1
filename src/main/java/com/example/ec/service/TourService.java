package com.example.ec.service;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.domain.Tour;
import com.example.ec.domain.TourPackage;
import com.example.ec.repository.TourPackageRepository;
import com.example.ec.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    private TourRepository repository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository repository, TourPackageRepository tourPackageRepository) {
        this.repository = repository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour create(String title, String description, String blurb, Integer price, String duration, String bullets, String keywords, String name, Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(name);
        if (tourPackage == null)
            throw new RuntimeException("Tour package does not exist:" + name);

        return repository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }

    public Iterable<Tour> lookUp() {
        return repository.findAll();
    }

    public long total() {
        return repository.count();
    }
}
