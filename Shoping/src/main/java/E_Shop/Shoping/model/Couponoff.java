package E_Shop.Shoping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Couponoff {
    @Id
    private int id;

    @Positive(message = "CouponOff value must be greater than 0")
    private Integer coupon_off;

    @NotNull(message = "End time cannot be null")
    private LocalDate date;

    @NotBlank(message = "Language cannot be empty")
    @Size(min = 1, message = "name must be between 4 and 20 characters")
    private String code;
}
