package com.example.ec.service;

import com.example.ec.domain.TourPackage;
import com.example.ec.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourPackageService {
    private TourPackageRepository repository;

    @Autowired
    public TourPackageService(TourPackageRepository repository) {
        this.repository = repository;
    }

    public TourPackage create(String code, String name) {
        if (!repository.existsById(code))
            repository.save(new TourPackage(code, name));

        return null;
    }

    public Iterable<TourPackage> lookUp() {
        return repository.findAll();
    }

    public long total() {
        return repository.count();
    }
}
