package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Deletproduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Deletproductrepo extends JpaRepository<Deletproduct, Integer> {
    //    flase or true check in product id use(false) and not use(true)
    @Query("SELECT CASE WHEN COUNT(p) = 0 THEN true ELSE false END FROM Deletproduct p WHERE p.product_id = ?1")
    boolean deletecheckidtruefalse(String id);
}
