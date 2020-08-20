package recommendation.second;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import recommendation.first.Rating;

public class MovieRunnerAverage {

	// Path of files
	String ratedMoviesFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratedmovies_short.csv";
	String ratingsFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings_short.csv";

	/**
	 * This method prints the list of movies, one movie per line (print its
	 * rating first, followed by its title) in sorted order by ratings, lowest
	 * rating to highest rating with at least provided minimum ratings.
	 */
	public void printAverageRatings() {

		SecondRatings sR = new SecondRatings(ratedMoviesFile, ratingsFile);
		System.out.println("Number of Movies: " + sR.getNoOfMovies());
		System.out.println("Number of Raters: " + sR.getNoOfRaters());

		int minimalRaters = 3;

		ArrayList<Rating> ratings = sR.getAverageRatings(minimalRaters);
		Collections.sort(ratings, comparator);

		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + sR.getTitle(rating.getItem()));
		}
	}

	/**
	 * This method should first create a SecondRatings object, reading in data from
	 * the movie and ratings data files.
	 * 
	 * Then this method should print out the average ratings for a specific movie
	 * title
	 */
	public void getAverageRatingOneMovie() {
		SecondRatings sR = new SecondRatings(ratedMoviesFile, ratingsFile);
		ArrayList<Rating> ratings = sR.getAverageRatings(0);

		String title = "The Godfather";
		String id = sR.getId(title);

		for (Rating rating : ratings) {
			if (rating.getItem().equals(id)) {
				System.out.println("Title: " + title + " Rating: " + rating.getValue());
				break;
			}
		}
	}
	
	//Comparator to compare ratings.
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
