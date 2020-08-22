package recommendation.bootstrap;

import recommendation.database.MovieDatabase;
import recommendation.database.RaterDatabase;
import recommendation.filters.GenreFilter;
import recommendation.generateRatings.MovieRunnerSimilarRatings;

public class Main {
	
	static String ratingsFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings.csv";
	static String ratedMoviesFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratedmoviesfull.csv";
	
	public static void main(String[] args) {
		
		System.out.println("***** INITIALIZING AND LOADING DATABASE ********");
		MovieDatabase.initialize(ratedMoviesFile);
		RaterDatabase.initialize(ratingsFile);
		
		MovieRunnerSimilarRatings printRatings = new MovieRunnerSimilarRatings();
		printRatings.printSimilarRatingsByFilter(new GenreFilter("Action"));

	}
}
