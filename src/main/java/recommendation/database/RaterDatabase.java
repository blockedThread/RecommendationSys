package recommendation.database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.opencsv.CSVReader;
import recommendation.helpers.EfficientRater;
import recommendation.helpers.Rater;

/**
 * Write a description of RaterDatabase here.
 * 
 * @author (Himanshu Gajbhiye)
 * @version (a version number or a date)
 */
public class RaterDatabase {
	private static HashMap<String, Rater> ourRaters;

	public static void initialize(String filename) {
		ourRaters = new HashMap<String, Rater>();
		addRatings(filename);
	}

	public static void addRatings(String filename) {

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filename), ',', '\"', 1);
			String[] line;
			while ((line = reader.readNext()) != null) {
				addRaterRating(line[0], line[1], Double.parseDouble(line[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addRaterRating(String raterID, String movieID, double rating) {
		Rater rater = null;
		if (ourRaters.containsKey(raterID)) {
			rater = ourRaters.get(raterID);
		} else {
			rater = new EfficientRater(raterID);
			ourRaters.put(raterID, rater);
		}
		rater.addRating(movieID, rating);
	}

	public static Rater getRater(String id) {
		return ourRaters.get(id);
	}

	public static ArrayList<Rater> getRaters() {
		ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());

		return list;
	}

	public static int size() {
		return ourRaters.size();
	}

}
