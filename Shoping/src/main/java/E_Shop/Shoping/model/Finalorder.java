package E_Shop.Shoping.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Finalorder {

    @Id
    private int bill_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MMM-dd")
    private LocalDate date;
    //    LocalDate.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MMM-dd")
    private LocalDate deliverydate;
    private String delivverytype;
    private int delivverycharge;
    private String paymenttype;
    private float estroepoint;
    private String otp;
    private int couponoff;
    private boolean orderok;
    private boolean couponcode;

    //   today.plusDays(2);

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Finalorderlist> finalorderlists ;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private User user;
}
