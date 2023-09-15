package send.toyou.friendmicroapi.friend;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;



@Setter
@Getter
@Table(name = "friend")
public class Friend {
    /*@Id
    @SequenceGenerator(
            name = "friend_sequence",
            sequenceName = "friend_sequence",

            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "friend_sequence"
    )*/
    private String name;
    private final LocalDate creation = LocalDate.now();

    public Friend(String name) {
        this.name = name;
    }

    public Friend(){}

    public Friend(Long id1, Long id2){
        this.name = id1 + "->" + id2;
    }
    @Override
    public String toString() {
        return "Friend{" +
                ", name='" + name + '\'' +
                ", creation=" + creation +
                '}';
    }
}
