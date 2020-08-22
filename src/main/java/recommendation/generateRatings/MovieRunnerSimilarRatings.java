package recommendation.generateRatings;

import java.util.ArrayList;
import java.util.Collections;

import recommendation.database.MovieDatabase;
import recommendation.database.RaterDatabase;
import recommendation.filters.AllFilters;
import recommendation.filters.DirectorsFilter;
import recommendation.filters.Filter;
import recommendation.filters.GenreFilter;
import recommendation.filters.MinutesFilter;
import recommendation.filters.TrueFilter;
import recommendation.filters.YearAfterFilter;
import recommendation.helpers.FourthRatings;
import recommendation.pojos.Rating;

public class MovieRunnerSimilarRatings {
	
	static String ratingsFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings.csv";
	static String ratedMoviesFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratedmoviesfull.csv";
	
	/**
	 * This method should first create a SecondRatings object, reading in data from
	 * the movie and ratings data files.
	 * 
	 * Then this method should print out the average ratings for a specific movie
	 * title
	 */
	public void getAverageRatingOneMovie() {
		FourthRatings fR = new FourthRatings();
		ArrayList<Rating> ratings = fR.getAverageRatings(0);

		String title = "The Godfather";
		String id = "";
		
		for(String movieId : MovieDatabase.filterBy(new TrueFilter())) {
			if(MovieDatabase.getTitle(movieId).equals(title)) {
				id = movieId;
				break;
			}
		}
		
		for (Rating rating : ratings) {
			if (rating.getItem().equals(id)) {
				System.out.println("Title: " + title + " Rating: " + rating.getValue());
				break;
			}
		}
	}
	
	/**
	 * This method prints the list of movies, one movie per line (print its rating
	 * first, followed by its title) in sorted order by ratings, lowest rating to
	 * highest rating with at least provided minimum ratings.
	 */
	public void printAverageRatings() {

		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		FourthRatings fR = new FourthRatings();
		ArrayList<Rating> ratings = fR.getAverageRatings(minimalRaters);
		Collections.sort(ratings);

		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
		}
	}

	/**
	 * This method prints the list of movies, one movie per line (print its rating
	 * first, followed by its title) in sorted order by ratings, lowest rating to
	 * highest rating with at least provided minimum ratings and released after the
	 * provided year.
	 */
	public void printAverageRatingsByYear() {

		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		FourthRatings fR = new FourthRatings();
		ArrayList<Rating> ratings = fR.getAverageRatingsByFilter(minimalRaters, new YearAfterFilter(2000));
		Collections.sort(ratings);

		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " "
					+ MovieDatabase.getTitle(rating.getItem()));
		}

	}
	
	/**
	 * This method prints the list of movies, one movie per line (print its rating
	 * first, followed by its title) in sorted order by ratings, lowest rating to
	 * highest rating with at least provided minimum ratings and satisfying genre.
	 */
	public void printAverageRatingsByGenre() {

		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		FourthRatings fR = new FourthRatings();
		ArrayList<Rating> ratings = fR.getAverageRatingsByFilter(minimalRaters, new GenreFilter("Crime"));
		Collections.sort(ratings);

		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
			System.out.println(MovieDatabase.getGenres(rating.getItem()));
		}

	}
	
	public void printAverageRatingsByMinutes() {

		FourthRatings fR = new FourthRatings();
		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		ArrayList<Rating> ratings = fR.getAverageRatingsByFilter(minimalRaters, new MinutesFilter(110, 170));
		Collections.sort(ratings);
		
		System.out.println("Found movies: " + ratings.size());
		
		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " +  MovieDatabase.getMinutes(rating.getItem())+ " " + MovieDatabase.getTitle(rating.getItem()));
		}

	}
	
	public void printAverageRatingsByDirectors() {

		FourthRatings fR = new FourthRatings();
		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());

		int minimalRaters = 1;

		ArrayList<Rating> ratings = fR.getAverageRatingsByFilter(minimalRaters, new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze"));
		Collections.sort(ratings);
		
		System.out.println("Found Movies: " + ratings.size());
		
		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
			System.out.println(MovieDatabase.getDirector(rating.getItem()));
		}

	}
	
	public void printAverageRatingsByYearAfterAndGenre () {

		FourthRatings fR = new FourthRatings();
		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());
		
		AllFilters allFilters = new AllFilters();
		allFilters.addFilter(new YearAfterFilter(1980));
		allFilters.addFilter(new GenreFilter("Romance"));
		int minimalRaters = 1;

		ArrayList<Rating> ratings = fR.getAverageRatingsByFilter(minimalRaters, allFilters);
		Collections.sort(ratings);
		
		System.out.println("Found Movies: " + ratings.size());
		
		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getMinutes(rating.getItem()) + " " 
						+ MovieDatabase.getTitle(rating.getItem()));
			System.out.println(MovieDatabase.getDirector(rating.getItem()));
		}

	}
	
	public void printAverageRatingsByDirectorsAndMinutes() {

		FourthRatings fR = new FourthRatings();
		System.out.println("Number of Raters: " + RaterDatabase.size());
		System.out.println("Number of Movies: " + MovieDatabase.size());
		
		AllFilters allFilters = new AllFilters();
		allFilters.addFilter(new DirectorsFilter("Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola"));
		allFilters.addFilter(new MinutesFilter(30, 170));
		int minimalRaters = 1;

		ArrayList<Rating> ratings = fR.getAverageRatingsByFilter(minimalRaters, allFilters);
		Collections.sort(ratings);
		
		System.out.println("Found Movies: " + ratings.size());
		
		for (Rating rating : ratings) {
			System.out.println(rating.getValue() + " " + MovieDatabase.getMinutes(rating.getItem()) + " " 
						+ MovieDatabase.getTitle(rating.getItem()));
			System.out.println(MovieDatabase.getDirector(rating.getItem()));
		}

	}
	
	public void printSimilarRatings() {
		FourthRatings fR = new FourthRatings();
		ArrayList<Rating> ratings = fR.getSimilarRatings("65", 20, 5);
		
		for(Rating rating : ratings) {
			System.out.println(MovieDatabase.getTitle(rating.getItem()));
		}
	}
	
	public void printSimilarRatingsByFilter(Filter f) {
		FourthRatings fR = new FourthRatings();
		ArrayList<Rating> ratings = fR.getSimilarRatingsByFilter("65", 5, 20, f);
		
		for(Rating rating : ratings) {
			System.out.println(MovieDatabase.getTitle(rating.getItem()));
		}
	}
}
