package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Productcolor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Productcolorrepo extends JpaRepository<Productcolor,Integer> {

    //***************adminservice*********************//

    @Query(value = "SELECT * FROM Productcolor WHERE quntity < 10", nativeQuery = true)
    List<Productcolor> findLowStockProducts();

//    get color by product_id
    @Query("SELECT pc FROM Productcolor pc WHERE pc.product.product_id = :productId")
    List<Productcolor> findByProductId(@Param("productId") String productId);

//    get color by product_id and color
    @Query("SELECT pc FROM Productcolor pc WHERE pc.product.product_id = :pid AND pc.colorcode = :color")
    Productcolor findBypidandcolor(String color, String pid);

//    jyare method thi data aave tyre example (int quantity) query(?1)
//    @Query(value = "SELECT * FROM productcolor WHERE quntity < ?1", nativeQuery = true)
//    List<Productcolor> findLowStockProducts(int quntity);
}
