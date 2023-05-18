package com.mosaics.mosaicsback.entity.museum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "museum_img")
@Setter
@Getter
@NoArgsConstructor
public class MuseumImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "filename")
    private String fileName;

    @NonNull
    @Column(name = "type")
    private String typeOfFile;

    @Lob
    @Column(name = "file_content", columnDefinition = "oid")
    private byte @NonNull [] fileContent;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "museum_id", referencedColumnName = "id")
    private Museum museum;

    public MuseumImg(String originalFilename, String contentType, byte[] bytes, Museum museum) {
        this.fileName = originalFilename;
        this.typeOfFile = contentType;
        this.fileContent = bytes.clone();
        this.setMuseum(museum);
    }
}
