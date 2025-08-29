import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaySeven {
    public static void main(String[] args) {

        // List of calibration numbers
        ArrayList<ArrayList<Long>> calibrations = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));

            // Read results of file line by line
            for(String line: lines) {
                // Populate list with file values
                ArrayList<Long> values = new ArrayList<>();
                String[] equation = line.split(":");
                String[] calibration = equation[1].split(" ");
                values.add(Long.parseLong(equation[0]));
                for(String str: calibration) {
                    if(!str.isEmpty())
                        values.add(Long.parseLong(str));
                }
                calibrations.add(values);
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        long calibrationResult = 0;

        // Validate equations for each calibration
        for(ArrayList<Long> equation: calibrations) {
            // Create truth table for each calibration
            ArrayList<ArrayList<Boolean>> truthTable = new ArrayList<>();
            int n = equation.size()-1;
            boolean[] trueOrFalse = new boolean[n];
            Arrays.fill(trueOrFalse, true);
            // Create 2^n rows
            for(int i = 0; i < Math.pow(2, n); i++) {
                ArrayList<Boolean> row = new ArrayList<>();
                // Create n columns
                for(int j = 0; j < n; j++) {
                    row.add(trueOrFalse[j]);
                    // Flip true or false if i % 2^(n-j-1) is 0
                    if(i % (int)Math.pow(2, n-j-1) == 0)
                        trueOrFalse[j] = !trueOrFalse[j];
                }
                truthTable.add(row);
            }

            // Test equations using T as addition and F as multiplication operators
            for(ArrayList<Boolean> row: truthTable) {
                long result = equation.get(1);
                for(int index = 2; index < equation.size(); index++) {
                    // Addition
                    if(row.get(index-2))
                        result += equation.get(index);
                    // Multiplication
                    else
                        result *= equation.get(index);
                }
                // Valid equation
                if(result == equation.getFirst()) {
                    calibrationResult += equation.getFirst();
                    break;
                }
            }
        }

        System.out.println("Total calibration result with 2 operations: "+calibrationResult);

        calibrationResult = 0;

        // Validate equations for each calibration
        for(ArrayList<Long> equation: calibrations) {
            // Create truth table for each calibration
            ArrayList<ArrayList<Integer>> truthTable = new ArrayList<>();
            int n = equation.size()-1;
            int[] tripleOption = new int[n];
            Arrays.fill(tripleOption, 0);
            // Create 3^n rows
            for(int i = 0; i < Math.pow(3, n); i++) {
                ArrayList<Integer> row = new ArrayList<>();
                // Create n columns
                for(int j = 0; j < n; j++) {
                    row.add(tripleOption[j]);
                    // Flip true or false if i % 3^(n-j-1) is 0
                    if(i % (int)Math.pow(3, n-j-1) == 0) {
                        tripleOption[j]++;
                        if(tripleOption[j] > 2)
                            tripleOption[j] = 0;
                    }
                }
                truthTable.add(row);
            }

            // Test equations using T as addition and F as multiplication operators
            for(ArrayList<Integer> row: truthTable) {
                long result = equation.get(1);
                for(int index = 2; index < equation.size(); index++) {
                    // Addition
                    if(row.get(index-2) == 0)
                        result += equation.get(index);
                    // Multiplication
                    else if(row.get(index-2) == 1)
                        result *= equation.get(index);
                    // Concatenation
                    else
                        result = Long.parseLong(String.valueOf(result)+String.valueOf(equation.get(index)));
                }
                // Valid equation
                if(result == equation.getFirst()) {
                    calibrationResult += equation.getFirst();
                    break;
                }
            }
        }

        System.out.println("Total calibration result with 3 operations: "+calibrationResult);
    }
}