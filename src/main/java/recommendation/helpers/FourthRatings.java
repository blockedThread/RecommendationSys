package recommendation.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import recommendation.database.MovieDatabase;
import recommendation.database.RaterDatabase;
import recommendation.filters.Filter;
import recommendation.filters.TrueFilter;
import recommendation.pojos.Rating;

public class FourthRatings {

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

		for (Rater rater : RaterDatabase.getRaters()) {
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

		Collections.sort(ratings);

		return ratings;
	}

	/**
	 * This method returns an ArrayList of type Rating of all the movies that have
	 * at least minimalRaters ratings and satisfies the filter criteria.
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

		Collections.sort(ratings);

		return ratings;
	}

	/**
	 * This method should first translate a rating from the scale 0 to 10 to the
	 * scale -5 to 5 and return the dot product of the ratings of movies that they
	 * both rated.
	 * 
	 * @param me - current rater
	 * @param r  - other rater
	 * @return dot product of ratings.
	 */
	private double dotProduct(String myId, String otherId) {
		ArrayList<Rating> myRatings = RaterDatabase.getRater(myId).getRatings();

		HashMap<String, Double> otherRatings = new HashMap<>();
		for (Rating rating : RaterDatabase.getRater(otherId).getRatings()) {
			otherRatings.put(rating.getItem(), rating.getValue());
		}

		double prod = 0;

		for (Rating rating : myRatings) {
			if (otherRatings.containsKey(rating.getItem())) {
				prod += (rating.getValue() - 5) * (otherRatings.get(rating.getItem()) - 5);
			}
		}

		return prod;
	}

	/**
	 * This method returns an ArrayList of type Rating sorted by ratings from
	 * highest to lowest rating with the highest rating first and only including
	 * those raters who have a positive similarity rating since those with negative
	 * values are not similar in any way.
	 * 
	 * Note that in each Rating object the item field is a rater’s ID, and the value
	 * field is the dot product comparison between that rater and the rater whose ID
	 * is the parameter to getSimilarities. Be sure not to use the dotProduct method
	 * with parameter id and itself!
	 * 
	 * @param raterId
	 * @return List of similar raters.
	 */
	private ArrayList<Rating> getSimilarities(String raterId) {

		ArrayList<Rating> similarities = new ArrayList<Rating>();

		for (Rater rater : RaterDatabase.getRaters()) {
			if (rater.getID().equals(raterId))
				continue;

			double prod = dotProduct(raterId, rater.getID());
			if (prod > 0) {
				similarities.add(new Rating(rater.getID(), prod));
			}
		}

		Collections.sort(similarities, Collections.reverseOrder());

		return similarities;
	}

	/**
	 * This method returns an ArrayList of type Rating, of movies and their
	 * weighted average ratings using only the top numSimilarRaters with positive
	 * ratings and including only those movies that have at least minimalRaters
	 * ratings from those most similar raters (not just minimalRaters ratings
	 * overall)
	 * 
	 * @param raterId - rater's Id
	 * @param numSimilarRaters
	 * @param minimalRaters
	 * @return
	 */
	public ArrayList<Rating> getSimilarRatings(String raterId, int numSimilarRaters, int minimalRaters) {
		ArrayList<Rating> similarRatings = new ArrayList<>();
		ArrayList<Rating> similarities = getSimilarities(raterId);

		for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
			int moviesRated = 0;
			double weightedAverage = 0;

			for (int i = 0; i < Math.min(similarities.size(), numSimilarRaters); i++) {
				String currRaterId = similarities.get(i).getItem();
				Rater currRater = RaterDatabase.getRater(currRaterId);

				for (Rating rating : currRater.getRatings()) {
					if (rating.getItem().equals(movieId)) {
						moviesRated++;
						weightedAverage += rating.getValue() * similarities.get(i).getValue();
					}
				}
			}

			if (moviesRated > minimalRaters) {
				similarRatings.add(new Rating(movieId, weightedAverage / moviesRated));
			}
		}

		Collections.sort(similarRatings);

		return similarRatings;
	}

	public ArrayList<Rating> getSimilarRatingsByFilter(String raterId, int numSimilarRaters, int minimalRaters,
			Filter criteria) {
		ArrayList<Rating> similarRatings = new ArrayList<Rating>();
		ArrayList<Rating> similarities = getSimilarities(raterId);

		for (String movieId : MovieDatabase.filterBy(criteria)) {
			int moviesRated = 0;
			double weightedAverage = 0;

			for (int i = 0; i < Math.min(similarities.size(), numSimilarRaters); i++) {
				String currRaterId = similarities.get(i).getItem();
				Rater currRater = RaterDatabase.getRater(currRaterId);

				for (Rating rating : currRater.getRatings()) {
					if (rating.getItem().equals(movieId)) {
						moviesRated++;
						weightedAverage += rating.getValue() * similarities.get(i).getValue();
					}
				}
			}

			if (moviesRated > minimalRaters) {
				similarRatings.add(new Rating(movieId, weightedAverage / moviesRated));
			}
		}

		Collections.sort(similarRatings);

		return similarRatings;
	}
}
