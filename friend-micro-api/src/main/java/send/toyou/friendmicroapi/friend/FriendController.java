package send.toyou.friendmicroapi.friend;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/friend")
@Slf4j
public class FriendController {
    @Autowired
    private FriendServiceImpl friendServiceImpl;

    @PostMapping
    public Mono<ResponseEntity<Friend>> saveFriend(@RequestBody Friend friend){
        return this.friendServiceImpl.addNewFriend(friend)
                .doOnNext(friendCreated -> log.info("Friend created: {}", friendCreated))
                .map(friendCreated -> new ResponseEntity<>(friendCreated, friendCreated.equals(friend) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST));
    }
    @GetMapping("/{name}")
    public Mono<ResponseEntity<Friend>> getPackage(@RequestParam String name){
        return this.friendServiceImpl.getFriendByName(name).doOnNext(friend -> log.info("Friend found out: {}", friend))
                .map(friend -> friend != null
                        ? new ResponseEntity<>(friend, HttpStatus.FOUND)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }
    @DeleteMapping("/{name}")
    public Mono<ResponseEntity<Friend>> deleteFriend(@RequestParam String name){
        return this.friendServiceImpl.deleteFriend(name)
                .map(friendDeleted -> {
                    if (friendDeleted!=null){
                        log.info("Friend deleted: {}", friendDeleted);
                        return ResponseEntity.ok(friendDeleted);
                    }else {
                        log.info("Friend not found: {}", name);
                        return ResponseEntity.notFound().build();
                    }
                });
    }
}
