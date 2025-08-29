import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaySix {
    public static void main(String[] args) {
        // List of map rows
        ArrayList<ArrayList<Character>> map = new ArrayList<>();

        // Clone of map rows
        ArrayList<ArrayList<Character>> mapCopy = new ArrayList<>();

        // Current guard location
        int[] guardLocation = new int[2];
        
        // Clone of guard location
        int[] guardLocationCopy = new int[2];
        
        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));

            // Read results of file line by line
            for(String line: lines) {
                // Populate list with file values
                ArrayList<Character> mapRow = new ArrayList<>();
                for(int index = 0; index < line.length(); index++) {
                    mapRow.add(line.charAt(index));
                    if(line.charAt(index) == '^') {
                        guardLocation = new int[]{index, map.size()};
                        guardLocationCopy = new int[]{index, map.size()};
                    }
                    }
                map.add(mapRow);
                mapCopy.add(new ArrayList<>(List.copyOf(mapRow)));
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Walking direction
        int direction = 0;

        // Walk guard until they are off the map
        while(isInBounds(guardLocation, direction, map)) {
            switch(direction) {
                // Move up
                case 0:
                    // Check if guard will be in bounds if moved
                    if(isInBounds(guardLocation, direction, map)) {
                        // Rotate or set new guard position on map
                        if(map.get(guardLocation[1]-1).get(guardLocation[0]) == '#') {
                            map.get(guardLocation[1]).set(guardLocation[0], '>');
                            direction++;
                        } else {
                            map.get(guardLocation[1]).set(guardLocation[0], 'X');
                            map.get(guardLocation[1]-1).set(guardLocation[0], '^');
                            guardLocation[1]--;
                        }
                    }
                    break;
                // Move right
                case 1:
                    // Check if guard will be in bounds if moved
                    if(isInBounds(guardLocation, direction, map)) {
                        // Rotate or set new guard position on map
                        if(map.get(guardLocation[1]).get(guardLocation[0]+1) == '#') {
                            map.get(guardLocation[1]).set(guardLocation[0], 'V');
                            direction++;
                        } else {
                            map.get(guardLocation[1]).set(guardLocation[0], 'X');
                            map.get(guardLocation[1]).set(guardLocation[0]+1, '>');
                            guardLocation[0]++;
                        }
                    }
                    break;
                // Move down
                case 2:
                    // Check if guard will be in bounds if moved
                    if(isInBounds(guardLocation, direction, map)) {
                        // Rotate or set new guard position on map
                        if(map.get(guardLocation[1]+1).get(guardLocation[0]) == '#') {
                            map.get(guardLocation[1]).set(guardLocation[0], '<');
                            direction++;
                        } else {
                            map.get(guardLocation[1]).set(guardLocation[0], 'X');
                            map.get(guardLocation[1]+1).set(guardLocation[0], 'V');
                            guardLocation[1]++;
                        }
                    }
                    break;
                // Move left
                case 3:
                    // Check if guard will be in bounds if moved
                    if(isInBounds(guardLocation, direction, map)) {
                        // Rotate or set new guard position on map
                        if(map.get(guardLocation[1]).get(guardLocation[0]-1) == '#') {
                            map.get(guardLocation[1]).set(guardLocation[0], '^');
                            direction = 0;
                        } else {
                            map.get(guardLocation[1]).set(guardLocation[0], 'X');
                            map.get(guardLocation[1]).set(guardLocation[0]-1, '<');
                            guardLocation[0]--;
                        }
                    }
                    break;
            }
        }

        // Positions guard has visited
        int visitedPositions = 1;

        for(ArrayList<Character> row: map)
            for(Character pos: row)
                if(pos == 'X')
                    visitedPositions++;

        System.out.println("Total positions visited by guard: "+visitedPositions);

        // Total map intersections
        int intersections = 0;

        // Possible obstruction locations
        ArrayList<int[]> possibleObstructions = new ArrayList<>();

        // Validated obstruction locations
        ArrayList<int[]> validObstructions = new ArrayList<>();

        // Obstruction location
        int[] obstruction;

        // Check map for valid obstructions
        for(int yPos = 0; yPos < map.size(); yPos++) {
            for(int xPos = 0; xPos < map.getFirst().size(); xPos++) {
                if(yPos < map.size() - 1) {
                    if(map.get(yPos).get(xPos) == 'X') {
                        // Check valid downwards obstructions
                        if(map.get(yPos+1).get(xPos) == 'X') {
                            if(yPos + 2 < map.size()) {
                                obstruction = new int[]{xPos, (yPos + 2)};
                                boolean foundEquals = false;
                                if(possibleObstructions.isEmpty())
                                    possibleObstructions.add(obstruction);
                                else {
                                    for (int[] possibleObstruction : possibleObstructions) {
                                        if (Arrays.equals(possibleObstruction, obstruction)) {
                                            foundEquals = true;
                                            break;
                                        }
                                    }
                                    if(!foundEquals)
                                        possibleObstructions.add(obstruction);
                                }
                            }
                        }
                    }
                }
                if(yPos > 0) {
                    if(map.get(yPos).get(xPos) == 'X') {
                        // Check valid upwards obstructions
                        if(map.get(yPos-1).get(xPos) == 'X') {
                            if(yPos - 2 >= 0) {
                                obstruction = new int[]{xPos, (yPos - 2)};
                                boolean foundEquals = false;
                                if(possibleObstructions.isEmpty())
                                    possibleObstructions.add(obstruction);
                                else {
                                    for (int[] possibleObstruction : possibleObstructions) {
                                        if (Arrays.equals(possibleObstruction, obstruction)) {
                                            foundEquals = true;
                                            break;
                                        }
                                    }
                                    if(!foundEquals)
                                        possibleObstructions.add(obstruction);
                                }
                            }
                        }
                    }
                }
                if(xPos < map.getFirst().size() - 1) {
                    if(map.get(yPos).get(xPos) == 'X') {
                        // Check valid rightwards obstructions
                        if(map.get(yPos).get(xPos+1) == 'X') {
                            if(xPos + 2 < map.getFirst().size()) {
                                obstruction = new int[]{xPos + 2, (yPos)};
                                boolean foundEquals = false;
                                if(possibleObstructions.isEmpty())
                                    possibleObstructions.add(obstruction);
                                else {
                                    for (int[] possibleObstruction : possibleObstructions) {
                                        if (Arrays.equals(possibleObstruction, obstruction)) {
                                            foundEquals = true;
                                            break;
                                        }
                                    }
                                    if(!foundEquals)
                                        possibleObstructions.add(obstruction);
                                }
                            }
                        }
                    }
                }
                if(xPos > 0) {
                    if(map.get(yPos).get(xPos) == 'X') {
                        // Check valid leftwards obstructions
                        if(map.get(yPos).get(xPos-1) == 'X') {
                            if(xPos - 2 >= 0) {
                                obstruction = new int[]{xPos - 2, (yPos)};
                                boolean foundEquals = false;
                                if(possibleObstructions.isEmpty())
                                    possibleObstructions.add(obstruction);
                                else {
                                    for (int[] possibleObstruction : possibleObstructions) {
                                        if (Arrays.equals(possibleObstruction, obstruction)) {
                                            foundEquals = true;
                                            break;
                                        }
                                    }
                                    if(!foundEquals)
                                        possibleObstructions.add(obstruction);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Remove obstruction from guard starting location if exists
        int[] startingLocation = null;
        for(int[] possibleObstruction: possibleObstructions) {
            if (Arrays.equals(possibleObstruction, guardLocationCopy))
                startingLocation = possibleObstruction;
        }
        if(startingLocation != null)
            possibleObstructions.remove(startingLocation);

        // Clone of map copy
        ArrayList<ArrayList<Character>> currentMap = new ArrayList<>();

        // Clone of guard location copy
        int[] currentGuardLocation = new int[]{guardLocationCopy[0], guardLocationCopy[1]};
        
        // Validate obstruction locations for time loop
        for(int currentObstruction = 0; currentObstruction < possibleObstructions.size(); currentObstruction++) {
            // Reset current map
            currentMap = new ArrayList<>();
            
            // Duplicate map copy
            for(ArrayList<Character> row: mapCopy) {
                ArrayList<Character> mapRow = new ArrayList<>();
                for(Character c: row) {
                    mapRow.add(c);
                }
                currentMap.add(mapRow);
            }

            // Set obstruction to validate
            currentMap.get(possibleObstructions.get(currentObstruction)[1]).set(possibleObstructions.get(currentObstruction)[0], 'O');

            // Reset current guard location
            currentGuardLocation = new int[]{guardLocationCopy[0], guardLocationCopy[1]};

            // Locations of turns taken by guard
            ArrayList<int[]> turnLocations = new ArrayList<>();

            // Walking direction
            direction = 0;

            // Turning lock
            boolean finishedTurning = true;

            // Walk guard until they are off the mapCopy
            while(isInBounds(currentGuardLocation, direction, currentMap)) {
                boolean looping = false;
                switch(direction) {
                    // Move up
                    case 0:
                        // Check if guard will be in bounds if moved
                        if(isInBounds(currentGuardLocation, direction, currentMap)) {
                            // Rotate or set new guard position on currentMap
                            if(currentMap.get(currentGuardLocation[1]-1).get(currentGuardLocation[0]) == '#' || currentMap.get(currentGuardLocation[1]-1).get(currentGuardLocation[0]) == 'O') {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], '>');
                                if(finishedTurning) {
                                    // Check if turn location has already been visited
                                    for (int[] turnLocation : turnLocations) {
                                        if (Arrays.equals(turnLocation, new int[]{currentGuardLocation[0], currentGuardLocation[1]})) {
                                            looping = true;
                                            break;
                                        }
                                    }
                                    turnLocations.add(new int[]{currentGuardLocation[0], currentGuardLocation[1]});
                                    finishedTurning = false;
                                }
                                direction++;
                            } else {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], 'X');
                                currentMap.get(currentGuardLocation[1]-1).set(currentGuardLocation[0], '^');
                                currentGuardLocation[1]--;
                                finishedTurning = true;
                            }
                        }
                        break;
                    // Move right
                    case 1:
                        // Check if guard will be in bounds if moved
                        if(isInBounds(currentGuardLocation, direction, currentMap)) {
                            // Rotate or set new guard position on currentMap
                            if(currentMap.get(currentGuardLocation[1]).get(currentGuardLocation[0]+1) == '#' || currentMap.get(currentGuardLocation[1]).get(currentGuardLocation[0]+1) == 'O') {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], 'V');
                                if(finishedTurning) {
                                    // Check if turn location has already been visited
                                    for (int[] turnLocation : turnLocations) {
                                        if (Arrays.equals(turnLocation, new int[]{currentGuardLocation[0], currentGuardLocation[1]})) {
                                            looping = true;
                                            break;
                                        }
                                    }
                                    turnLocations.add(new int[]{currentGuardLocation[0], currentGuardLocation[1]});
                                    finishedTurning = false;
                                }
                                direction++;
                            } else {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], 'X');
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0]+1, '>');
                                currentGuardLocation[0]++;
                                finishedTurning = true;
                            }
                        }
                        break;
                    // Move down
                    case 2:
                        // Check if guard will be in bounds if moved
                        if(isInBounds(currentGuardLocation, direction, currentMap)) {
                            // Rotate or set new guard position on currentMap
                            if(currentMap.get(currentGuardLocation[1]+1).get(currentGuardLocation[0]) == '#' || currentMap.get(currentGuardLocation[1]+1).get(currentGuardLocation[0]) == 'O') {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], '<');
                                if(finishedTurning) {
                                    // Check if turn location has already been visited
                                    for (int[] turnLocation : turnLocations) {
                                        if (Arrays.equals(turnLocation, new int[]{currentGuardLocation[0], currentGuardLocation[1]})) {
                                            looping = true;
                                            break;
                                        }
                                    }
                                    turnLocations.add(new int[]{currentGuardLocation[0], currentGuardLocation[1]});
                                    finishedTurning = false;
                                }
                                direction++;
                            } else {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], 'X');
                                currentMap.get(currentGuardLocation[1]+1).set(currentGuardLocation[0], 'V');
                                currentGuardLocation[1]++;
                                finishedTurning = true;
                            }
                        }
                        break;
                    // Move left
                    case 3:
                        // Check if guard will be in bounds if moved
                        if(isInBounds(currentGuardLocation, direction, currentMap)) {
                            // Rotate or set new guard position on currentMap
                            if(currentMap.get(currentGuardLocation[1]).get(currentGuardLocation[0]-1) == '#' || currentMap.get(currentGuardLocation[1]).get(currentGuardLocation[0]-1) == 'O') {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], '^');
                                if(finishedTurning) {
                                    // Check if turn location has already been visited
                                    for (int[] turnLocation : turnLocations) {
                                        if (Arrays.equals(turnLocation, new int[]{currentGuardLocation[0], currentGuardLocation[1]})) {
                                            looping = true;
                                            break;
                                        }
                                    }
                                    turnLocations.add(new int[]{currentGuardLocation[0], currentGuardLocation[1]});
                                    finishedTurning = false;
                                }
                                direction = 0;
                            } else {
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0], 'X');
                                currentMap.get(currentGuardLocation[1]).set(currentGuardLocation[0]-1, '<');
                                currentGuardLocation[0]--;
                                finishedTurning = true;
                            }
                        }
                        break;
                }

                // Check if obstruction causes loop
                if(looping) {
                    validObstructions.add(possibleObstructions.get(currentObstruction));
                    break;
                }
            }
        }

        for(int i = 0; i < validObstructions.size(); i++) {
            for(int j = 0; j < validObstructions.size(); j++) {
                if(i != j) {
                    if(Arrays.equals(validObstructions.get(i), validObstructions.get(j))) {
                        System.out.println("Duplicate location");
                    }
                }
            }
        }

        System.out.println("Total valid obstruction locations: "+validObstructions.size());
    }

    public static boolean isInBounds(int[] currentLocation, int currentDirection, ArrayList<ArrayList<Character>> currentMap) {
        switch(currentDirection) {
            case 0:
                return currentLocation[1] - 1 >= 0;
            case 1:
                return currentLocation[0] + 1 < currentMap.getFirst().size();
            case 2:
                return currentLocation[1] + 1 < currentMap.size();
            case 3:
                return currentLocation[0] - 1 >= 0;
        }
        return false;
    }
}