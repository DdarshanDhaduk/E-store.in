package E_Shop.Shoping.security;//package E_Shop.Shoping.security;
//

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Myconfig {

    @Autowired
    @Lazy
    private Customuserdetaliservice customuserdetaliservice;


    @Bean
    public PasswordEncoder getpasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(customuserdetaliservice);
        daoAuthenticationProvider.setPasswordEncoder(getpasswordEncoder());
        return  daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/adminnormal/**").hasRole("ADMINNORMAL")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    // Redirect based on role
                    if (authentication.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/admin/menu"); // Redirect to admin menu
                    }
                     else if(authentication.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"))){
                        response.sendRedirect("/home"); // Redirect to user home
                    }
                     else {
                         response.sendRedirect("/adminnormal/deliveryboy");
                    }
                })
                .failureUrl("/login?error=true").permitAll();
//                .and().logout();
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout=true") // Redirect after logout
//                .permitAll(); // Allow logout for everyone
                httpSecurity.authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() throws Exception {
//        UserDetails user = User.withUsername("user@gmail.com")
//                .password(getpasswordEncoder().encode("password123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin@gmail.com")
//                .password(getpasswordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }


//       .and().formLogin(form ->
//                        form.loginPage("/login")
//                            .loginProcessingUrl("/do_login")
////                                .loginProcessingUrl() in this you can specify post URLin which data will be sent and used int html action
//                                .defaultSuccessUrl("/user/create")
//                            .failureForwardUrl("/login?error=true")
//                )
//                .logout(logout ->
//                        logout.logoutUrl("/logout")
//                              .logoutSuccessUrl("/login?logout=true")
//                );

}