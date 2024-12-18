import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayFive {
    public static void main(String[] args) {
        // List of page rules
        ArrayList<ArrayList<Integer>> pageRules = new ArrayList<>();

        //List of pages that have rules
        ArrayList<Integer> hasRules = new ArrayList<>();

        // List of current page orderings
        ArrayList<ArrayList<Integer>> pageOrdering = new ArrayList<>();

        // Get values from input.txt
        try {
            // Read file
            List<String> lines = Files.readAllLines(Paths.get("src/input.txt"));
            boolean finishedRules = false;

            // Read results of file line by line
            for(String line: lines) {
                // Switch input reading method
                if(line.isEmpty())
                    finishedRules = true;
                else {
                    // Populate lists with file values
                    if (!finishedRules) {
                        String[] pagesStr = line.split("\\|");
                        ArrayList<Integer> rule = new ArrayList<>();
                        rule.add(Integer.parseInt(pagesStr[0]));
                        rule.add(Integer.parseInt(pagesStr[1]));
                        pageRules.add(rule);
                        hasRules.add(Integer.parseInt(pagesStr[1]));
                    } else {
                        String[] pagesStr = line.split(",");
                        ArrayList<Integer> pages = new ArrayList<>();
                        for (String page: pagesStr) {
                            pages.add(Integer.parseInt(page));
                        }
                        pageOrdering.add(pages);
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("File not found");
        }

        // Page lists that are ordered correctly
        ArrayList<ArrayList<Integer>> correctlyOrdered = new ArrayList<>();

        // Page lists that are ordered incorrectly
        ArrayList<ArrayList<Integer>> incorrectlyOrdered = new ArrayList<>();

        // Check all page lists for correct order
        for(ArrayList<Integer> pages: pageOrdering) {
            boolean followsAllRules = true;
            ArrayList<ArrayList<Integer>> currentRules = new ArrayList<>();
            // Check which rules should be active
            for(int page: pages) {
                for(ArrayList<Integer> rule: pageRules) {
                    if(pages.contains(rule.get(0)) && pages.contains(rule.get(1)) && !currentRules.contains(rule)) {
                        currentRules.add(rule);
                    }
                }
            }
            // Check all currently active rules
            for(ArrayList<Integer> rule: currentRules) {
                // Compare indices of pages in rule
                if(!(pages.indexOf(rule.get(0)) < pages.indexOf(rule.get(1)))) {
                    followsAllRules = false;
                    break;
                }
            }
            if(followsAllRules)
                correctlyOrdered.add(pages);
            else
                incorrectlyOrdered.add(pages);
        }

        // Sum of middle pages of correctly ordered page lists
        int middleTotal = 0;

        // Find middle page value for each correctly ordered page list
        for(ArrayList<Integer> pages: correctlyOrdered) {
            int middlePage = (pages.size() / 2);
            middleTotal += pages.get(middlePage);
        }

        System.out.println("Sum of middle pages of correctly ordered page lists: "+middleTotal);

        // Fix incorrectly ordered pages
        for(ArrayList<Integer> pages: incorrectlyOrdered) {
            ArrayList<ArrayList<Integer>> currentRules = new ArrayList<>();
            // Check which rules should be active
            for(int page: pages) {
                for(ArrayList<Integer> rule: pageRules) {
                    if(pages.contains(rule.get(0)) && pages.contains(rule.get(1)) && !currentRules.contains(rule)) {
                        currentRules.add(rule);
                    }
                }
            }
            boolean followsAllRules = false;
            while(!followsAllRules) {
                int brokenRulesNum = 0;
                // Check all currently active rules
                for(ArrayList<Integer> rule: currentRules) {
                    // Compare indices of pages in rule
                    if(!(pages.indexOf(rule.get(0)) < pages.indexOf(rule.get(1)))) {
                        // Swap indices of rule breaking pages
                        int moveNum = rule.get(0);
                        int moveNumLoc = pages.indexOf(rule.get(1));
                        pages.set(pages.indexOf(rule.get(0)), rule.get(1));
                        pages.set(moveNumLoc, moveNum);
                        brokenRulesNum++;
                        break;
                    }
                }
                // Check if any rules have been broken
                if(brokenRulesNum == 0)
                    followsAllRules = true;

            }
        }

        // Sum of middle pages of newly correctly ordered page lists
        int middleFixedTotal = 0;

        // Find middle page value for each newly correctly ordered page list
        for(ArrayList<Integer> pages: incorrectlyOrdered) {
            int middlePage = (pages.size() / 2);
            middleFixedTotal += pages.get(middlePage);
        }

        System.out.println("Sum of middle pages of newly correctly ordered page lists: "+middleFixedTotal);
    }
}