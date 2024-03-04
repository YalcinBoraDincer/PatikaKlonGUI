package com.patika.dev.view;

import com.patika.dev.Helper.Config;
import com.patika.dev.Helper.Helper;
import com.patika.dev.model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class quizForSTUDENT extends JFrame {

    private JPanel wrapper;
    private JTextArea textArea1;
    private JRadioButton A;
    private JRadioButton B;
    private JRadioButton C;
    private JRadioButton D;
    private JButton sonrakiButton;
    private JButton bitirButton; // Button to finish the quiz
    private int courseID;
    private ArrayList<Quiz> quizzes;
    private int currentQuestionIndex;
    private int correctAnswersCount; // Counter for correct answers
    private int incorrectAnswersCount; // Counter for incorrect answers
    private ArrayList<String> incorrectAnswersList; // List to store incorrectly answered questions
    ButtonGroup  buttonGroup;


    public quizForSTUDENT(int courseID){
        this.courseID=courseID;
        add(wrapper);
        setSize(1000,500);
        int x= Helper.screenCenterLocation("x",getSize());
        int y=Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(A);
        buttonGroup.add(B);
        buttonGroup.add(C);
        buttonGroup.add(D);
        setVisible(true);
        textArea1.setEditable(false);
        Font font = new Font("Arial", Font.BOLD, 15);
        textArea1.setFont(font);

        loadQuizzes(courseID);
        displayQuestion();

        sonrakiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextQuestion();
            }
        });

        // Initialize the finish button
        bitirButton = new JButton("Bitir");
        bitirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishQuiz();
            }
        });
        //wrapper.add(bitirButton);
    }

    private void loadQuizzes(int courseId) {
        quizzes = Quiz.getQuizzesByCourseId(courseId);
        currentQuestionIndex = 0;
        correctAnswersCount = 0;
        incorrectAnswersCount = 0;
        incorrectAnswersList = new ArrayList<>();
    }

    private void displayQuestion() {
        if (!quizzes.isEmpty() && currentQuestionIndex < quizzes.size()) {
            Quiz currentQuiz = quizzes.get(currentQuestionIndex);
            textArea1.setText(currentQuiz.getQuestion());
            A.setText("A)"+currentQuiz.getOption_a());
            B.setText("B)"+currentQuiz.getOption_b());
            C.setText("C)"+currentQuiz.getOption_c());
            D.setText("D)"+currentQuiz.getOption_d());
            buttonGroup.clearSelection();


        } else {
            JOptionPane.showMessageDialog(this, "Quiz Bitti Tebrikler ! ");
            finishQuiz(); // Finish the quiz automatically when all questions are answered
        }
    }

    private void nextQuestion() {
        // Get the selected answer
        String selectedAnswer = getSelectedAnswer();

        // Get the current quiz
        Quiz currentQuiz = quizzes.get(currentQuestionIndex);

        // Check if the selected answer is correct
        if (selectedAnswer != null && selectedAnswer.equals(currentQuiz.getAnswer())) {
            correctAnswersCount++;
        } else {
            incorrectAnswersCount++;
            incorrectAnswersList.add(currentQuiz.getQuestion()); // Add the incorrectly answered question to the list
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    // Helper method to get the selected answer
    private String getSelectedAnswer() {
        if (A.isSelected()) {
            return "A";
        } else if (B.isSelected()) {
            return "B";
        } else if (C.isSelected()) {
            return "C";
        } else if (D.isSelected()) {
            return "D";
        }
        return null; // Return null if no answer is selected
    }

    // Method to finish the quiz
    private void finishQuiz() {
        // Display summary dialog
        JOptionPane.showMessageDialog(this, "Quiz Sonucu:\nDoğru Cevaplar: " + correctAnswersCount +
                        "\nYanlış Cevaplar: " + incorrectAnswersCount +
                        "\nYanlış Cevaplanan Sorular:\n" + String.join("\n", incorrectAnswersList),
                "Quiz Sonuçları", JOptionPane.INFORMATION_MESSAGE);

        dispose(); // Close the quiz window
    }

    public static void main(String[] args) {
        quizForSTUDENT quiz=new quizForSTUDENT(46);
    }
}
