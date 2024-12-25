package E_Shop.Shoping.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class User {
    @Id
    @NotNull(message = "Username cannot be null.")
    private String username;
//    @Getter(AccessLevel.NONE)

    private String password;

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, message = "Name cannot be empty.")
    private String name;

    private  String role;

    @NotNull(message = "Phone number cannot be null.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phone_no;

    private Integer home_no;

    private String street_name;

    private String area;

    private String city;

    private String district;

    private String state;

    private String pincode;

    private int estore_point;
    private boolean enable;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Likes> likes;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Orders> orders;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Buymovies> buymovies;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH}, mappedBy = "user")
    private List<Finalorder> finalorders;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private List<Repalceorder> repalceorders;

//    <---aa code userdetails na interface no se login page na data ne check karava mate-->

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
////      single roles ohy tyare aa sale
//        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.getRole());
//        return List.of(simpleGrantedAuthority);
//
////        multi roles hoy atle ke List<sString> ma (@Elementcollection)  thi jo aave  to aa support kare
////        return role.stream().map(role -> new SimpleGrantedAuthority(this.getRole())).collect(Collections.Tolist());
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
