package Task2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectorRepositoryImpl implements DirectorRepository {
    private Connection connection;

    public DirectorRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Director get(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Directors WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date birthDate = resultSet.getDate("birth_date");
                String country = resultSet.getString("country");

                return new Director(id, firstName, lastName, birthDate, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(Director director) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Directors (first_name, last_name, birth_date, country) VALUES (?, ?, ?, ?)");
            statement.setString(1, director.getFirstName());
            statement.setString(2, director.getLastName());
            statement.setDate(3, director.getBirthDate());
            statement.setString(4, director.getCountry());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Director director) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Directors WHERE id = ?");
            statement.setInt(1, director.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Director> get(List<String> genres) {
        List<Director> directors = new ArrayList<>();

        try {
            // Создаем SQL запрос
            String sql = "SELECT * FROM Directors d INNER JOIN Movies m ON d.id = m.director WHERE m.genre IN (";

            // Добавляем параметр для каждого жанра из списка
            for (int i = 0; i < genres.size(); i++) {
                sql += "?";
                if (i != genres.size() - 1) {
                    sql += ",";
                }
            }

            sql += ")";

            // Создаем PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Устанавливаем значения параметров
            for (int i = 0; i < genres.size(); i++) {
                statement.setString(i + 1, genres.get(i));
            }

            // Выполняем запрос и получаем результат
            ResultSet rs = statement.executeQuery();

            // Обрабатываем результат
            while (rs.next()) {
                Director director = new Director(rs);
                directors.add(director);
            }

            // Закрываем ресурсы
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directors;
    }
}