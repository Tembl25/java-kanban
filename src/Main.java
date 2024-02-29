import manager.Managers;
import interfaces.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = Managers.getDefault();

        addAllTask(taskManager);
        updateAndPrintTasks(taskManager);
    }

    private static void updateAndPrintTasks(TaskManager taskManager) {

        printAllTasks(taskManager);

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
        printAllTasks(taskManager);

        System.out.println("======================");
        System.out.println("Статусы эпиков и подзадач после изменения:");

        System.out.println("Статусы эпиков:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println("Эпик: " + epic.getTitle() + ", Статус: " + epic.getStatus());
        }

        System.out.println("Статусы подзадач:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println("Подзадача: " + subtask.getTitle() + ", Статус: " + subtask.getStatus());
        }
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

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllSubtasksForEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}