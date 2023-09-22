package send.toyou.friendmicroapi.friend;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table("friend")
@AllArgsConstructor
public class Friend {
    @Column("name")
    private String name;

    @Column("creation")
    private LocalDate creation;


    public Friend(Long id1, Long id2) {
        this.name = id1 + "->" + id2;
    }

    public Friend(String name) {
        this.name = name;
        this.creation = LocalDate.now();
    }
}
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
