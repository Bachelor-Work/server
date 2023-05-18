package com.mosaics.mosaicsback.entity.museum;

import com.mosaics.mosaicsback.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manager_request")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean status;

    @NonNull
    @Column(name = "description")
    private String description;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NonNull
    private User user;

}
