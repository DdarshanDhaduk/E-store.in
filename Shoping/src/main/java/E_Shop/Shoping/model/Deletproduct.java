package E_Shop.Shoping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deletproduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int delete_id;
    private String product_id;
    private String product_name;
    private int total_buyquntity;
    private int total_Addquntity;
    private String name;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] img;
}
