import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        addAllTask(taskManager);
        printAllTask(taskManager);
    }

    private static void addAllTask(TaskManager taskManager) {
        Task goToGym = new Task("Поход в спортзал", "Отжимания");
        taskManager.createTask(goToGym);

        Epic buyFood = new Epic("Купить продуктов", "Холодильник пуст");
        taskManager.createEpic(buyFood);

        Subtask buyMeet = new Subtask("Купить мясо", "Курица", 2);
        Subtask buyMilk = new Subtask("Купить молоко", "Коровье молоко", 2);
        taskManager.createSubtask(buyMeet);
        taskManager.createSubtask(buyMilk);
    }

    private static void printAllTask(TaskManager taskManager) {
        System.out.println("Задачи:");
        for (Task task: taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Task task: taskManager.getAllEpics()) {
            System.out.println(task);
        }

        System.out.println("Подзадачи:");
        for (Task task: taskManager.getAllSubtasks()) {
            System.out.println(task);
        }
    }
}