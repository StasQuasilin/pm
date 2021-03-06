package entity.task;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "task_dependency")
public class TaskDependency extends JsonAble {
    private int id;
    private Task task;
    private Task dependency;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_dependent")
    public Task getTask() {
        return task;
    }
    public void setTask(Task dependent) {
        this.task = dependent;
    }

    @ManyToOne
    @JoinColumn(name = "_principal")
    public Task getDependency() {
        return dependency;
    }
    public void setDependency(Task principal) {
        this.dependency = principal;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(Keys.ID, id);
        json.put(Keys.DEPENDENT, task.toJson());
        json.put(Keys.PRINCIPAL, dependency.toJson());

        return json;
    }
}
