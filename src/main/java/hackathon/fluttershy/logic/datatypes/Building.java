package hackathon.fluttershy.logic.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class Building implements Comparable {

    private long id;
    private String name;
    private String description;
    private String url;
    private int openingHour;
    private int closingHour;

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Building b) {
            return Long.compare(b.id, id);
        }
        throw new RuntimeException("Cannot compare Room with " + o.getClass().descriptorString());
    }
}
