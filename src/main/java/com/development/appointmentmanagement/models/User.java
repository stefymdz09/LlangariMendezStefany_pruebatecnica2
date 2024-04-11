
package com.development.appointmentmanagement.models;


import java.io.Serializable;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;//NickName
    private String password;
    /*
    muchos usuarios pueden estar asociados al mismo rol, pero cada usuario solo puede tener un único rol.
     */
    @ManyToOne
    @JoinColumn(name = "idRole")
    private Role role;

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(Long idUser, String username, String password, Role role) {
        this.id = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User( String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setIdUser(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
