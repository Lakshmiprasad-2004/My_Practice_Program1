package com.java.Practice;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class QuizGame {

    private Model model;
    private Scanner sc;

    private int score = 0;
    private int reward = 0;
    private boolean lifeLineUsed = false;

    public QuizGame(Model model, Scanner sc) {
        this.model = model;
        this.sc = sc;
    }

    public void startQuiz() {

        System.out.print("Enter Player Name: ");
        String name = sc.nextLine();

        ArrayList<Question> questions = model.viewAllQuestions();

        if (questions.isEmpty()) {
            System.out.println("No questions found in database.");
            return;
        }

        System.out.println("\n===== QUIZ GAME STARTED =====");

        for (Question q : questions) {

            System.out.println("\n" + q.getQuestion());

            System.out.println("1. " + q.getOption1());
            System.out.println("2. " + q.getOption2());
            System.out.println("3. " + q.getOption3());
            System.out.println("4. " + q.getOption4());

            System.out.println("5. Use 50-50 Lifeline");
            System.out.println("6. Use Audience Poll");

            System.out.print("Enter your answer: ");
            int answer = sc.nextInt();

            if (answer == 5) {

                if (!lifeLineUsed) {

                    lifeLineUsed = true;

                    System.out.println("50-50 Lifeline:");

                    int correct = q.getCorrectAnswer();

                    for (int i = 1; i <= 4; i++) {

                        if (i == correct) {
                            System.out.println(i + ". " + getOption(q, i));
                        }
                    }

                    int wrongOption = correct == 1 ? 2 : 1;

                    System.out.println(wrongOption + ". "
                            + getOption(q, wrongOption));

                    System.out.print("Enter your answer: ");
                    answer = sc.nextInt();

                } else {
                    System.out.println("Lifeline already used!");
                    System.out.print("Enter your answer: ");
                    answer = sc.nextInt();
                }
            }

            if (answer == 6) {

                System.out.println("\n===== AUDIENCE POLL =====");

                int correct = q.getCorrectAnswer();

                Random random = new Random();

                int correctPercentage = 50 + random.nextInt(31);

                int remaining = 100 - correctPercentage;

                int wrong1 = remaining / 3;
                int wrong2 = remaining / 3;
                int wrong3 = remaining - wrong1 - wrong2;

                int[] percentages = new int[4];

                percentages[correct - 1] = correctPercentage;

                int index = 0;

                for (int i = 0; i < 4; i++) {

                    if (i != correct - 1) {

                        if (index == 0)
                            percentages[i] = wrong1;
                        else if (index == 1)
                            percentages[i] = wrong2;
                        else
                            percentages[i] = wrong3;

                        index++;
                    }
                }

                for (int i = 0; i < 4; i++) {
                    System.out.println((i + 1) + ". "
                            + percentages[i] + "%");
                }

                System.out.println("Audience Poll Completed!");

                System.out.print("Enter your answer: ");
                answer = sc.nextInt();
            }

            if (answer == q.getCorrectAnswer()) {

                score++;
                reward += 100;

                System.out.println("Correct Answer!");
                System.out.println("Reward: Rs." + reward);

            } else {

                System.out.println("Wrong Answer!");
                System.out.println("Correct Answer: "
                        + q.getCorrectAnswer());

                break;
            }
        }

        System.out.println("\n===== QUIZ RESULT =====");
        System.out.println("Player Name: " + name);
        System.out.println("Correct Answers: " + score);
        System.out.println("Total Reward: Rs." + reward);

        model.saveResult(name, score, reward, lifeLineUsed);
    }

    private String getOption(Question q, int option) {

        switch (option) {

        case 1:
            return q.getOption1();

        case 2:
            return q.getOption2();

        case 3:
            return q.getOption3();

        case 4:
            return q.getOption4();

        default:
            return "";
        }
    }
}
