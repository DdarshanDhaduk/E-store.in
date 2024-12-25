package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Couponoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Couponoffrepo extends JpaRepository<Couponoff, Integer> {
}
