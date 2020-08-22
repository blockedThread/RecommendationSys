package recommendation.filters;

import recommendation.database.MovieDatabase;

public class MinutesFilter implements Filter{
	
	private int minMinutes = 0;
	private int maxMinutes = 0;
	
	@Override
	public boolean satisfies(String id) {
		int movieMinutes = MovieDatabase.getMinutes(id);
		return movieMinutes >= minMinutes && movieMinutes <= maxMinutes;
	}
	
	public MinutesFilter(int minMinutes, int maxMinutes) {
		this.minMinutes = minMinutes;
		this.maxMinutes = maxMinutes;
	}
}
