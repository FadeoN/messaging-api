package messaging.models.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;

    private String username;
    private String password;

    public User() {
    }

    @Builder
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
