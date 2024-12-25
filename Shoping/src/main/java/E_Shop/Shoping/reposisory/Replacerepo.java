package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Repalceorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Replacerepo extends JpaRepository<Repalceorder, Integer> {

    //    get finalorder by date descending order
    @Query("SELECT r FROM Repalceorder r ORDER BY r.localDate DESC")
    List<Repalceorder> findDatesDesc();
}
