package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Finalorderlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Finalorderlistrepo extends JpaRepository<Finalorderlist, Integer> {

        //    get orderproduct by billid
          @Query("SELECT fol FROM Finalorderlist fol WHERE fol.finalorders.bill_id = :billid")
          List<Finalorderlist> findFinalOrderlistByBillId(@Param("billid") int billid);

    //    check in this user buy this product
    @Query("SELECT fol FROM Finalorderlist fol WHERE fol.finalorders.user.username = :uid AND fol.product_id1 = :pid")
    List<Finalorderlist>  checkinuserbuyproduct(@Param("uid") String uid,@Param("pid") String pid);
}
