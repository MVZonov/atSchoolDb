import Task2.Director;
import Task2.DirectorRepository;
import Task2.DirectorRepositoryImpl;
import Task2.Movie;
import Task2.MovieRepository;
import Task2.MovieRepositoryImpl;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Task2Test {
    private Connection connection;
    private MovieRepository movieRepository;
    private DirectorRepository directorRepository;

    @BeforeAll
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE Directors (id SERIAL PRIMARY KEY, " +
                "first_name TEXT, last_name TEXT, " +
                "birth_date DATE, country TEXT)");
        statement.executeUpdate("CREATE TABLE Movies (id SERIAL PRIMARY KEY," +
                "title TEXT,genre TEXT,release_date DATE, director_id INT, FOREIGN KEY (director_id) REFERENCES Directors(id))");
        directorRepository = new DirectorRepositoryImpl(connection);
        movieRepository = new MovieRepositoryImpl(connection);

        int id1 = 1, id2 = 2;
        String firstName1 = "Василий", firstName2 = "Геннадий";
        String lastName1 = "Пупкин", lastName2 = "Огурцов";
        LocalDate localDate1 = LocalDate.of(1979, 1, 12), localDate2 = LocalDate.of(1965, 1, 14);
        java.sql.Date birthDate1 = java.sql.Date.valueOf(localDate1), birthDate2 = java.sql.Date.valueOf(localDate2);
        String country1 = "Russland", country2 = "Russland";

        Director director1 = new Director(id1, firstName1, lastName1, birthDate1, country1);
        Director director2 = new Director(id2, firstName2, lastName2, birthDate2, country2);
        directorRepository.save(director1);
        directorRepository.save(director2);
    }

    @AfterAll
    public void drop() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE Movies");
        statement.execute("DROP TABLE Directors");
        connection.close();
    }

    @DisplayName("Проверяем что в БД пусто")
    @Test
    @Order(1)
    public void getBeforeSave() {
        Movie movie = movieRepository.get(1);
        assertNull(movie);
    }

    @Test
    @Order(2)
    public void testAddMovie() {
        // Создаем объект фильма
        int id1 = 1, id2= 2, id3 = 3;
        String title1 = "Побег", title2 = "Возвращение", title3 = "Скала";
        String genre1 = "Приключения", genre2 = "Фантастика";
        LocalDate localDateFilm1 = LocalDate.of(1999, 1, 12);
        LocalDate localDateFilm2 = LocalDate.of(2000, 1, 12);
        LocalDate localDateFilm3 = LocalDate.of(2006, 1, 12);
        java.sql.Date release1 = java.sql.Date.valueOf(localDateFilm1);
        java.sql.Date release2 = java.sql.Date.valueOf(localDateFilm2);
        java.sql.Date release3 = java.sql.Date.valueOf(localDateFilm3);
        int directorIdFirst = 1, directorIdSecond = 2;
        Movie movie1 = new Movie(id1, title1, genre1, release1, directorIdFirst);
        Movie movie2 = new Movie(id2, title2, genre1, release2, directorIdFirst);
        Movie movie3 = new Movie(id3, title3, genre2, release3, directorIdSecond);

        // Добавляем фильмы в репозиторий
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);

        // Проверяем, что фильм успешно добавлен
        assertEquals("Movie{id=1, title='Побег', genre='Приключения', release=1999-01-12, directorId=1}", movie1.toString());
        assertEquals("Movie{id=2, title='Возвращение', genre='Приключения', release=2000-01-12, directorId=1}", movie2.toString());
        assertEquals("Movie{id=3, title='Скала', genre='Фантастика', release=2006-01-12, directorId=2}", movie3.toString());
    }

    @Test
    @Order(3)
    public void testRemoveMovie() {
        movieRepository.delete(movieRepository.get(3));
        assertEquals(null, movieRepository.get(3));
        assertEquals("Movie{id=2, title='Возвращение', genre='Приключения', release=2000-01-12, directorId=1}", movieRepository.get(2).toString());
        //сохранить пару новых фильмов
    }

    @Test
    @Order(4)
    void returnsMoviesByDirector() {
        List<Movie> directorMovies = movieRepository.get(directorRepository.get(1));
        // Assert
        assertEquals(2, directorMovies.size());
    }
}