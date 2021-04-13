import java.util.ArrayList;
import java.util.Map;

public class LeaderBoard {
    private static Map<String, Integer> _leaderBoardItems;
    public static void Get() {
        if (_leaderBoardItems == null)
            _leaderBoardItems =
        System.out.println("LeaderBoard");
        for (var item : _leaderBoardItems.keySet()) {
            int leftPadding = 0, rightPadding = 0, spaceForContent = 9;
            System.out.println("-----------");
            spaceForContent -= String.valueOf(item).length();
            rightPadding = (spaceForContent / 2);
            leftPadding = spaceForContent - rightPadding;
            System.out.print("|");
            for (int i = 0; i < leftPadding; i++) {
                System.out.print(" ");
            }
            System.out.print(item);
            for (int i = 0; i < rightPadding; i++) {
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println("-----------");
        }
        System.out.println("-----------");
        System.out.println("|         |");
        System.out.println("-----------");
    }
    public static void Set() {

    }
    public static void Save() {

    }
    private static Map<String, Integer> ReadFile() {

    }
    private static void WriteFile() {

    }
}
