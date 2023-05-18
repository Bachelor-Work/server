package com.mosaics.mosaicsback.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mosaics.mosaicsback.entity.museum.ManagerRequest;
import com.mosaics.mosaicsback.entity.museum.Museum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "manager_allowed")
    private Boolean managerAllowed;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Museum museum;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private ManagerRequest managerRequest;

    public void addRole(Role role) {
        this.roles.add(role);
    }
}