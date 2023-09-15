package send.toyou.friendmicroapi.friend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class FriendService {//Esto tendria que ser la implementacion de la interface

    private final FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository){
        this.friendRepository = friendRepository;
    }
    public Mono<Friend> addNewFriend(Friend friend) {
        return friendRepository.save(friend);
    }

    public Mono<Friend> deleteFriend(String friend){

        Optional<Friend> friendOptional = friendRepository.findById(friend).blockOptional();

        if (friendOptional.isEmpty()){
            log.info("ERROR: Friend -> {} does not exists", friend);
            return Mono.empty();
        }
        else{
            friendRepository.deleteById(friend);
            return Mono.just(friendOptional.get());
        }
    }

    public Mono<Friend> getFriendByName(String name){
        return this.friendRepository.findFriendByName(name)
                .doOnNext(friend -> log.info("Find friend with name: {}",name)).flatMap(friend -> {
                  if(friend!=null){
                        return Mono.just(friend);
                  }
                    log.info("Friend not found: {}",name);
                    return Mono.empty();
                });
    }

}
