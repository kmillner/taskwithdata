import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
	private String description;
	private int id;

	public int getId() {
	return id;
	}

	public String getDescription() {
	return description;
	}

	public Task(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object otherTask){
		if(!(otherTask instanceof Task)) {
			return false;
		}
		else{
			Task newTask = (Task) otherTask;
			return this.getDescription().equals(newTask.getDescription()) &&
			this.getId() ==newTask.getId();

		}
	}

	public static List<Task> all() {
    String sql = "SELECT id, description FROM Tasks";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save (){
  	try (Connection con = DB.sql2o.open()) {
  		String sql = "INSERT INTO tasks (description) VALUES (:description)";
  		this.id = (int)con.createQuery(sql, true)
  		.addParameter("description", this.description)
  		.executeUpdate()
  		.getKey();
 	 	}
 	 }

 	 public static Task find (int id){
 	 	try (Connection con = DB.sql2o.open()){
	 	 	String sql = "SELECT * FROM tasks WHERE id = :id";
	 	 	Task task = con.createQuery(sql)
	 	 	.addParameter("id", id)
	 	 	.executeAndFetchFirst(Task.class);
	 	 	return task;
   		}
 	 	}
	} //close main