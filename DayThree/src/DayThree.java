import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayThree {
    public static void main(String[] args) {
        // List of memory
        ArrayList<String> validMemory = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));

            //Concatenate lines
            String memoryBlock = "";
            for(String line: lines)
                memoryBlock += line;

            int indexFound;

            // Recursively check if line contains beginning of sequence
            while(memoryBlock.contains("mul(")) {
                indexFound = memoryBlock.lastIndexOf("mul(");
                int terminationIndex = -1;
                // Check if line contains end of sequence
                for(int index = indexFound+4; index < memoryBlock.length(); index++) {
                    if(memoryBlock.charAt(index) == ')') {
                        terminationIndex = index+1;
                        break;
                    }
                }
                // Add substring of valid memory to list
                if(terminationIndex != -1) {
                    validMemory.add(memoryBlock.substring(indexFound, terminationIndex));
                    // Remove substring from line
                    if(terminationIndex+1 < memoryBlock.length())
                        memoryBlock = memoryBlock.substring(0, indexFound) + memoryBlock.substring(terminationIndex+1);
                    else
                        memoryBlock = memoryBlock.substring(0, indexFound);
                } else {
                    // Remove substring from line
                    memoryBlock = memoryBlock.substring(0, indexFound) + memoryBlock.substring(indexFound+4);
                }
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Validate "valid" memory
        ArrayList<String> invalidMemory = new ArrayList<>();

        // Calculate multiples of valid memory
        int totalValid = 0;

        // Read all multiples and multiply the values
        for(String memory: validMemory) {
            // Validate if substring is a valid number
            try {
                totalValid += Integer.parseInt(memory.substring(memory.indexOf("(") + 1, memory.indexOf(","))) * Integer.parseInt(memory.substring(memory.indexOf(",") + 1, memory.indexOf(")")));
            } catch(NumberFormatException | StringIndexOutOfBoundsException e) {
                invalidMemory.add(memory);
            }
        }

        // Remove all invalid memory
        for(String memory: invalidMemory) {
            validMemory.remove(memory);
        }

        System.out.println("Uncorrupted memory values: "+totalValid);

        // List of memory with commands
        ArrayList<String> validMemoryWithCommands = new ArrayList<>();
        ArrayList<String> notvalidMemoryWithCommands = new ArrayList<>();

        // Get values from input.txt
        try {
            // Set default memory activation
            boolean memoryEnabled = true;

            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));

            //Concatenate lines
            String memoryBlock = "";
            for(String line: lines)
                memoryBlock += line;

            int indexPoint = 0;

            // Recursively check if line contains beginning of sequence
            while(memoryBlock.substring(indexPoint).contains("mul(") || memoryBlock.substring(indexPoint).contains("do()") || memoryBlock.substring(indexPoint).contains("don't()")) {
                // Check for sequence closest to indexPoint
                int offset = Math.min(Math.min(memoryBlock.substring(indexPoint).contains("mul(") ? memoryBlock.substring(indexPoint).indexOf("mul("): 999999, memoryBlock.substring(indexPoint).contains("do()") ? memoryBlock.substring(indexPoint).indexOf("do()"): 999999), memoryBlock.substring(indexPoint).contains("don't()") ? memoryBlock.substring(indexPoint).indexOf("don't()"): 999999);
                if(offset == -1)
                    break;
                indexPoint += offset;
                // Check if sequence is command or value
                if (memoryBlock.substring(indexPoint).indexOf("do()") == 0 || memoryBlock.substring(indexPoint).indexOf("don't()") == 0) {
                    memoryEnabled = memoryBlock.substring(indexPoint).indexOf("do()") == 0;
                    // Move indexPoint forward
                    indexPoint += memoryEnabled ? 4 : 7;
                } else {
                    int terminationIndex = -1;
                    // Check if line contains end of sequence
                    for (int index = indexPoint; index < memoryBlock.length(); index++) {
                        if (memoryBlock.charAt(index) == ')') {
                            terminationIndex = index + 1;
                            break;
                        }
                    }
                    // Add substring of valid memory to list
                    if (terminationIndex != -1) {
                        if(memoryEnabled)
                            validMemoryWithCommands.add(memoryBlock.substring(indexPoint, terminationIndex));
                        else
                            notvalidMemoryWithCommands.add(memoryBlock.substring(indexPoint, terminationIndex));
                    }
                    indexPoint = indexPoint + 4;
                }
                if(indexPoint > memoryBlock.length())
                    break;
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Validate "valid" memory
        ArrayList<String> invalidMemoryWithCommands = new ArrayList<>();

        // Calculate multiples of valid memory
        int totalValidEnabled = 0;

        // Read all multiples and multiply the values
        for(String memory: validMemoryWithCommands) {
            try {
                totalValidEnabled += Integer.parseInt(memory.substring(memory.indexOf("(") + 1, memory.indexOf(","))) * Integer.parseInt(memory.substring(memory.indexOf(",") + 1, memory.indexOf(")")));
            } catch(NumberFormatException | StringIndexOutOfBoundsException e) {
                invalidMemoryWithCommands.add(memory);
            }
        }

        // Remove all invalid memory
        for(String memory: invalidMemoryWithCommands) {
            validMemoryWithCommands.remove(memory);
        }

        System.out.println("Enabled uncorrupted memory values: "+totalValidEnabled);
    }
}