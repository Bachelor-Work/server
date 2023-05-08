package com.mosaics.mosaicsback.entity.museum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "museum_type")
@Setter
@Getter
@NoArgsConstructor
public class MuseumType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "museumType", fetch = FetchType.EAGER)
    private List<Museum> museum;

}
