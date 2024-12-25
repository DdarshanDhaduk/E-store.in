package E_Shop.Shoping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Productcolor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int color_id;
//    private String imgUrl1;
//    private String imgUrl2;
//    private String imgUrl3;

    private String vidoUrl1;

    @NotNull(message = "quntity cannot be empty")
//    @Positive(message = "quntity value must be greater than 0")
    @Min(value = 1, message = "quantity must be a positive integer")
    private Integer quntity;

    private int totalquntity;

    private String colorcode;

    @Lob
    @Column(name = "img1", columnDefinition = "LONGBLOB")
    private byte[] img1;
    @Lob
    @Column(name = "img2", columnDefinition = "LONGBLOB")
    private byte[] img2;
    @Lob
    @Column(name = "img3", columnDefinition = "LONGBLOB")
    private byte[] img3;
    @Lob
    @Column(name = "imgv1", columnDefinition = "LONGBLOB")
    private byte[] imgv1;
    @Lob
    @Column(name = "video1", columnDefinition = "LONGBLOB")
    private byte[] video1;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Product product;



}
