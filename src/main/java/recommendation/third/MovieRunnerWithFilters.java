package recommendation.third;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import recommendation.first.Rating;

public class MovieRunnerWithFilters {

	// Path of files
	String ratedMoviesFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratedmovies_short.csv";
	String ratingsFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings_short.csv";

	/**
	 * This method prints the list of movies, one movie per line (print its rating
	 * first, followed by its title) in sorted order by ratings, lowest rating to
	 * highest rating with at least provided minimum ratings.
	 */
	public void printAverageRatings() {

		ThirdRatings tR = new ThirdRatings(ratingsFile);
		System.out.println("Number of Raters: " + tR.getNoOfRaters());

		MovieDatabase.initialize(ratedMoviesFile);
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		ArrayList<Rating> ratings = tR.getAverageRatings(minimalRaters);
		Collections.sort(ratings, comparator);

		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
		}
	}
	
	public void printAverageRatingsByYear() {
		
		ThirdRatings tR = new ThirdRatings(ratingsFile);
		System.out.println("Number of Raters: " + tR.getNoOfRaters());

		MovieDatabase.initialize(ratedMoviesFile);
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		ArrayList<Rating> ratings = tR.getAverageRatingsByFilter(minimalRaters,new YearAfterFilter(2000));
		Collections.sort(ratings, comparator);

		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem())+ " " + MovieDatabase.getTitle(rating.getItem()));
		}
		
	}
	
	// Comparator to compare ratings.
	private Comparator<Rating> comparator = new Comparator<Rating>() {
		@Override
		public int compare(Rating r1, Rating r2) {
			if (Double.compare(r1.getValue(), r2.getValue()) > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	};
}
