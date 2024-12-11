import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class DayOne {
    public static void main(String[] args) {
        // Number lists
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));
            // Read results of file line by line
            for(String line: lines) {
                // Populate lists with file values
                int[] tuple = {Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[line.split(" ").length-1])};
                leftList.add(tuple[0]);
                rightList.add(tuple[1]);
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Sort lists
        Collections.sort(leftList);
        Collections.sort(rightList);

        // Find total distance
        int totalDistance = 0;
        for(int index = 0; index < leftList.size(); index++) {
            totalDistance += Math.abs(leftList.get(index) - rightList.get(index));
        }

        // Print result of total distance
        System.out.println("Total distance: "+totalDistance);

        int similarityScore = 0;
        for(int entry: leftList) {
            int appearances = 0;
            for(int value: rightList) {
                if(entry == value)
                    appearances++;
            }
            similarityScore += entry * appearances;
        }

        // Print result of similarity score
        System.out.println("Similarity score: "+similarityScore);
    }
}