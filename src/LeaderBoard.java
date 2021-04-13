import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LeaderBoard {
    private static HashMap<String, Integer> _leaderBoardItems;
    private static String _filePath = "leaderboard.txt";
    
    public static void Show() {
        if (_leaderBoardItems == null)
            _leaderBoardItems = ReadFile(_filePath);

        if (_leaderBoardItems != null && _leaderBoardItems.entrySet().size() > 0) {
            System.out.println("LeaderBoard");
            for (String key : _leaderBoardItems.keySet()) {
                System.out.format("* %s: %d\n", key, _leaderBoardItems.get(key));
            }
            System.out.println();
        }
    }
    public static void Save(String name, int result) {
        if (_leaderBoardItems.containsKey(name) && _leaderBoardItems.get(name) > result) {
            return;
        }
        _leaderBoardItems.put(name, result);

        ArrayList<Integer> orderedList = new ArrayList<>(_leaderBoardItems.values());
        Collections.sort(orderedList);

        WriteFile(_filePath, _leaderBoardItems, orderedList);
    }
    private static HashMap<String, Integer> ReadFile(String filePath) {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(":");
                map.put(data[0], Integer.parseInt(data[1]));
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException ex) {
            System.out.println("File not found!");
        }
        return map;
    }
    private static void WriteFile(String filePath, HashMap<String, Integer> items, ArrayList<Integer> order) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int itemValue : order) {
                for (String itemKey : items.keySet()) {
                    if (items.get(itemKey).equals(itemValue)) {
                        writer.write(itemKey + ":" + itemValue + "\n");
                        break;
                    }
                }
            }
            writer.close();
        }
        catch (IOException ex) {
            System.out.println("File not found!");
        }
    }
}
