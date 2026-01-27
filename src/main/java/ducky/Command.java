package ducky;


import java.util.HashMap;

public class Command extends HashMap<String, String> {

    /**
     * Constructor for a Command given an input String
     * Command format: [commandType] [commandValue] /param1 value1 /param2 value2 ...
     *
     * Command is a HashMap<String,String> with the following
     * command.get("commandType") gives the type of command
     * command.get("commandValue") gives the value of the command
     * command.get("paramX") gives valueX where X is an integer starting from 1
     *
     * @param input a string in the Command format described above
     */
    public Command (String input) {
        String[] segments = input.split("/");

        for(String segment : segments){
            String[] tokens = segment.split("\\s+");
            String param = tokens[0];
            String value = "";
            for (int i = 1; i < tokens.length; i++) {
                if (i != 1)
                    value += " "; 
                value += tokens[i];
            }

            if (segment == segments[0]) {
                this.put("commandType", param);
                this.put("commandValue", value);
            }
            
            else {
                this.put(param, value);
            }
        }
    } 
}