import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TaskManager {
    private final Map<Integer, Task> tasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public List<Subtask> getAllSubtasksForEpic(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task instanceof Subtask subtask) {
                if (subtask.getEpicId() == epicId) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }
}