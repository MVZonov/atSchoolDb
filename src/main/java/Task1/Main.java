package Task1;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            // создание подключения к БД
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

            // создание таблицы Directors
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE Directors (id SERIAL PRIMARY KEY, " +
                    "first_name TEXT, last_name TEXT, " +
                    "birth_date DATE, country TEXT)");

            // добавление 5 записей в таблицу Directors
            statement.executeUpdate("INSERT INTO Directors (first_name, last_name, birth_date, country) " +
                    "VALUES ('Иван', 'Иванов', '1980-01-01', 'Россия'), " +
                    "('Петр', 'Петров', '1985-05-05', 'Россия'), " +
                    "('Анна', 'Сидорова', '1990-10-10', 'Россия'), " +
                    "('Мария', 'Кузнецова', '1982-02-02', 'Россия'), " +
                    "('Дмитрий', 'Смирнов', '1987-07-07', 'Россия')");

            // распечатка и вывод на экран таблицы Directors
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Directors");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthDate = resultSet.getDate("birth_date");
                String country = resultSet.getString("country");

                System.out.println(id + "\t" + firstName + "\t" + lastName + "\t" + birthDate + "\t" + country);
            }

            // закрытие подключения
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
