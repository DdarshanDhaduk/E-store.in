package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Userrepo extends JpaRepository<User,String> {

    //    flase or true check in user id use(false) and not use(true)
    @Query("SELECT CASE WHEN COUNT(u) = 0 THEN true ELSE false END FROM User u WHERE u.username = ?1")
    boolean existsuserByIdCustom(String id);

}
