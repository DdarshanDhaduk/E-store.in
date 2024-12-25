package E_Shop.Shoping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finalorderlist {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int product_id;
    private String product_name;
    private String product_id1;
    private int price;
    private String colorcode;
    private int quntity;
    private boolean replaceproduct;
    private LocalDate replaceendtime;

    @Lob
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Finalorder finalorders;
}
