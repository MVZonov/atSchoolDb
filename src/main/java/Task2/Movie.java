package Task2;


import java.sql.Date;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private Date release;
    private int directorId;

    public Movie(int id, String title, String genre, Date release, int directorId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.release = release;
        this.directorId = directorId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Date getRelease() {
        return release;
    }

    public int getDirectorId() {
        return directorId;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", release=" + release +
                ", directorId=" + directorId +
                '}';
    }
}