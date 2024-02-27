package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;
    private int taskIdCounter = 1;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(taskIdCounter++);
        task.setStatus(TaskStatus.NEW);
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(taskIdCounter++);
        epic.setStatus(TaskStatus.NEW);
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(taskIdCounter++);
            subtask.setStatus(TaskStatus.NEW);
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).recalculateStatus(this);
        }
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void removeTaskById(int id) {
        Task task = tasks.remove(id);
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        } else if (task instanceof Subtask) {
            for (Epic epic : epics.values()) {
                if (epic.getSubtaskIds().contains(id)) {
                    epic.getSubtaskIds().remove(Integer.valueOf(id));
                    epic.recalculateStatus(this);
                }
            }
        }
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.recalculateStatus(this);
        }
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        Epic existingEpic = epics.get(epic.getId());
        if (existingEpic != null) {
            existingEpic.setTitle(epic.getTitle());
            existingEpic.setDescription(epic.getDescription());
        }
    }

    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            for (Epic epic : epics.values()) {
                if (epic.getSubtaskIds().contains(id)) {
                    epic.getSubtaskIds().remove(Integer.valueOf(id));
                    epic.recalculateStatus(this);
                }
            }
        }
    }

    public List<Subtask> getAllSubtasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            List<Subtask> epicSubtasks = new ArrayList<>();
            for (int subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
            return epicSubtasks;
        } else {
            return new ArrayList<>();
        }
    }
}
