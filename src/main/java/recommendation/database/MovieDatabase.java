package recommendation.database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

import recommendation.filters.Filter;
import recommendation.pojos.Movie;

public class MovieDatabase {
	private static HashMap<String, Movie> ourMovies;

	public static void initialize(String moviefile) {
		ourMovies = new HashMap<String, Movie>();
		loadMovies(moviefile);
	}

	private static void loadMovies(String filename) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filename), ',', '\"', 1);
			String[] line;
			while ((line = reader.readNext()) != null) {
				ourMovies.put(line[0], new Movie(line[0], line[1], line[2], line[4], line[5], line[3], line[7],
						Integer.parseInt(line[6])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean containsID(String id) {
		return ourMovies.containsKey(id);
	}

	public static int getYear(String id) {
		return ourMovies.get(id).getYear();
	}

	public static String getGenres(String id) {
		return ourMovies.get(id).getGenres();
	}

	public static String getTitle(String id) {
		return ourMovies.get(id).getTitle();
	}

	public static Movie getMovie(String id) {
		return ourMovies.get(id);
	}

	public static String getPoster(String id) {
		return ourMovies.get(id).getPoster();
	}

	public static int getMinutes(String id) {
		return ourMovies.get(id).getMinutes();
	}

	public static String getCountry(String id) {
		return ourMovies.get(id).getCountry();
	}

	public static String getDirector(String id) {
		return ourMovies.get(id).getDirector();
	}

	public static int size() {
		return ourMovies.size();
	}

	public static ArrayList<String> filterBy(Filter f) {
		ArrayList<String> list = new ArrayList<String>();
		for (String id : ourMovies.keySet()) {
			if (f.satisfies(id)) {
				list.add(id);
			}
		}

		return list;
	}

}
