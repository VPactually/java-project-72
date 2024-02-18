package hexlet.code.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    private int id;
    private String name;
    private Timestamp created_at;

    public Url(String name, Timestamp created_at) {
        this.name = name;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return name;
    }
}
