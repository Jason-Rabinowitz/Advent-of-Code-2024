import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayFour {
    public static void main(String[] args) {
        // List of word search lines
        ArrayList<char[]> wordSearch = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));
            // Read results of file line by line
            for(String line: lines) {
                // Populate lists with file values
                char[] characters = new char[line.length()];
                // Convert string to array of characters
                for(int index = 0; index < characters.length; index++)
                    characters[index] = line.charAt(index);
                wordSearch.add(characters);
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Find instances of 'XMAS' in wordSearch
        int instanceXMAS = 0;

        // Recursively search through each line
        for(int row = 0; row < wordSearch.size(); row++) {
            char[] currentLine = wordSearch.get(row);
            for(int column = 0; column < currentLine.length; column++) {
                String[] directions = new String[8];
                Arrays.fill(directions, "IGNORE");
                if(currentLine[column] == 'X') {
                    // Forwards
                    try {
                        directions[0] = "" + currentLine[column] + currentLine[column + 1] + currentLine[column + 2] + currentLine[column + 3];
                    } catch (Exception ignored){}
                    // Backwards
                    try {
                        directions[1] = "" + currentLine[column] + currentLine[column - 1] + currentLine[column - 2] + currentLine[column - 3];
                    } catch (Exception ignored){}
                    // Downwards
                    try {
                        directions[2] = "" + currentLine[column] + wordSearch.get(row + 1)[column] + wordSearch.get(row + 2)[column] + wordSearch.get(row + 3)[column];
                    } catch (Exception ignored){}
                    // Upwards
                    try {
                        directions[3] = "" + currentLine[column] + wordSearch.get(row - 1)[column] + wordSearch.get(row - 2)[column] + wordSearch.get(row - 3)[column];
                    } catch (Exception ignored){}
                    // Down-Right Diagonal
                    try {
                        directions[4] = "" + currentLine[column] + wordSearch.get(row + 1)[column + 1] + wordSearch.get(row + 2)[column + 2] + wordSearch.get(row + 3)[column + 3];
                    } catch (Exception ignored){}
                    // Up-Right Diagonal
                    try {
                        directions[5] = "" + currentLine[column] + wordSearch.get(row - 1)[column + 1] + wordSearch.get(row - 2)[column + 2] + wordSearch.get(row - 3)[column + 3];
                    } catch (Exception ignored){}
                    // Down-Left Diagonal
                    try {
                        directions[6] = "" + currentLine[column] + wordSearch.get(row + 1)[column - 1] + wordSearch.get(row + 2)[column - 2] + wordSearch.get(row + 3)[column - 3];
                    } catch (Exception ignored){}
                    // Up-Right Diagonal
                    try {
                        directions[7] = "" + currentLine[column] + wordSearch.get(row - 1)[column - 1] + wordSearch.get(row - 2)[column - 2] + wordSearch.get(row - 3)[column - 3];
                    } catch (Exception ignored){}
                    // Check directions for XMAS instances
                    for(String direction: directions) {
                        if(direction.equals("XMAS"))
                            instanceXMAS++;
                    }
                }
            }
        }

        System.out.println("Instances of XMAS: "+instanceXMAS);

        // Find X-MAS instances in wordSearch
        int instanceX_MAS = 0;

        // Recursively search through each line
        for(int row = 0; row < wordSearch.size(); row++) {
            char[] currentLine = wordSearch.get(row);
            for(int column = 0; column < currentLine.length; column++) {
                char[][] rows = new char[3][3];
                if(currentLine[column] == 'A') {
                    // Read 3x3 grid around 'A'
                    try {
                        for(int checkRow = -1; checkRow <= 1; checkRow++) {
                            for(int checkColumn = -1; checkColumn <= 1; checkColumn++){
                                rows[checkRow + 1][checkColumn + 1] = wordSearch.get(row + checkRow)[column + checkColumn];
                            }
                        }
                    } catch (Exception ignored){}
                    // Check for rotations of 3x3 grid
                    ArrayList<char[][]> crossRotations = new ArrayList<>();
                    char[][] crossRotation = new char[3][3];
                    // Original grid
                    crossRotations.add(rows);
                    // 90 Degree Rotation
                    for(int crossRow = 0; crossRow < 3; crossRow++) {
                        for(int crossColumn = 0; crossColumn < 3; crossColumn++) {
                            crossRotation[crossRow][crossColumn] = rows[2 - crossColumn][crossRow];
                        }
                    }
                    crossRotations.add(crossRotation);
                    crossRotation = new char[3][3];
                    // 180 Degree Rotation
                    for(int crossRow = 0; crossRow < 3; crossRow++) {
                        for (int crossColumn = 0; crossColumn < 3; crossColumn++) {
                            crossRotation[crossRow][crossColumn] = rows[2 - crossRow][2 - crossColumn];
                        }
                    }
                    crossRotations.add(crossRotation);
                    crossRotation = new char[3][3];
                    // 270 Degree Rotation
                    for(int crossRow = 0; crossRow < 3; crossRow++) {
                        for(int crossColumn = 0; crossColumn < 3; crossColumn++) {
                            crossRotation[crossRow][crossColumn] = rows[crossColumn][2 - crossRow];
                        }
                    }
                    crossRotations.add(crossRotation);
                    // Check rotations for valid X-MAS in prime rotation
                    for(char[][] rotation: crossRotations) {
                        if((rotation[0][0] == 'M' && rotation[0][2] == 'M') && (rotation[2][0] == 'S' && rotation[2][2] == 'S')) {
                            instanceX_MAS++;
                        }
                    }
                }
            }
        }

        System.out.println("X-MAS Instances: "+instanceX_MAS);
    }
}