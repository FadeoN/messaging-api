package messaging.models.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    //TODO: Password Encoding
    @Column(nullable = false)
    private String password;

    public User() {
    }

    @Builder(toBuilder = true)
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
