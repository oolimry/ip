package ducky;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveManagerTest {
    @Test
    public void SaveManager_testing() {
        SaveManager saveManager = new SaveManager();
        ArrayList<Command> commands;

        // test that thing is cleared properly
        saveManager.clearAllTasks();
        commands = saveManager.readAllLines();
        assertEquals(0, commands.size());

        // save something
        TaskList tasks = new TaskList();
        tasks.add(new ToDoTask("shower"));
        tasks.add(new DeadlineTask("sleep soon", LocalDate.of(2026,2,5)));
        saveManager.saveAllTasks(tasks);

        // load again
        commands = saveManager.readAllLines();
        assertEquals(2, commands.size());

        // testing the order is correct
        Command command1 = commands.get(0);
        assertEquals("todo", command1.get("commandType"));
        assertEquals("shower", command1.get("commandValue"));
        Command command2 = commands.get(1);
        assertEquals("deadline", command2.get("commandType"));
        assertEquals("sleep soon", command2.get("commandValue"));
    }
}