package E_Shop.Shoping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ratingstar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String gmail;
    private LocalDateTime date;
    private String description;
    private int star_no;
//    @Lob
//    @Column(name = "img", columnDefinition = "LONGBLOB")
//    private byte[] img;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Product product;
}
