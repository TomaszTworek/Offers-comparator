package pl.endproject.offerscomparator.subdomains.userRegistration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userSeq")
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    private Long user_id;
    private String login;
    private String password;
    private String email;
    private String token;
    private Boolean active;
    private Integer points;
    private LocalDate last_update;
    private String role;

    /*
    todo: relacje pomiedzy loggerem a userem
    //    @Transient
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Logger> loggers;

*/

    public User() {
    }

    public User(String login, String password, String email, String token, Boolean active, Integer points, LocalDate last_update, String role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.token = token;
        this.active = active;
        this.points = points;
        this.last_update = last_update;
        this.role = role;
    }


    public User(String login, String password, String email, String token) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.token = token;
        this.active = false;
        this.points = 0;
        this.last_update = LocalDate.now();
        this.role = "USER";
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getLast_update() {
        return last_update;
    }

    public void setLast_update(LocalDate last_update) {
        this.last_update = last_update;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(token, user.token) &&
                Objects.equals(active, user.active) &&
                Objects.equals(points, user.points) &&
                Objects.equals(last_update, user.last_update) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, login, password, email, token, active, points, last_update, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", active=" + active +
                ", points=" + points +
                ", last_update=" + last_update +
                ", role='" + role + '\'' +
                '}';
    }
}
