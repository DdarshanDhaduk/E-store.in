package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Productmenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Productmenurepo extends JpaRepository<Productmenu,Integer> {
}
