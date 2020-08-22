package recommendation.helpers;

import java.util.ArrayList;

import recommendation.pojos.Rating;

public interface Rater {
	 public void addRating(String item, double rating);
	 public boolean hasRating(String item);
	 public String getID();
	 public double getRating(String item);
	 public int numRatings();
	 public ArrayList<String> getItemsRated();
	 public ArrayList<Rating> getRatings();
}
