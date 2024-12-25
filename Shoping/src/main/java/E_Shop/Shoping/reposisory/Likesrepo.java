package E_Shop.Shoping.reposisory;

import E_Shop.Shoping.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Likesrepo extends JpaRepository<Likes, Integer> {

//    get likeproduct by userid
    @Query("SELECT l FROM Likes l JOIN l.users u WHERE u.username = :userid")
    List<Likes> findLikesProductByUserId(@Param("userid") String userid);

    //    get likeproduct by productid
    @Query("SELECT l FROM Likes l WHERE l.product.product_id = :pid")
    List<Likes> getlikebypid(@Param("pid") String pid);

    //    get buymovies by same user_id and same p_id
    @Query("SELECT l FROM Likes l WHERE l.users.username = :uid And l.product.product_id = :pid")
    Likes getlikesbyuseridandpid(@Param("uid") String uid, @Param("pid") String pid);
}
