package E_Shop.Shoping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Headoff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int offid;
    @Lob
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;
// Today's offer is for Diwali
    @NotBlank(message = "name cannot be empty")
    @Size(min = 4, max = 20, message = "name must be between 4 and 20 characters")
    private String name;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endtime;

    @NotNull(message = "Coupon value cannot be null")
    @Positive(message = "Coupon value must be greater than 0")
    private Integer happycoupon;
}
