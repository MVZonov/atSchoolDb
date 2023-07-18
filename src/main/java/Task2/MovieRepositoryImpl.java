package Task2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {
    private List<Movie> movies;

    public MovieRepositoryImpl(Connection connection) {
        movies = new ArrayList<>();
    }

    @Override
    public Movie get(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public void save(Movie movie) {
        movies.add(movie);
    }

    @Override
    public void delete(Movie movie) {
        movies.remove(movie);
    }

    @Override
    public List<Movie> get(Director d) {
        List<Movie> directorMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getDirectorId()==d.getId()) {
                directorMovies.add(movie);
            }
        }
        return directorMovies;
    }
}
