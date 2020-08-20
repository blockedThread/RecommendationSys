package recommendation.first;

import recommendation.second.MovieRunnerAverage;

public class Main {
	public static void main(String[] args) {
		FirstRatings fR = new FirstRatings();
		fR.testLoadMovies();
		fR.testLoadRaters();
		fR.testLoadRaters("193");
		fR.numRatingMovie("1798709");
		
		MovieRunnerAverage mra = new MovieRunnerAverage();
		mra.printAverageRatings();
		mra.getAverageRatingOneMovie();
	}
}
