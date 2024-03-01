package manager;

import interfaces.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final Deque<Task> taskHistory;
    private static final int MAX_TASK_HISTORY_SIZE = 10;

    public InMemoryHistoryManager() {
        this.taskHistory = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            taskHistory.addFirst(task);
            if (taskHistory.size() > MAX_TASK_HISTORY_SIZE) {
                taskHistory.removeLast();
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskHistory);
    }
}
