
package com.java.Practice;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Model model = new Model();

        QuizGame game = new QuizGame(model, sc);

        game.startQuiz();

        sc.close();
    }
}
