package com.company;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Option {
    String color;
    String abr;
    int count;
    int payout;
    public Option(String color, String abr, int count, int payout) {
        this.color = color;
        this.abr = abr;
        this.count = count;
        this.payout = payout;
    }
}
public class Main {
    static List<Option> options;
    static List<Option> wheel;
    static String str_options = "";
    static int currtokens = 0;
    static String currbet = "";
    static int curramount = 0;

    public static void main(String[] args) {
        Ini(100);
        Welcome();
        GetInput();
        RollTheWheel();
    }



    private static void Ini(int tokens) {
        options = new ArrayList<Option>();
        options.add(new Option("Black", "f", 39, 2));
        options.add(new Option("Red", "p", 39, 2));
        options.add(new Option("Blue", "k", 20, 5));
        options.add(new Option("Gold", "a", 2, 50));

        wheel = new ArrayList<Option>();
        for (int i = 0; i < options.size(); i++) {
            Option li = options.get(i);
            for (int j = 0; j < li.count; j++) {
                wheel.add(li);
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
        currtokens = tokens;

    }
    private static void Welcome() {
        System.out.println("Welcome to Simple Java Roulette!\n");
        if (options != null) {
            System.out.println("Your options are:");
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                System.out.format("\t%s(%s) %d%% - Payout: ×%d\n", option.color, option.abr, option.count,option.payout);
            }
            LogTokens();
        }
    }

    private static void GetInput() {
        System.out.format("Choose: %s\n", str_options);
        Scanner reader = new Scanner(System.in);
//        reader.useDelimiter(""); // To only read one character

        boolean valid = false;
        do {
            System.out.print("\tYour bet: ");
            currbet = reader.next().toLowerCase();
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                if (currbet.equals(option.abr) || currbet.equals(option.color)) {
                    valid = true;
                    currbet = option.abr;
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
                curramount = reader.nextInt();
            } catch (Exception e) {
                reader.nextLine();
                curramount = 0;
            }
            if (curramount > 0 && currtokens - curramount >= 0)
                valid = true;
            if (!valid)
                System.out.println("\tError, try again!");
        }
        while(!valid);
        reader.nextLine();
        currtokens -= curramount;
    }
    private static int GetRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    private static void LogTokens() {
        System.out.format("\nCurrent tokens: %d\n", currtokens);
    }
    private static void RollTheWheel() {
        Option rolled = wheel.get(GetRandom(0, 99));
        System.out.format("\n\nThe wheel stopped at %s.\n", rolled.color);
        if (currbet.equals(rolled.abr)) {
            System.out.println("You have guessed it!");
            currtokens += curramount * rolled.payout;
        }
        else
            System.out.println("Better luck next time!");

        if (currtokens > 0) {
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
            String str = reader.next().toLowerCase();
            if (str.equals("y") || str.equals("yes")) {
                valid = true;
                retry = true;
            }
            else if (str.equals("n") || str.equals("no")) {
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
        else
            System.out.println("\nThanks for playing!\nYour total prize is " + currtokens + " tokens!");
    }
}
