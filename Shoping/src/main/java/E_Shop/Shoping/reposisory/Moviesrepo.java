package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Moviesrepo extends JpaRepository<Movies, String> {
    //***************homeservice*********************//

    //    maximum off get 4 Movies
    @Query(value = "SELECT * FROM Movies ORDER BY coupon_price ASC LIMIT 4", nativeQuery = true)
    List<Movies> findTopFourtCouponPricedMovies();

    //get product by same name and not same product id
    @Query("SELECT m FROM Movies m WHERE m.movie_id != :id")
    List<Movies> findmoviesandnotsameid(@Param("id") String id);

}
