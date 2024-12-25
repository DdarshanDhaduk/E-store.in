    package E_Shop.Shoping.security;

    import E_Shop.Shoping.model.User;
    import E_Shop.Shoping.service.Userservice;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    @Service
    public class Customuserdetaliservice implements UserDetailsService {

        @Autowired
        @Lazy
        private Userservice userservice;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user= userservice.getuserbyid(username);
            System.out.println("userDetails called");
            System.out.println(user.getUsername());
            if(user==null){
                throw new UsernameNotFoundException("Could not found user !!");
            }
            return new Customuserdetails(user);
        }
    }
