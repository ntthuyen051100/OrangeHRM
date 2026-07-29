package models;

public class UserInfo {
    private String username;
    private String role;
    private String name;
    private String status;


    public UserInfo(String username, String role, String name, String status) {
        this.username = username;
        this.role = role;
        this.name = name;
        this.status = status;
    }
    public String getUsername() {
        return username;
    }
    public String getRole() {
        return role;
    }
    public String getName() {
        return name;
    }
    public String getStatus() {
        return status;
    }
}
