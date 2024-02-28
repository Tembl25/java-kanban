import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        addAllTask(taskManager);
        updateAndPrintTasks(taskManager);
    }

    private static void updateAndPrintTasks(TaskManager taskManager) {

        printAllTask(taskManager);

        Task taskToUpdate = taskManager.getTaskById(9);
        taskToUpdate.setStatus(TaskStatus.DONE);
        taskManager.updateTask(taskToUpdate);

        Subtask subtaskToUpdate = taskManager.getSubtaskById(3);
        subtaskToUpdate.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtaskToUpdate);

        taskManager.removeTaskById(1);
        taskManager.removeEpicById(6);

        System.out.println("======================");
        System.out.println("После изменения задач:");
        printAllTask(taskManager);
    }

    private static void addAllTask(TaskManager taskManager) {
        Task goToGym = new Task("Поход в спортзал", "Отжимания");
        taskManager.createTask(goToGym);

        Epic buyFood = new Epic("Купить продуктов", "Холодильник пуст");
        taskManager.createEpic(buyFood);

        Subtask buyMeet = new Subtask("Купить мясо", "Курица", buyFood.getId());
        Subtask buyMilk = new Subtask("Купить молоко", "Коровье молоко", buyFood.getId());
        Subtask buyCheese = new Subtask("Купить сыр", "Классический сыр", buyFood.getId());
        taskManager.createSubtask(buyMeet);
        taskManager.createSubtask(buyMilk);
        taskManager.createSubtask(buyCheese);

        Epic buyClothes = new Epic("Купить одежду", "Нет одежды");
        taskManager.createEpic(buyClothes);

        Subtask buyJacket = new Subtask("Купить куртку", "Классическая куртка", buyClothes.getId());
        Subtask buyPants = new Subtask("Купить штаны", "Классические штаны", buyClothes.getId());
        taskManager.createSubtask(buyJacket);
        taskManager.createSubtask(buyPants);

        Task walk = new Task("Погулять", "Погулять с собакой");
        taskManager.createTask(walk);
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