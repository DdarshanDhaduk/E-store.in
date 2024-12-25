package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Productrepo extends JpaRepository<Product,String> {

    //***************adminservice*********************//
//    live offer get
    @Query(value = "SELECT * FROM product WHERE endtime > NOW()", nativeQuery = true)
    List<Product> liveOffer();

    //    maximum off get 8 product
    @Query(value = "SELECT * FROM Product ORDER BY localoff DESC LIMIT 8", nativeQuery = true)
    List<Product> findTopEightDiscountedProducts();

//    flase or true check in product id use(false) and not use(true)
    @Query("SELECT CASE WHEN COUNT(p) = 0 THEN true ELSE false END FROM Product p WHERE p.product_id = ?1")
    boolean existsByIdCustom(String id);

//***************homeservice*********************//
    //Get Product off and localoff not same and endtime < now_time;
    @Query("SELECT p FROM Product p WHERE p.off != p.localoff AND p.endtime < CURRENT_TIMESTAMP")
    List<Product> findProductsWithDifferentOffAndLocalOff();

    //get product by productmenu id
    @Query("SELECT p FROM Product p WHERE p.productmenu.menu_id = :menuId")
    List<Product> getProductbymenuId(@Param("menuId") int menuId);

    //get product by same name and not same product id
    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.product_id != :id")
    List<Product> findreleatedproductlistByname(@Param("name") String name, @Param("id") String id);

    //    get search base product bt productname and name

//    @Query("SELECT p FROM Product p WHERE p.product_name LIKE %:keyword%")
    @Query("SELECT p FROM Product p WHERE p.product_name LIKE %:keyword% OR p.productmenu.name LIKE %:keyword%")
    List<Product> findbynameContaining(@Param("keyword") String keyword);
}
