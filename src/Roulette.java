import java.util.ArrayList;
import java.util.Scanner;



public class Roulette {
    static ArrayList<Option> options;
    static ArrayList<Option> wheel;
    static int count_sum = -1;
    static String str_options = "";
    static int tokens = 0;
    static String current_bet = "";
    static int bet_amount = 0;


    public static void main(String[] args) {
        Ini();
        Welcome();
        GetInput();
        RollTheWheel();
    }

    //    Adjust options in the wheel: add, remove or change
    private static void Fill() {
        tokens = 100;

        options.add(new Option("Black", "f", 39, 2));
        options.add(new Option("Red", "p", 39, 2));
        options.add(new Option("Blue", "k", 20, 5));
        options.add(new Option("Gold", "a", 2, 50));
    }


    private static void Ini() {
        options = new ArrayList<>();
        Fill();
        wheel = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            Option li = options.get(i);
            for (int j = 0; j < li.count; j++) {
                wheel.add(li);
                count_sum++;
            }
//            String str = String.format("%s(%s)", li.color, li.abr);
            String str = li.abr;
            if(i < options.size()-2)
                str_options += str + ", ";
            else if (i == options.size()-2)
                str_options += str + " or ";
            else
                str_options += str;
        }
    }
    private static void Welcome() {
        System.out.println("Welcome to Simple Java Roulette!\n");
        LeaderBoard.Show();
        if (options != null) {
            System.out.println("Your options are:");
            options.forEach(option -> System.out.format("\t%s(%s) %d%% - Payout: ×%d\n", option.color, option.abr, option.count, option.payout));
            LogTokens();
        }
    }
    private static void GetInput() {
        System.out.format("Choose: %s\n", str_options);
        Scanner reader = new Scanner(System.in);

        boolean valid = false;
        do {
            System.out.print("\tYour bet: ");
            current_bet = reader.next();
            for (Option option : options) {
                if (current_bet.equalsIgnoreCase(option.abr) || current_bet.equalsIgnoreCase(option.color)) {
                    valid = true;
                    current_bet = option.abr;
                    break;
                }
            }
            if (!valid)
                System.out.println("\tError, try again!");
        }
        while(!valid);
        reader.nextLine();

        valid = false;
        do {
            System.out.print("\tBet amount: ");
            try {
                bet_amount = reader.nextInt();
            } catch (Exception e) {
                reader.nextLine();
                bet_amount = 0;
            }
            if (bet_amount > 0 && tokens >= bet_amount)
                valid = true;
            if (!valid)
                System.out.println("\tError, try again!");
        }
        while(!valid);
        reader.nextLine();
        tokens -= bet_amount;
    }
    private static void RollTheWheel() {
        Option rolled = wheel.get(Roll(count_sum));
        System.out.format("\n\nThe wheel stopped at %s.\n", rolled.color);
        if (current_bet.equals(rolled.abr)) {
            System.out.format("You have guessed it! +%d\n", bet_amount * rolled.payout - bet_amount);
            tokens += bet_amount * rolled.payout;
        }
        else
            System.out.println("Better luck next time!");

        if (tokens > 0) {
            LogTokens();
            Retry();
        }
        else
            System.out.println("You have run out of tokens! Bye!");
    }
    private static void Retry() {
        boolean retry = false;
        Scanner reader = new Scanner(System.in);

        boolean valid = false;
        do {
            System.out.println("Continue playing?\n[Y]es / [N]o");
            String str = reader.next();
            if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")) {
                valid = true;
                retry = true;
            }
            else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")) {
                valid = true;
                retry = false;
            }
            if (!valid)
                System.out.println("Error, try again!");
        }
        while (!valid);
        reader.nextLine();

        if (retry) {
            GetInput();
            RollTheWheel();
        }
        else {
            System.out.println("\nThanks for playing!\nYour total prize is " + tokens + " tokens!");
            System.out.println("Please enter your name!");
            LeaderBoard.Save(reader.nextLine(), tokens);
        }
    }
    private static void LogTokens() {
        System.out.format("\nCurrent tokens: %d\n", tokens);
    }
    private static int Roll(int wheel_count) {
        int min = 0;
        return (int) (Math.random() * (wheel_count - min + 1)) + min;
    }
}
