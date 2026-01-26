package ducky;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    @Test
    public void Command_normalParsing() {
        Command command1 = new Command("event party /to 1992-01-21     /from 1992-01-22");
        assertEquals("event", command1.get("commandType"));
        assertEquals("party", command1.get("commandValue"));
        assertEquals("1992-01-22", command1.get("from"));
        assertEquals("1992-01-21", command1.get("to"));
        assertEquals(null, command1.get("dummy"));
    }
}