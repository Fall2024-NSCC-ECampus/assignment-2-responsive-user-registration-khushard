package com.example.userregistration.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


/**
* An Entity class that represents a user in the system.
* User info placed to 'userTable' in the database.
* */
@Entity
@Table(name="userTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    // Username must be unique, and can't be blank.
    @Column(name = "userName", unique = true, nullable = false)
    @NotBlank (message = "Username is required")
    private String userName;

    /**
    * Password is hashed using BCrypt.
    * JsonIgnore prevents the password from being included in any JSON responses.
    * */
    @JsonIgnore
    @NotBlank (message = "Password is required")
    private String password;

    // Email must be unique and valid email format.
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid Email")
    private String email;



    public User(Long id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
