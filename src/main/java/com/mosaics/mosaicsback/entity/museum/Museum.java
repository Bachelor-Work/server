package com.mosaics.mosaicsback.entity.museum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mosaics.mosaicsback.entity.Model;
import com.mosaics.mosaicsback.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "museum")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Museum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "museum_name")
    private String museumName;

    @NonNull
    @Column(name = "museum_description")
    private String museumDescription;

    @Column(name = "instagram_url")
    private String instagramURL;

    @Column(name = "twitter_url")
    private String twitterURL;

    @OneToMany(mappedBy = "museum", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Model> model;

    @OneToOne(mappedBy = "museum", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private MuseumImg museumImg;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "museum_type_id", referencedColumnName = "id")
    private MuseumType museumType;

    @OneToOne(cascade=CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User user;

}