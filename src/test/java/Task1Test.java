import Task1.Director;
import Task1.DirectorRepository;
import Task1.DirectorRepositoryImpl;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Task1Test {
    private Connection connection;
    private DirectorRepository directorRepository;

    @BeforeAll
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE Directors (id SERIAL PRIMARY KEY, " +
                "first_name TEXT, last_name TEXT, " +
                "birth_date DATE, country TEXT)");
        directorRepository = new DirectorRepositoryImpl(connection);
    }

    @AfterAll
    public void drop() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE Directors");
        connection.close();
    }

    @DisplayName("Проверяем что в БД пусто")
    @Test
    @Order(1)
    public void getBeforeSave() {
        Director director = directorRepository.get(1);
        assertNull(director);
    }

    @DisplayName("Сохраняем в БД Василия Пупкина")
    @Test
    @Order(2)
    public void save() {
        int id = 1;
        String firstName = "Василий";
        String lastName = "Пупкин";
        LocalDate localDate = LocalDate.of(1979, 1, 12);
        java.sql.Date birthDate = java.sql.Date.valueOf(localDate);
        String country = "Russland";

        Director director = new Director(id, firstName, lastName, birthDate, country);
        directorRepository.save(director);

        Director savedDirector = directorRepository.get(id);
        assertNotNull(savedDirector);
        assertEquals(id, savedDirector.getId());
        assertEquals(firstName, savedDirector.getFirstName());
        assertEquals(lastName, savedDirector.getLastName());
        assertEquals(birthDate, savedDirector.getBirthDate());
        assertEquals(country, savedDirector.getCountry());
    }

    @DisplayName("Вычитываем из БД Василия Пупкина")
    @Test
    @Order(3)
    public void getAfterSave() {
        Director director = directorRepository.get(1);
        Director nullDirector = directorRepository.get(2);
        assertEquals("Director{id=1, firstName='Василий', lastName='Пупкин', birthDate=1979-01-12, country='Russland'}", director.toString());
        assertNull(nullDirector);
    }

    @DisplayName("Удаляем из БД Василия Пупкина")
    @Test
    @Order(4)
    public void delete(){
        directorRepository.delete(directorRepository.get(1));
    }

    @DisplayName("Проверяем что в БД пусто")
    @Test
    @Order(5)
    public void getAfterDelete() {
        Director director = directorRepository.get(1);
        assertNull(director);
    }
}