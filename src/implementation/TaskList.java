package implementation;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TaskList {
	private Set<String> approvedUsers;
	private Map<String, Boolean> tasks;

	public TaskList(String username) {
		this.approvedUsers = new HashSet<String>();
		this.approvedUsers.add(username);
		this.tasks = new LinkedHashMap<String, Boolean>();
	}

	public boolean addTask(String task) {
		if (this.tasks.containsKey(task)) {
			return false;
		} else {
			this.tasks.put(task, false);
			return true;
		}
	}

	public boolean hasPermission(String username) {
		return this.approvedUsers.contains(username);
	}

	public Map<String, Boolean> getTasks() {
		return this.tasks;
	}

	public boolean removeTask(String task) {
		if (this.tasks.containsKey(task)) {
			this.tasks.remove(task);
			return true;
		}
		return false;
	}
	
	public boolean checkTask(String task) {
		if (this.tasks.containsKey(task)) {
			this.tasks.put(task,true);
			return true;
		}
		return false;
	}
}
