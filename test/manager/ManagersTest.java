package manager;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ManagersTest {

    @Test
    public void testDefaultTaskManagerNotNull() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
    }

    @Test
    public void testDefaultTaskManagerValidInstance() {
        TaskManager taskManager = Managers.getDefault();
        assertTrue(taskManager instanceof InMemoryTaskManager);
    }

    @Test
    public void testDefaultHistoryManagerNotNull() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }

    @Test
    public void testDefaultHistoryManagerValidInstance() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertTrue(historyManager instanceof InMemoryHistoryManager);
    }
}
