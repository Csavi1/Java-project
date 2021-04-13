import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class LeaderBoard {
    private static LinkedHashMap<String, Integer> _leaderBoardItems;
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

        ArrayList<Integer> mapValueOrder = new ArrayList<>(_leaderBoardItems.values());
        Collections.reverse(mapValueOrder);

        WriteFile(_filePath, mapValueOrder);
    }
    private static LinkedHashMap<String, Integer> ReadFile(String filePath) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
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
    private static void WriteFile(String filePath, ArrayList<Integer> order) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int itemValue : order) {
                for (String itemKey : _leaderBoardItems.keySet()) {
                    if (_leaderBoardItems.get(itemKey).equals(itemValue)) {
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
