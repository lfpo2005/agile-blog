package dev.fernando.agileblog.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_IMG_POST")
public class ImageBodyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID imgId;
    @Column(columnDefinition = "TEXT")
    private String img;
    @Column(length = 100)
    private String alt;
}
