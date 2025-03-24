package hexlet.code.model;

import java.sql.Timestamp;

public class Url {

    private Long id;
    private String name;
    private Timestamp created_at;

    public Url(Long id, String name, Timestamp created_at) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
    }
}
