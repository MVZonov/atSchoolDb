package Task1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

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
}