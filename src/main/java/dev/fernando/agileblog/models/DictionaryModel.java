package dev.fernando.agileblog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_DICTIONARY")
public class DictionaryModel extends RepresentationModel<DictionaryModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID dictionaryId;
    @Column(nullable = false, length = 120)
    private String word;
    @Column(nullable = false, length = 250)
    private String link;
    @Column(nullable = false, length = 1000)
    private String reference;
    private int searchCount;

}
