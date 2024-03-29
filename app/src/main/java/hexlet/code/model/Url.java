package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class Url {
    private int id;
    private String name;
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
