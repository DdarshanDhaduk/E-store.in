package E_Shop.Shoping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movies {

    @Id
    private String movie_id;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "Director cannot be empty")
    private String director;

    @NotBlank(message = "genre cannot be empty")
    private String genre;

    @NotBlank(message = "Starring cannot be empty")
    private String starring;

    @NotBlank(message = "ContentDescriptor cannot be empty")
    private String content_descriptor;

    @NotBlank(message = "Releaseyear cannot be empty")
    @Pattern(regexp = "^[0-9]{4}$", message = "Invalid releseyear format. Must be a 4-digit year")
    private String releaseyear;

    @NotBlank(message = "Releaseyear cannot be empty")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$", message = "Invalid durating format. Expected HH:mm:ss")
    private String durating;

    @NotBlank(message = "Language cannot be empty")
    private String language;

    @NotBlank(message = "Mtype cannot be empty")
    private String mtype;

    @NotNull(message = "CouponPrice cannot be empty")
    @Positive(message = "CouponPrice value must be greater than 0")
    private Integer coupon_price;

    @Lob
    @Column(name = "img1", columnDefinition = "LONGBLOB")
    private byte[] img1;
    @Lob
    @Column(name = "img2", columnDefinition = "LONGBLOB")
    private byte[] img2;
    @Lob
    @Column(name = "img3", columnDefinition = "LONGBLOB")
    private byte[] img3;
    @Lob
    @Column(name = "tailer", columnDefinition = "LONGBLOB")
    private byte[] tailer;

    private String movieurl;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Buymovies> buymovies ;
}
