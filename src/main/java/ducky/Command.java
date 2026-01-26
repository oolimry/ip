package ducky;


import java.util.HashMap;

public class Command extends HashMap<String, String> {

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