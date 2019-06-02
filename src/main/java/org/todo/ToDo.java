package  org.todo;

import javax.persistence.Column;
import javax.persistence.Entity;
 
import io.quarkus.hibernate.orm.panache.PanacheEntity;
 
@Entity
public class ToDo extends PanacheEntity {
 
    @Column(length = 200, unique = true)
    public String task;
 
    @Column()
    public Boolean complete;
 
    public ToDo() {
    }
 
    public ToDo(String task, Boolean complete) {
        this.task = task;
        this.complete = complete;
    }
}
