package recommendation.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import recommendation.pojos.Rating;

public class EfficientRater implements Rater{
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if(myRatings.containsKey(item)) 
        	return myRatings.get(item).getValue();
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(String movieId : myRatings.keySet()){
            list.add(myRatings.get(movieId).getItem());
        }
        
        return list;
    }
    
    public ArrayList<Rating> getRatings() {
    	return new ArrayList<>(myRatings.values());
    }
}
