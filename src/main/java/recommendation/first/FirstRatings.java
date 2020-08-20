package recommendation.first;

import java.io.FileReader;
import java.io.IOException;
/**
 * Write a description of FirstRatings here.
 * 
 * @author (Himanshu Gajbhiye) 
 * @version (19/08/20)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.opencsv.CSVReader;

public class FirstRatings {

	public ArrayList<Movie> loadMovies(String filename) {
		ArrayList<Movie> movie = new ArrayList<Movie>();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filename), ',', '\"', 1);
			String[] line;
			while ((line = reader.readNext()) != null) {
				movie.add(new Movie(line[0], line[1], line[2], line[4], line[5], line[3], line[7],
						Integer.parseInt(line[6])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return movie;
	}

	public void testLoadMovies() {
		ArrayList<Movie> movies = loadMovies(
				"C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratedmovies_short.csv");

        System.out.println("Movies: " + movies.size());
//        for(Movie movie : movies) {
//          System.out.println(movie.toString());
//        }

		int comedyGenre = 0;
		for (Movie movie : movies) {
			String[] genre = movie.getGenres().split(",");
			for (String g : genre) {
				if ((g.trim()).equals("Comedy")) {
					comedyGenre++;
				}
			}
		}
		System.out.println("Comedy: " + comedyGenre + "\n");

		int greaterThan150 = 0;
		for (Movie movie : movies) {
			if (movie.getMinutes() > 150)
				greaterThan150++;
		}
		System.out.println("Greater than 150: " + greaterThan150 + "\n");

		HashMap<String, Integer> directorCount = new HashMap<String, Integer>();
		int maxCount = 0;

		for (Movie movie : movies) {
			String[] directors = movie.getDirector().split(",");
			for (String director : directors) {
				String temp = director.trim();
				directorCount.put(temp, directorCount.getOrDefault(temp, 0) + 1);
				maxCount = Math.max(maxCount, directorCount.get(temp));
			}
		}
		System.out.println("Maximum no of movies: " + maxCount);
		for (String director : directorCount.keySet()) {
			if (directorCount.get(director) == maxCount) {
				System.out.println(director);
			}
		}
		System.out.println();
	}

	public ArrayList<Rater> loadRaters(String filename) {
		ArrayList<Rater> raters = new ArrayList<Rater>();

		// Created a map to store rater corresponding to it's id and appended its
		// ratings.
		HashMap<String, Rater> ratersMap = new HashMap<>();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filename), ',', '\"', 1);
			String[] line;
			while ((line = reader.readNext()) != null) {
				Rater rater = ratersMap.get(line[0]);
				if (rater == null)
					ratersMap.put(line[0], new Rater(line[0]));
				ratersMap.get(line[0]).addRating(line[1], Double.parseDouble(line[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		raters.addAll(ratersMap.values());

		return raters;
	}

	public void testLoadRaters() {
		ArrayList<Rater> raters = loadRaters(
				"C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings_short.csv");

		System.out.println("Raters: " + raters.size());

//         for(Rater rater : raters) {
//             System.out.println(rater.getID() + " " + rater.numRatings());
//             
//             for(Rating rating : rater.getRatings()) {
//            	 System.out.println(rating.getItem() + " " + rating.getValue());
//             }
//             
//         }

		int maxRatings = 0;
		for (Rater rater : raters) {
			maxRatings = Math.max(maxRatings, rater.numRatings());
		}
		System.out.println("Max rating: " + maxRatings);
		
		for (Rater rater : raters) {
			if (rater.numRatings() == maxRatings) {
				System.out.println("Max rater " + rater.getID());
			}
		}
		System.out.println();
		
		HashSet<String> movies = new HashSet<>();
		
		for(Rater rater : raters) {
			movies.addAll(rater.getItemsRated());
		}
		System.out.println("Different movies rated: " + movies.size());
	}

	public void testLoadRaters(String id) {
		ArrayList<Rater> raters = loadRaters(
				"C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings_short.csv");

		int ratings = 0;
		for (Rater rater : raters) {
			if (rater.getID().equals(id)) {
				ratings = rater.numRatings();
				break;
			}
		}

		System.out.println("Max Ratings " + ratings);

	}
	
	public void numRatingMovie(String movieId) {
		ArrayList<Rater> raters = loadRaters(
				"C:\\Users\\himan\\eclipse-workspace\\RecommendationSys\\src\\main\\resources\\data\\ratings_short.csv");
		int ratings = 0;
		
		for(Rater rater : raters) {
			for(String movie : rater.getItemsRated()) {
				if(movieId.contentEquals(movie)) 
					ratings++;
			}
		}
		
		System.out.println("Movie: " + movieId + " " + ratings);
	}
}
