package com.messaging.models.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 768193107575285028L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;
}