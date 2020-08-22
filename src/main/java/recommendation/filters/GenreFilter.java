package recommendation.filters;

import recommendation.database.MovieDatabase;

public class GenreFilter implements Filter{

	private String genre = "";
	
	@Override
	public boolean satisfies(String id) {
		String[] genres = MovieDatabase.getGenres(id).split(",");
		
		for(String g : genres) {
			if(g.trim().equals(genre))
				return true;
		}
		
		return false;
	}
	
	public GenreFilter(String genre) {
		this.genre = genre;
	}
}
