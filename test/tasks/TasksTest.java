package tasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import interfaces.TaskManager;
import manager.Managers;

public class TasksTest {
    private TaskManager taskManager;

    @Before
    public void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void testAddEpicAsSubtaskToItself() {
        Epic epic = new Epic("Epic", "Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description", epic.getId());
        taskManager.createSubtask(subtask);

        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(retrievedEpic);
        assertEquals(epic, retrievedEpic);

        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask.getId());
        assertNotNull(retrievedSubtask);
        assertEquals(subtask, retrievedSubtask);
    }

    @Test
    public void testConflictBetweenExplicitAndGeneratedIds() {
        Task taskWithExplicitId = new Task("Task with Explicit ID", "Description");
        taskWithExplicitId.setId(100);
        int explicitId = taskManager.createTask(taskWithExplicitId);

        Task anotherTask = new Task("Another Task", "Description");
        int generatedId = taskManager.createTask(anotherTask);

        assertNotEquals(explicitId, generatedId);
    }

    @Test
    public void testCreateAndGetEpicById() {
        Epic epic = new Epic("Epic 1", "Description 1");
        int epicId = taskManager.createEpic(epic);

        Epic retrievedEpic = taskManager.getEpicById(epicId);

        assertEquals(epic, retrievedEpic);
    }

    @Test
    public void testCreateAndGetSubtaskById() {
        Epic epic = new Epic("Epic for Subtask", "Description for Subtask");
        int epicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Description 1", epicId);
        int subtaskId = taskManager.createSubtask(subtask);

        Subtask retrievedSubtask = taskManager.getSubtaskById(subtaskId);

        assertEquals(subtask, retrievedSubtask);
    }

    @Test
    public void testCreateAndGetTaskById() {
        Task task = new Task("Task 1", "Description 1");
        int taskId = taskManager.createTask(task);

        Task retrievedTask = taskManager.getTaskById(taskId);

        assertEquals(task, retrievedTask);
    }

    @Test
    public void testCreateTasksOfDifferentTypes() {
        Task task = new Task("Task", "Description");
        Epic epic = new Epic("Epic", "Description");
        Subtask subtask = new Subtask("Subtask", "Description", 1);

        int taskId = taskManager.createTask(task);
        int epicId = taskManager.createEpic(epic);
        int subtaskId = taskManager.createSubtask(subtask);

        assertNotEquals(taskId, epicId);
        assertNotEquals(epicId, subtaskId);
        assertNotEquals(taskId, subtaskId);
    }

    @Test
    public void testSubtaskCannotBeItsOwnEpic() {
        Epic epic = new Epic("Epic 1", "Description 1");
        int epicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Description 1", 2);
        subtask.setId(epicId);

        int subtaskId = taskManager.createSubtask(subtask);
        assertEquals(-1, subtaskId);
    }

    @Test
    public void testSubtaskEqualityById() {
        Epic epic = new Epic("Epic", "Description");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        Subtask retrievedSubtask1 = taskManager.getSubtaskById(subtask1.getId());
        Subtask retrievedSubtask2 = taskManager.getSubtaskById(subtask2.getId());

        assertEquals(subtask1, retrievedSubtask1);
        assertEquals(subtask2, retrievedSubtask2);
    }

    @Test
    public void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Task retrievedTask1 = taskManager.getTaskById(task1.getId());
        Task retrievedTask2 = taskManager.getTaskById(task2.getId());

        assertEquals(task1, retrievedTask1);
        assertEquals(task2, retrievedTask2);
    }

    @Test
    public void testTaskImmutableAfterAddingToManager() {
        Task originalTask = new Task("Original Task", "Description");

        int taskId = taskManager.createTask(originalTask);

        Task retrievedTask = taskManager.getTaskById(taskId);

        assertEquals(originalTask, retrievedTask);
    }
}
