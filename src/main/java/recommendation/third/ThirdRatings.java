package recommendation.third;

import java.util.ArrayList;

import recommendation.first.FirstRatings;
import recommendation.first.Rating;
import recommendation.second.EfficientRater;

public class ThirdRatings {
//	private ArrayList<Movie> myMovies;
	private ArrayList<EfficientRater> myRaters;

	/**
	 * This method returns a double representing the average movie rating for this
	 * ID if there are at least minimalRaters ratings.
	 * 
	 * If there are not minimalRaters ratings, then it returns 0.0.
	 * 
	 * @param movieId       - id of a movie of which average is to be calculated.
	 * @param minimalRaters - minimum number of raters required.
	 * @return - average rating in double
	 */
	private double getAverageByID(String movieId, int minimalRaters) {
		double avgRating = 0;
		int noOfRaters = 0;

		for (EfficientRater rater : myRaters) {
			for (Rating rating : rater.getRatings()) {
				if (rating.getItem().equals(movieId)) {
					avgRating += rating.getValue();
					noOfRaters++;
				}
			}
		}

		if (noOfRaters >= minimalRaters) {
			return avgRating / noOfRaters;
		}

		return 0.0;
	}

	/**
	 * This method finds the average rating for every movie that has been rated by
	 * at least minimalRaters raters.
	 * 
	 * Store each such rating in a Rating object in which the movie ID and the
	 * average rating are used in creating the Rating object.
	 * 
	 * The method getAverageRatings should return an ArrayList of all the Rating
	 * objects for movies that have at least the minimal number of raters supplying
	 * a rating.
	 * 
	 * @param minimalRaters - minimum number of rater required.
	 * @return - list of ratings.
	 */
	public ArrayList<Rating> getAverageRatings(int minimalRaters) {

		ArrayList<Rating> ratings = new ArrayList<Rating>();

		for (String movie : MovieDatabase.filterBy(new TrueFilter())) {
			double rating = getAverageByID(movie, minimalRaters);

			// Add rating if rating is greater than zero.
			if (Double.compare(rating, 0.0) > 0) {
				ratings.add(new Rating(movie, rating));
			}
		}

		return ratings;
	}

	/**
	 * This method returns an ArrayList of type Rating of all the
	 * movies that have at least minimalRaters ratings and satisfies the filter
	 * criteria.
	 * 
	 * @param minimalRaters
	 * @param criteria
	 * @return
	 */
	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter criteria) {
		ArrayList<Rating> ratings = new ArrayList<>();
		ArrayList<String> movieIds = MovieDatabase.filterBy(criteria);

		for (String movieId : movieIds) {
			double rating = getAverageByID(movieId, minimalRaters);

			// Add rating if rating is greater than zero.
			if (Double.compare(rating, 0.0) > 0) {
				ratings.add(new Rating(movieId, rating));
			}
		}

		return ratings;
	}

	// Returns the number of raters.
	public int getNoOfRaters() {
		return myRaters.size();
	}

	// Constructor takes filenames and load them.
	public ThirdRatings(String ratingsFile) {
		FirstRatings fR = new FirstRatings();
		myRaters = fR.loadRaters(ratingsFile);
	}

	public ThirdRatings() {
	}
}
