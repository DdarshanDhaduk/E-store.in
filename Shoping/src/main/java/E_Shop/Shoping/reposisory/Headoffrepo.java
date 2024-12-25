package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Headoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Headoffrepo extends JpaRepository<Headoff, Integer> {
    @Query(value = "SELECT * FROM headoff WHERE endtime > NOW()", nativeQuery = true)
    Headoff liveHeadOffer();
}
