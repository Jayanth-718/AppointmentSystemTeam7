package com.team7.appointmentsystem.models;

public class LoggedInUser {
    private String name;
    private String email;
    private Long id;
    private String role;

    public LoggedInUser(String name, String email, Long id, String role) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
