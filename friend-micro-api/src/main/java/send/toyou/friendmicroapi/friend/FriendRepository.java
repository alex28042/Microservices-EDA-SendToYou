package send.toyou.friendmicroapi.friend;


import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;


import reactor.core.publisher.Mono;

@Repository("customFriendRepository")
public interface FriendRepository extends R2dbcRepository<Friend, String>{
    @Query("SELECT f FROM Friend f WHERE f.name = $1")
    Mono<Friend> findFriendByName(String name);
}
