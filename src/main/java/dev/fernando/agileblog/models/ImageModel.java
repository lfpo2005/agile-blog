package dev.fernando.agileblog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Base64;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_IMAGE")
public class ImageModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID imageId;

   /* @Column(columnDefinition = "text", nullable = false)
    private String imageOriginal;*/

    @Column(columnDefinition = "text", nullable = false)
    private String imageMin;

    public ImageModel(byte[] originalImageBytes, byte[] resizedImageBytes) {
//        this.imageOriginal = Base64.getEncoder().encodeToString(originalImageBytes);
        this.imageMin = Base64.getEncoder().encodeToString(resizedImageBytes);
    }
}
