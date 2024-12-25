package E_Shop.Shoping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int order_id;
    private String color;
    private int quntity;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JsonIgnore
    private User users;

}
