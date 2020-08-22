package recommendation.filters;

import java.util.HashSet;

import recommendation.database.MovieDatabase;

public class DirectorsFilter implements Filter{
	
	//String of comma separated directors.
	private String directors = "";
	
	@Override
	public boolean satisfies(String id) {
		
		HashSet<String> dirs = new HashSet<>();
		for(String d : directors.split(",")) {
			dirs.add(d.trim());
		}
		
		String[] currDirs = MovieDatabase.getDirector(id).split(",");
		for(String d : currDirs) {
			if(dirs.contains(d.trim()))
				return true;
		}
		
		return false;
	}
	
	public DirectorsFilter(String directors) {
		this.directors = directors;
	}
}
