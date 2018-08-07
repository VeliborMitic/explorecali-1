package com.example.ec;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.service.TourPackageService;
import com.example.ec.service.TourService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {

	@Autowired
	private TourService tourService;

	@Autowired
	private TourPackageService tourPackageService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		tourPackageService.create("BC", "Backpack Cal");
		tourPackageService.create("CC", "California Calm");
		tourPackageService.create("CH", "California Hot springs");
		tourPackageService.create("CY", "Cycle California");
		tourPackageService.create("DS", "From Desert to Sea");
		tourPackageService.create("KC", "Kids California");
		tourPackageService.create("NW", "Nature Watch");
		tourPackageService.create("SC", "Snowboard Cali");
		tourPackageService.create("TC", "Taste of California");
		tourPackageService.lookUp().forEach(tourPackage -> System.out.println(tourPackage));
		TourFromFile.importTours().forEach(tour -> tourService.create(tour.title, tour.description, tour.blurb, Integer.parseInt(tour.price),
				tour.length, tour.bullets, tour.keywords, tour.packageType, Difficulty.valueOf(tour.difficulty), Region.findByLabel(tour.region)));
		System.out.println("Number of tours: " + tourService.total());
	}

	static class TourFromFile {
		private String packageType, title, description, blurb, price,
				length, bullets, keywords, difficulty, region;


		static List<TourFromFile> importTours() throws IOException {
			return new ObjectMapper()
					.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
					.readValue(TourFromFile.class.getResourceAsStream("/ExploreCalifornia.json"), new TypeReference<List<TourFromFile>>(){});
		}
	}
}
