package send.toyou.friendmicroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FriendMicroApiApplication {
    List<User> comunidad = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(FriendMicroApiApplication.class, args);
    }
    public User getUser(String username) {
        for (User user : comunidad) {
            if (user.getUsrName().equals(username)) return user;
        }
        return null; // Return null if user not found
    }
    public boolean addFriend(String solicitante, String amigo) {
        User usr = getUser(solicitante);
        boolean error = esAmigo(solicitante, amigo) || (getUser(amigo).getUsrName() == null);// Check if the users are already friends or if the requested friend doesn't exist
        if (!error) {
            usr.setFriends(amigo);
            comunidad.remove(usr);
            comunidad.add(usr);
            return true;
        } else return false;
    }
    public boolean esAmigo(String solicitante, String solicitado) {
        return getUser(solicitante).isFriend(solicitado);
    }
    public boolean removeFriend(String solicitante, String amigo) {
        User usr = getUser(solicitante);

        if (usr != null && usr.isFriend(amigo)) { // Verifies that the usr exist and they are friends
            usr.removeFriend(amigo);
            comunidad.remove(usr);
            comunidad.add(usr);
            return true;
        } else {
            return false;
        }
    }

}
