package essentials.elements;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private final String login;
    private final String password;
    private int id;

    public UserInfo(String login, String password, int id) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
