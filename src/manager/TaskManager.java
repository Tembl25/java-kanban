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

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public int createTask(Task task) {
        int taskId = taskIdCounter++;
        task.setId(taskId);
        task.setStatus(TaskStatus.NEW);
        tasks.put(taskId, task);
        return taskId;
    }

    public int createEpic(Epic epic) {
        int epicId = taskIdCounter++;
        epic.setId(epicId);
        epic.setStatus(TaskStatus.NEW);
        epics.put(epicId, epic);
        return epicId;
    }

    public int createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            int subtaskId = taskIdCounter++;
            subtask.setId(subtaskId);
            subtask.setStatus(TaskStatus.NEW);
            subtasks.put(subtaskId, subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubtaskId(subtaskId);
            recalculateStatus(epic);
            return subtaskId;
        }
        return -1;
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        Epic existingEpic = epics.get(epic.getId());
        if (existingEpic != null) {
            existingEpic.setTitle(epic.getTitle());
            existingEpic.setDescription(epic.getDescription());
        }
    }


    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null && epic.getSubtaskIds().contains(subtask.getId())) {
                subtasks.put(subtask.getId(), subtask);
                recalculateStatus(epic);
            } else {
                System.out.println("Ошибка: Неверно задан epicId для подзадачи или такой эпик не существует.");
            }
        } else {
            System.out.println("Подзадача с ID " + subtask.getId() + " не найдена в хранилище.");
        }
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
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
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                recalculateStatus(epic);
            } else {
                System.out.println("Ошибка: Эпик с ID " + subtask.getEpicId() + " не найден.");
            }
        } else {
            System.out.println("Подзадача с ID " + id + " не найдена в хранилище.");
        }
    }


    public List<Subtask> getAllSubtasksForEpic(int epicId) {
        Epic epic = epics.get(epicId);
        List<Subtask> epicSubtasks = new ArrayList<>();
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(TaskStatus.NEW);
        }
    }


    private void recalculateStatus(Epic epic) {
        boolean allDone = true;
        boolean allNew = true;
        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() != TaskStatus.NEW) {
                    allNew = false;
                }
            }
        }
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

}
