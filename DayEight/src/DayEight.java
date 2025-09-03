import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayEight {
    public static void main(String[] args) {
        // Map of antennae
        ArrayList<ArrayList<Character>> map = new ArrayList<>();

        // Frequencies and locations of antennae
        ArrayList<Character> frequencies = new ArrayList<>();
        ArrayList<ArrayList<Integer[]>> locations = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));
            // Read results of file line by line
            for(String line: lines) {
                // Populate list with file values
                ArrayList<Character> row = new ArrayList<>();
                ArrayList<Integer[]> locationList;
                for(String character: line.split("")) {
                    char c = character.charAt(0);
                    row.add(c);
                    // Check if character is a frequency
                    if(c != '.') {
                        // Check if frequency previously been found and populate lists
                        if (!frequencies.contains(c)) {
                            locationList = new ArrayList<>();
//                            locationList.add(new Integer[]{lines.indexOf(line), line.indexOf(character)});
                            locationList.add(new Integer[]{line.indexOf(character), lines.indexOf(line)});
                            locations.add(locationList);
                            frequencies.add(c);
                        } else {
                            locations.get(frequencies.indexOf(c)).add(new Integer[]{line.indexOf(character), lines.indexOf(line)});
                        }
                    }
                }
                map.add(row);
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // List of antennae pair permutations
        ArrayList<ArrayList<Integer[][]>> pairings = new ArrayList<>();

        // Populate pairings with permutations
        for(ArrayList<Integer[]> character: locations) {
            ArrayList<Integer[][]> pairs = new ArrayList<>();
            Integer[][] pair;
            for (Integer[] location : character) {
                for(Integer[] location2: character) {
                    if(!Arrays.equals(location, location2)) {
                        pair = new Integer[][]{location, location2};
                        pairs.add(pair);
                    }
                }
            }
            pairings.add(pairs);
        }

        // Antinode locations
        ArrayList<Integer[]> antinodes = new ArrayList<>();

        // Current antinode location
        Integer[] location;

        // Find all possible antinodes
        for(int index = 0; index < frequencies.size(); index++) {
            for(Integer[][] match: pairings.get(index)) {
                // Get distance separating paired antennae
                int distanceX = match[1][0] - match[0][0];
                int distanceY = match[1][1] - match[0][1];

                // Antinode 1
                location = null;
                if(!antinodes.isEmpty()) {
                    for(Integer[] antinode: antinodes) {
                        location = new Integer[]{match[0][0] - distanceX, match[0][1] - distanceY};
                        // Check for duplicate
                        if (match[0][0] - distanceX == antinode[0] && match[0][1] - distanceY == antinode[1]) {
                            location = null;
                            break;
                        }
                    }
                } else {
                    location = new Integer[]{match[0][0] - distanceX, match[0][1] - distanceY};
                }
                if(location != null)
                    antinodes.add(new Integer[]{match[0][0]-distanceX, match[0][1]-distanceY});

                // Antinode 2
                location = null;
                for (Integer[] antinode : antinodes) {
                    location = new Integer[]{match[1][0] + distanceX, match[1][1] + distanceY};
                    // Check for duplicate
                    if (match[1][0] + distanceX == antinode[0] && match[1][1] + distanceY == antinode[1]) {
                        location = null;
                        break;
                    }
                }
                if(location != null)
                    antinodes.add(new Integer[]{match[1][0]+distanceX, match[1][1]+distanceY});
            }
        }

        // Out of bounds antinodes
        ArrayList<Integer[]> outOfBounds = new ArrayList<>();

        // Check for out of bounds antinodes
        for(Integer[] antinode: antinodes) {
            // Validate x boundary
            if(antinode[0] < 0 || antinode[0] > map.getFirst().size() - 1) {
                outOfBounds.add(antinode);
            }
            // Validate y boundary
            else if(antinode[1] < 0 || antinode[1] > map.size() - 1) {
                outOfBounds.add(antinode);
            }
        }

        // Remove out of bounds antinodes
        for(Integer[] antinode: outOfBounds)
            antinodes.remove(antinode);

        System.out.println("Total unique antinodes: "+antinodes.size());

        // Antinode locations
        antinodes = new ArrayList<>();

        // Find all possible antinodes
        for(int index = 0; index < frequencies.size(); index++) {
            for(Integer[][] match: pairings.get(index)) {
                // Get distance separating paired antennae
                int distanceX = match[1][0] - match[0][0];
                int distanceY = match[1][1] - match[0][1];

                // Antinode 1
                location = null;
                if(!antinodes.isEmpty()) {
                    for(Integer[] antinode: antinodes) {
                        location = new Integer[]{match[0][0] - distanceX, match[0][1] - distanceY};
                        // Check for duplicate
                        if (match[0][0] - distanceX == antinode[0] && match[0][1] - distanceY == antinode[1]) {
                            location = null;
                            break;
                        }
                    }
                } else {
                    antinodes.add(new Integer[]{match[0][0]-distanceX, match[0][1]-distanceY});
                }
                if(location != null) {
                    antinodes.add(new Integer[]{match[0][0] - distanceX, match[0][1] - distanceY});
                }

                // Locate all harmonies
                location = new Integer[]{match[0][0], match[0][1]};
                while((location[0] >= 0 && location[0] < map.getFirst().size()) && (location[1] >= 0 && location[1] < map.size())) {
                    boolean duplicate = false;
                    for(Integer[] antinode: antinodes) {
                        // Check for duplicate
                        if (location[0] == antinode[0] && location[1] == antinode[1]) {
                            duplicate = true;
                            break;
                        }
                    }
                    if(!duplicate)
                        antinodes.add(new Integer[]{location[0], location[1]});
                    location[0] -= distanceX;
                    location[1] -=distanceY;
                }

                // Antinode 2
                location = null;
                for (Integer[] antinode : antinodes) {
                    location = new Integer[]{match[1][0] + distanceX, match[1][1] + distanceY};
                    // Check for duplicate
                    if (match[1][0] + distanceX == antinode[0] && match[1][1] + distanceY == antinode[1]) {
                        location = null;
                        break;
                    }
                }
                if(location != null) {
                    antinodes.add(new Integer[]{match[1][0] + distanceX, match[1][1] + distanceY});
                }

                // Locate all harmonies
                location = new Integer[]{match[1][0], match[1][1]};
                while((location[0] >= 0 && location[0] < map.getFirst().size()) && (location[1] >= 0 && location[1] < map.size())) {
                    boolean duplicate = false;
                    for(Integer[] antinode: antinodes) {
                        // Check for duplicate
                        if (location[0] == antinode[0] && location[1] == antinode[1]) {
                            duplicate = true;
                            break;
                        }
                    }
                    if(!duplicate)
                        antinodes.add(new Integer[]{location[0], location[1]});
                    location[0] += distanceX;
                    location[1] +=distanceY;
                }
            }
        }

        // Out of bounds antinodes
        outOfBounds = new ArrayList<>();

        // Check for out of bounds antinodes
        for(Integer[] antinode: antinodes) {
            // Validate x boundary
            if(antinode[0] < 0 || antinode[0] > map.getFirst().size() - 1) {
                outOfBounds.add(antinode);
            }
            // Validate y boundary
            else if(antinode[1] < 0 || antinode[1] > map.size() - 1) {
                outOfBounds.add(antinode);
            }
        }

        // Remove out of bounds antinodes
        for(Integer[] antinode: outOfBounds)
            antinodes.remove(antinode);

        System.out.println("Total unique antinodes with harmonies: "+antinodes.size());

        }
    }
