package todo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "priorities")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int position;

    public Priority() {
    }

    public Priority(String name, int position) {
        this.name = name;
        this.position = position;
    }
}
