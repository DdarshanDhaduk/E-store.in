package E_Shop.Shoping.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

    @NotNull(message = "home_no cannot be null")
    private Integer home_no;

    @NotNull(message = "street_name cannot be null.")
    private String street_name;

    @NotNull(message = "area cannot be null.")
    private String area;

    @NotNull(message = "city cannot be null.")
    private String city;

    @NotNull(message = "district cannot be null.")
    private String district;

    @NotNull(message = "state cannot be null.")
    private String state;

    @NotNull(message = "PinCode number cannot be null.")
    @Pattern(regexp = "^\\d{6}$", message = "PinCode number must be exactly 6 digits.")
    private String pincode;

}
