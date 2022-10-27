package todo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "tasks")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");
    private boolean done = false;
    private int client;

    public Item() {
    }

    public Item(String description) {
        this.description = description;
    }

    public Item(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Item(String description, int client) {
        this.description = description;
        this.client = client;
    }

    public Item(int id, String description, int client) {
        this.id = id;
        this.description = description;
        this.client = client;
    }
}
