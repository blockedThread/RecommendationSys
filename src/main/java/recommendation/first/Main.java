package recommendation.first;

import recommendation.third.MovieRunnerWithFilters;

public class Main {
	
	static String ratingsFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings_short.csv";
	static String ratedMoviesFile = "C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratedmovies_short.csv";
	
	public static void main(String[] args) {
//		FirstRatings fR = new FirstRatings();
//		fR.testLoadMovies();
//		fR.testLoadRaters();
//		fR.testLoadRaters("193");
//		fR.numRatingMovie("1798709");
//		
//		MovieRunnerAverage mra = new MovieRunnerAverage();
//		mra.printAverageRatings();
//		mra.getAverageRatingOneMovie();
		
		MovieRunnerWithFilters mRwF = new MovieRunnerWithFilters();
		mRwF.printAverageRatingsByYear();
	}
}
