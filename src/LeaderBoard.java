import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard {
    private static ArrayList<Result> _leaderBoardItems;
    private static String _filePath = "leaderboard.txt";
    
    public static void Show() {
        if (_leaderBoardItems == null)
            _leaderBoardItems = ReadFile(_filePath);

        if (_leaderBoardItems != null && _leaderBoardItems.size() > 0) {
            System.out.println("Leaderboard (TOP 5)");
            for (Result item : _leaderBoardItems) {
                System.out.format("* %s: %d tokens\n", item.Name, item.Tokens);
            }
            System.out.println();
        }
    }
    public static void Save(String name, int result) {
        Result newResult = new Result(name, result);
        boolean inList = false;

        for (int i = 0; i < _leaderBoardItems.size(); i++) {
            Result item = _leaderBoardItems.get(i);
            if (item.Name.equals(newResult.Name)) {
                if (item.Tokens > newResult.Tokens) {
                    return;
                }
                _leaderBoardItems.set(i, newResult);
                inList = true;
                break;
            }
        }

        if (!inList) {
            _leaderBoardItems.add(new Result(name, result));
        }

        WriteFile(_filePath);
    }
    private static ArrayList<Result> ReadFile(String filePath) {
        ArrayList<Result> results = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(":");
                results.add(new Result(data[0], Integer.parseInt(data[1])));
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException ex) {
            System.out.println("File not found!");
        }
        return results;
    }
    private static void WriteFile(String filePath) {
        try {
            Collections.sort(_leaderBoardItems);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            int i = 0;
            for (Result item : _leaderBoardItems) {
                writer.write(item.Name + ":" + item.Tokens + "\n");
                i++;
                if (i == 5)
                    break;
            }
            writer.close();
        }
        catch (IOException ex) {
            System.out.println("File not found!");
        }
    }
}
