import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayTwo {
    public static void main(String[] args) {
        // List of levels
        ArrayList<ArrayList<Integer>> levels = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));
            // Read results of file line by line
            for(String line: lines) {
                // Populate lists with file values
                ArrayList<Integer> values = new ArrayList<>();
                for(String value: line.split(" ")) {
                    values.add(Integer.parseInt(value));
                }
                levels.add(values);
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Find safe reports
        int safeReports = 0;

        // Read all levels
        for(ArrayList<Integer> level: levels) {
            safeReports += readLevel(level) ? 1 : 0;
        }

        System.out.println("Safe reports: "+safeReports);

        // Find safe reports with allowances
        int safeReportsWithAllowances = 0;

        // Read all levels
        for(ArrayList<Integer> level: levels) {
            boolean safe = readLevel(level);
            // Check if report is already safe
            if(!readLevel(level)) {
                // Check if any report is safe by removing a single value
                ArrayList<Integer> removed;
                for(int index = 0; index < level.size(); index++) {
                    // Deep copy level without index value
                    removed = new ArrayList<>();
                    for(int entry = 0; entry < level.size(); entry++) {
                        if(entry != index)
                            removed.add(level.get(entry));
                    }
                    // Validate if altered report is safe
                    if(readLevel(removed)) {
                        safe = true;
                        break;
                    }
                }
            }
            safeReportsWithAllowances += safe ? 1 : 0;
        }

        System.out.println("Safe reports with allowances: "+safeReportsWithAllowances);
    }

    // Validate safety of report
    public static boolean readLevel(ArrayList<Integer> level) {
        boolean safe = true;
        boolean[] ascendingDescending = {false, false};
        // Read all level values
        for(int index = 0; index < level.size()-1; index++) {
            int diff = level.get(index) - level.get(index+1);
            // Compare level values for ascending or descending
            if(diff > 0)
                ascendingDescending[0] = true;
            else
                ascendingDescending[1] = true;
            // Check if ascending and descending
            if(ascendingDescending[0] && ascendingDescending[1]) {
                safe = false;
                break;
            }
            // Check if level values are within safe range
            if(Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                safe = false;
                break;
            }
        }
        return safe;
    }
}