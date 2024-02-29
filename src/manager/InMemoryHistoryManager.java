package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final Deque<Task> taskHistory;

    public InMemoryHistoryManager() {
        this.taskHistory = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            taskHistory.remove(task);
            taskHistory.addFirst(task);
            if (taskHistory.size() > 10) {
                taskHistory.removeLast();
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskHistory);
    }

}
