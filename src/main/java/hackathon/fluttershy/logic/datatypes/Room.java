package hackathon.fluttershy.logic.datatypes;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


@Getter
@AllArgsConstructor
public class Room implements Comparable{
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private Building building;

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Room r) {
            return Long.compare(r.id, id);
        }
        throw new RuntimeException("Cannot compare Room with " + o.getClass().descriptorString());
    }
}
