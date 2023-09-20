package send.toyou.friendmicroapi.friend;

import reactor.core.publisher.Mono;

public interface FriendService {
    Mono<Friend> addNewFriend(Friend friend);
    Mono<Friend> deleteFriend(String friend);
    Mono<Friend> getFriendByName(String name);

}
