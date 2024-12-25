package E_Shop.Shoping.reposisory;


import E_Shop.Shoping.model.Finalorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalorderRepo extends JpaRepository<Finalorder,Integer> {

//    get finalorder by user id
@Query("SELECT f FROM Finalorder f WHERE f.user.username = :userId")
List<Finalorder> findFinalOrderByUserId(@Param("userId") String userId);

    //    flase or true check in finalorder id use(false) and not use(true)
    @Query("SELECT CASE WHEN COUNT(f) = 0 THEN true ELSE false END FROM Finalorder f WHERE f.bill_id = ?1")
    boolean existsByfinalorderIdCustom(int id);

//    get finalorder by date descending order
    @Query("SELECT f FROM Finalorder f ORDER BY f.date DESC")
    List<Finalorder> findDatesDesc();

//    get finalorder for search
@Query("SELECT f FROM Finalorder f WHERE CAST(f.bill_id AS string) LIKE %:keyword%")
List<Finalorder> findByFinalOrderContaining(@Param("keyword") String keyword);

}
