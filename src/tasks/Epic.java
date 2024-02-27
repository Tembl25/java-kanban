package tasks;

import manager.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds;

    public Epic(String title, String description) {
        super(title, description);
        this.subtaskIds = new ArrayList<>();
    }

    public void recalculateStatus(TaskManager taskManager) {
        boolean allDone = true;
        boolean allNew = true;

        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = taskManager.getSubtaskById(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                }
            }
        }
        if (allDone) {
            setStatus(TaskStatus.NEW);
        } else if (allNew) {
            setStatus(TaskStatus.DONE);
        } else {
            setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }


    @Override
    public String toString() {
        return "Epic" + super.toString();
    }
}
