package recommendation.second;

/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

import recommendation.first.FirstRatings;
import recommendation.first.Movie;
import recommendation.first.Rater;
import recommendation.first.Rating;

public class SecondRatings {
	private ArrayList<Movie> myMovies;
	private ArrayList<Rater> myRaters;

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

		for (Rater rater : myRaters) {
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

		for (Movie movie : myMovies) {
			double rating = getAverageByID(movie.getID(), minimalRaters);

			// Add rating if rating is greater than zero.
			if (Double.compare(rating, 0.0) > 0) {
				ratings.add(new Rating(movie.getID(), rating));
			}
		}

		return ratings;
	}

	/**
	 * This method returns the title of the movie with that ID. If the movie ID does
	 * not exist, then this method returns a String indicating the ID was not found.
	 * 
	 * @param movieId - id of the movie to find title.
	 * @return title of the movie.
	 */
	public String getTitle(String movieId) {
		String title = "ID was not found";

		for (Movie movie : myMovies) {
			if (movie.getID().equals(movieId)) {
				title = movie.getTitle();
				break;
			}
		}

		return title;
	}
	
	//Returns the number of movies.
	public int getNoOfMovies() {
		return myMovies.size();
	}
	
	//Returns the number of raters.
	public int getNoOfRaters() {
		return myRaters.size();
	}

	/**
	 * This method returns the movie ID of this movie. If the title is not found,
	 * returns an appropriate message such as “NO SUCH TITLE.”
	 * 
	 * @param title - title of a movie 
	 * @return
	 */
	public String getId(String title) {
		String movieId = "NO SUCH TITLE.";
		
		for(Movie movie : myMovies) {
			if(movie.getTitle().equals(title)) {
				movieId = movie.getID();
				break;
			}
		}
		
		return movieId;
	}
	
	//Constructor takes filenames and load them.
	public SecondRatings(String ratedMoviesFile, String ratingsFile) {
		FirstRatings fR = new FirstRatings();
		myMovies = fR.loadMovies(ratedMoviesFile);
		myRaters = fR.loadRaters(ratingsFile);
	}

	public SecondRatings() {
	}
}
