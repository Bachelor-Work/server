package com.mosaics.mosaicsback.entity;

import com.mosaics.mosaicsback.entity.museum.Museum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "model")
@Getter
@Setter
@NoArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "file_name")
    private String fileName;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "type")
    private String typeOfFile;

    @Lob
    @Column(columnDefinition = "oid", name = "file_content")
    private byte @NonNull [] fileContent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "museum_id", nullable = false, referencedColumnName = "id")
    private Museum museum;

    public Model(String fileName, String typeOfFile, byte[] fileContent, Museum museum, String description) {
        this.fileName = fileName;
        this.typeOfFile = typeOfFile;
        this.fileContent = fileContent.clone();
        this.description = description;
        this.setMuseum(museum);
    }
}