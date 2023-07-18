package Task2;

import java.util.List;

public interface DirectorRepository {
    Director get(int id);
    void save(Director director);
    void delete(Director director);
    List<Director> get(List<String> genres);

}
