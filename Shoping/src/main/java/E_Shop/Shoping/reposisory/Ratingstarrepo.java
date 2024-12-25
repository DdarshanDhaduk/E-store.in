package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Ratingstar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Ratingstarrepo extends JpaRepository<Ratingstar,Integer> {

    //    get ratingstar by productid
    @Query("SELECT r FROM Ratingstar r WHERE r.product.product_id = :id")
    List<Ratingstar> findratingstarbyproductid(@Param("id") String id);

//    check in this user give review this product
    @Query("SELECT r FROM Ratingstar r WHERE r.product.product_id = :id AND r.gmail = :userid")
    Ratingstar findusergivereview(@Param("id") String id,@Param("userid") String userid);

    //    get ratingstar by productid
    @Query("SELECT r FROM Ratingstar r WHERE r.product.product_id = :pid")
    List<Ratingstar> getratingstarbypid(@Param("pid") String pid);

}
