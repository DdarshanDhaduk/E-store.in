package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Buymovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Buymoviesrepo extends JpaRepository<Buymovies,Integer> {

    @Query("SELECT bm FROM Buymovies bm WHERE bm.users.username = :uid")
    List<Buymovies> getbuymoviesbyuserid(@Param("uid") String uid);

//    get buymovies by same user_id and same movie_id
    @Query("SELECT bm FROM Buymovies bm WHERE bm.users.username = :uid And bm.movies.movie_id = :mid")
     Buymovies getbuymoviesbyuseridansmid(@Param("uid") String uid, @Param("mid") String mid);
}
