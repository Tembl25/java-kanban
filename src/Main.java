import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        addTasks(taskManager);
        System.out.println("Добавлены задачи:");
        printAllTasks(taskManager);
        System.out.println();

        Subtask subtask1 = (Subtask) taskManager.getTaskById(2);
        if (subtask1 != null) {
            subtask1.setStatus(TaskStatus.IN_PROGRESS);
            taskManager.updateTask(subtask1);
            System.out.println("Статус обновлен: " + subtask1.getStatus());
        } else {
            System.out.println("Задача не найдена");
        }
        System.out.println();

        List<Subtask> epicSubtasks = taskManager.getAllSubtasksForEpic(1);
        System.out.println("Подзадачи для эпика 1:");
        for (Subtask subtask : epicSubtasks) {
            System.out.println("Подзадача: " + subtask.getTitle() + ", Статус: " + subtask.getStatus());
        }
        System.out.println();

        removeTask(taskManager);
        System.out.println("Задача удалена");
        System.out.println();

        System.out.println("Оставшиеся задачи:");
        printAllTasks(taskManager);
    }

    private static void addTasks(TaskManager taskManager) {
        Epic epic1 = new Epic(1, "Крупный проект", "Описание крупного проекта", TaskStatus.NEW);
        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, 1);
        Subtask subtask2 = new Subtask(3, "Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, 1);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        taskManager.createTask(epic1);
        taskManager.createTask(subtask1);
        taskManager.createTask(subtask2);
    }

    private static void printAllTasks(TaskManager taskManager) {
        List<Task> allTasks = taskManager.getAllTasks();
        for (Task task : allTasks) {
            System.out.println("Задача: " + task.getTitle() + ", Статус: " + task.getStatus());
        }
    }

    private static void removeTask(TaskManager taskManager) {
        taskManager.removeTaskById(3);
    }
}
