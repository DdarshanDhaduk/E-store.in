package E_Shop.Shoping.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repalceorder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate localDate;

    private boolean check1;

    @OneToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Finalorderlist finalorderlist;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private User users;
}
