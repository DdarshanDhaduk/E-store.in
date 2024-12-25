package E_Shop.Shoping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Product product;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private User users;
}
