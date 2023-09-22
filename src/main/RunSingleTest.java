package main;

import client.UserInteraction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileInput.Input;

import java.io.File;
import java.io.IOException;

public class RunSingleTest {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File("checker/resources/in/basic_7.json"), Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        UserInteraction userInteraction = new UserInteraction(input, output);
        userInteraction.startUserInteraction();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
