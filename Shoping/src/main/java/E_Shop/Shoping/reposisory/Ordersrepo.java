package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Ordersrepo extends JpaRepository<Orders,Integer> {

    //    get ordercart by userid
    @Query("SELECT o FROM Orders o WHERE o.users.username = :userid")
    List<Orders> findOrderlistByUserid(@Param("userid") String userid);

    @Modifying
    @Transactional
    @Query("DELETE FROM Orders o WHERE o.product.product_id = :pid AND o.color = :color")
    void deleteallorder(@Param("pid") String pid, @Param("color") String color);

    @Modifying
    @Transactional
    @Query("DELETE FROM Orders o WHERE o.users.username = :gmailid")
    void deleteallorderbyuserid(@Param("gmailid") String gmailid);

    //    get order by productid
    @Query("SELECT o FROM Orders o WHERE o.product.product_id = :pid")
    List<Orders> getorderbypid(@Param("pid") String pid);

}
