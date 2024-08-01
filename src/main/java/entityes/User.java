package entityes;

import jakarta.persistence.*;

import java.net.ProtocolFamily;
import java.util.Collection;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  // id de referência para acesso ao DB
    private String username;
    private String password;

     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable(name = "user_roles",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))

     private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Object> getRoles() {
        return null; //attention
    }

    public ProtocolFamily getName() {
        return null;   // attention
    }


    @Entity
    public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private ERole name;


    }

    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_MODERATOR
    }

}
