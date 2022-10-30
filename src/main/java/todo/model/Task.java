package todo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private boolean done = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {
    }
}
