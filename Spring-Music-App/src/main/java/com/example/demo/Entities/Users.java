package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "userType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Artist.class, name = "ARTIST"),
        @JsonSubTypes.Type(value = Client.class, name = "CLIENT")
})
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Can not have empty strings")
    private String username;

    private String password;

    @JsonIgnore
    private UserType userType;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
