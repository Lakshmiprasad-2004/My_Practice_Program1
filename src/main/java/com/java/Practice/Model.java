
package com.java.Practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Model {

    public Connection getConnection() throws SQLException {

        System.out.println("Connecting to: " + Credentials.url);

        return DriverManager.getConnection(
                Credentials.url,
                Credentials.user,
                Credentials.pwd
        );
    }

    public ArrayList<Question> viewAllQuestions() {

        ArrayList<Question> questions = new ArrayList<>();

        String query = "SELECT * FROM questions ORDER BY id";

        try (
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getInt("correct_answer")
                );

                questions.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public void saveResult(String name, int score,
            int reward, boolean lifeLineUsed) {

        String query = "INSERT INTO quiz_results "
                + "(player_name, score, reward, lifeline_used) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, name);
            ps.setInt(2, score);
            ps.setInt(3, reward);
            ps.setBoolean(4, lifeLineUsed);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Result saved successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
