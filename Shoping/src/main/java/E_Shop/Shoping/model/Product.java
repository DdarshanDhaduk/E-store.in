package E_Shop.Shoping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String product_id;

    @NotBlank(message = "product_name cannot be empty")
    private String product_name;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "brand cannot be empty")
    private String brand;

    @NotNull(message = "price cannot be empty")
    @Positive(message = "price value must be greater than 0")
    private Integer price;

    @NotNull(message = "off cannot be empty")
    @Positive(message = "off value must be greater than 0")
    private Integer off;

    @NotNull(message = "localoff cannot be empty")
    @Positive(message = "localoff value must be greater than 0")
    private Integer localoff;

    @NotBlank(message = "product_details cannot be empty")
    private String product_details;

    @NotBlank(message = "warranty cannot be empty")
    private String warranty;

    @NotNull(message = "returnday cannot be empty")
    @Positive(message = "returnday value must be greater than 0")
    private Integer returnday;

    @NotNull(message = "coupon cannot be empty")
    @Positive(message = "coupon value must be greater than 0")
    private Integer coupon;

    @NotNull(message = "coupon_price cannot be empty")
    @Positive(message = "coupon_price value must be greater than 0")
    private Integer coupon_price;

    @NotNull(message = "coupon_off cannot be empty")
    @Positive(message = "coupon_off value must be greater than 0")
    private Integer coupon_off;

    @NotNull(message = "gst cannot be empty")
    @Positive(message = "gst value must be greater than 0")
    private Integer gst;

    @NotNull(message = "free_delivery cannot be empty")
    @Positive(message = "free_delivery value must be greater than 0")
    private Integer free_delivery;

    @NotBlank(message = "Director cannot be empty")
    private String faster_delivery;

    private int totalquntity;

    private int total_buyproduct;

    private int total_star;

    private int total_star_count;

    @Lob
    @Column(name = "productimg1", columnDefinition = "LONGBLOB")
    private byte[] productimg1;

    @Lob
    @Column(name = "productimg2", columnDefinition = "LONGBLOB")
    private byte[] productimg2;

    @Lob
    @Column(name = "headoffimg", columnDefinition = "LONGBLOB")
    private byte[] headoffimg;

    @Lob
    @Column(name = "offerimg", columnDefinition = "LONGBLOB")
    private byte[] offerimg;

    @Size(min = 1, message = "fd_1 cannot be empty")
    private String fd_1;

    @Size(min = 1, message = "fd_2 cannot be empty")
    private String fd_2;

    @Size(min = 1, message = "fd_3 cannot be empty")
    private String fd_3;

    @Size(min = 1, message = "fd_4 cannot be empty")
    private String fd_4;

    @Size(min = 1,  message = "d_1 cannot be empty")
    private String d_1;

    @Size(min = 1, message = "d_2 cannot be empty")
    private String d_2;

    @Size(min = 1, message = "d_3 cannot be empty")
    private String d_3;

    @Size(min = 1, message = "d_4 cannot be empty")
    private String d_4;

    @Size(min = 1, message = "d_5 cannot be empty")
    private String d_5;

    @Size(min = 1, message = "d_6 cannot be empty")
    private String d_6;

    @Size(min = 1, message = "d_7 cannot be empty")
    private String d_7;

    @Size(min = 1, message = "d_8 cannot be empty")
    private String d_8;

    @Size(min = 1, message = "d_9 cannot be empty")
    private String d_9;

    @Size(min = 1, message = "d_10 cannot be empty")
    private String d_10;

    @Size(min = 1, message = "d_11 cannot be empty")
    private String d_11;

    @Size(min = 1, message = "d_12 cannot be empty")
    private String d_12;

    @Size(min = 1, message = "d_13 cannot be empty")
    private String d_13;

    @Size(min = 1, message = "d_14 cannot be empty")
    private String d_14;

    @Size(min = 1, message = "d_15 cannot be empty")
    private String d_15;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endtime;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "product")
    @JsonIgnore
    private List<Productcolor> productcolors;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH}, mappedBy = "product")
    @JsonIgnore
    private List<Ratingstar> ratingstars;

    @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JsonIgnore
    private Productmenu productmenu;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "product")
    @JsonIgnore
    private List<Likes> likes;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "product")
    @JsonIgnore
    private List<Orders> orders;

}
