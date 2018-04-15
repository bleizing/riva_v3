package bleizing.riva.model;

/**
 * Created by Bleizing on 3/13/2018.
 */

public class User {
    private String username;

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
