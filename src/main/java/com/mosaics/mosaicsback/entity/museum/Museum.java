package com.mosaics.mosaicsback.entity.museum;

import com.mosaics.mosaicsback.entity.Model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "museum")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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

    @OneToMany(mappedBy = "museum", fetch = FetchType.EAGER)
    private List<Model> model;

    @OneToOne(mappedBy = "museum", fetch = FetchType.EAGER)
    private MuseumImg museumImg;

    @ManyToOne()
    @JoinColumn(name = "museum_type_id", referencedColumnName = "id")
    private MuseumType museumType;

}