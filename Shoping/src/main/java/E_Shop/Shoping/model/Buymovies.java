package E_Shop.Shoping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buymovies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mid;
    private int point;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Movies movies;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private User users;
}
