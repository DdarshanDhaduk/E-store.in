package E_Shop.Shoping.security;

import E_Shop.Shoping.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Customuserdetails implements UserDetails {
    private User user;

    public Customuserdetails(User user){
        this.user=user;
        System.out.println(getUsername());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
//      single roles ohy tyare aa sale
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
                return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
