/*
 * Class: CMSC203 
 * Instructor: Prof. Tanveer
 * Description: Grade Calculator that reads a grading configuration file and
 *              student scores, computes a weighted overall average, determines
 *              a letter grade (with optional +/- adjustment), and writes a
 *              summary report to grades_report.txt.
 * Due: MM/DD/YYYY
 * Platform/compiler: Eclipse
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or
 * any source. I have not given my code to any student.
 * Print your Name here: Andrea Ongomefen
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GradeCalculator {

    public static void main(String[] args) {

        String courseName  = "";
        int    categoryCount = 0;

        String cat1Name = ""; int cat1Weight = 0;
        String cat2Name = ""; int cat2Weight = 0;
        String cat3Name = ""; int cat3Weight = 0;

        int     totalWeight  = 0;
        boolean defaultUsed  = false;

        System.out.println("========================================");
        System.out.println("  CMSC203 Project 1 - Grade Calculator  ");
        System.out.println("========================================");
        System.out.println("Loading configuration from gradeconfig.txt ...");

        // firstly read config. file 
        try {
            Scanner configReader = new Scanner(new File("gradeconfig.txt"));

            courseName    = configReader.nextLine().trim();
            categoryCount = Integer.parseInt(configReader.nextLine().trim());

            // Each line: "CategoryName Weight"  e.g. "Projects 40"
            if (categoryCount >= 1) {
                String[] parts = configReader.nextLine().trim().split("\\s+");
                cat1Name   = parts[0];
                cat1Weight = Integer.parseInt(parts[1]);
                totalWeight += cat1Weight;
            }
            if (categoryCount >= 2) {
                String[] parts = configReader.nextLine().trim().split("\\s+");
                cat2Name   = parts[0];
                cat2Weight = Integer.parseInt(parts[1]);
                totalWeight += cat2Weight;
            }
            if (categoryCount >= 3) {
                String[] parts = configReader.nextLine().trim().split("\\s+");
                cat3Name   = parts[0];
                cat3Weight = Integer.parseInt(parts[1]);
                totalWeight += cat3Weight;
            }

            configReader.close();

            if (totalWeight != 100) {
                System.out.println("Warning: weights do not sum to 100. Using default configuration.");
                defaultUsed = true;
            } else {
                System.out.println("Configuration loaded successfully.");
                System.out.println("Total weight: " + totalWeight + "%");
            }

        } catch (FileNotFoundException e) {
            System.out.println("gradeconfig.txt not found. Using default configuration.");
            defaultUsed = true;
        } catch (Exception e) {
            System.out.println("Error reading config file. Using default configuration.");
            defaultUsed = true;
        }

        // apply defaults if needed
        if (defaultUsed) {
            courseName    = "Default Course";
            categoryCount = 3;
            cat1Name = "Projects"; cat1Weight = 40;
            cat2Name = "Quizzes";  cat2Weight = 30;
            cat3Name = "Exams";    cat3Weight = 30;
            totalWeight = 100;
            System.out.println("Default configuration applied: Projects 40%, Quizzes 30%, Exams 30%.");
        }

        System.out.println("Using input file:  grades_input.txt");
        System.out.println("Using output file: grades_report.txt");
        System.out.println("Reading student scores...");

        //  read student scores
        String firstName = "";
        String lastName  = "";

        double avg1 = 0, avg2 = 0, avg3 = 0;

        try {
            Scanner inputReader = new Scanner(new File("grades_input.txt"));

            firstName = inputReader.nextLine().trim();
            lastName  = inputReader.nextLine().trim();

            // Loop over the three categories
            int catNum = 0;
            while (catNum < categoryCount && inputReader.hasNextLine()) {
                catNum++;

                String categoryName = inputReader.nextLine().trim();
                int scoreCount = Integer.parseInt(inputReader.nextLine().trim());

                double sum = 0;
                for (int i = 0; i < scoreCount; i++) {
                    sum += inputReader.nextDouble();
                }
                if (inputReader.hasNextLine()) inputReader.nextLine(); // consume newline

                double avg = sum / scoreCount;

                // Validate category name matches config, assign to correct slot
                if (catNum == 1) {
                    if (!categoryName.equalsIgnoreCase(cat1Name)) {
                        System.out.println("Warning: expected '" + cat1Name +
                                           "' but found '" + categoryName + "'. Using anyway.");
                    }
                    avg1 = avg;
                } else if (catNum == 2) {
                    if (!categoryName.equalsIgnoreCase(cat2Name)) {
                        System.out.println("Warning: expected '" + cat2Name +
                                           "' but found '" + categoryName + "'. Using anyway.");
                    }
                    avg2 = avg;
                } else if (catNum == 3) {
                    if (!categoryName.equalsIgnoreCase(cat3Name)) {
                        System.out.println("Warning: expected '" + cat3Name +
                                           "' but found '" + categoryName + "'. Using anyway.");
                    }
                    avg3 = avg;
                }
            }

            inputReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: grades_input.txt not found. Program exiting.");
            return;
        } catch (Exception e) {
            System.out.println("Error reading student file: " + e.getMessage());
            return;
        }

        // compute overall weighted average 
        double overallAverage = (avg1 * cat1Weight / 100.0)
                              + (avg2 * cat2Weight / 100.0)
                              + (avg3 * cat3Weight / 100.0);

        // base letter grade
        String baseGrade;
        if      (overallAverage >= 90) baseGrade = "A";
        else if (overallAverage >= 80) baseGrade = "B";
        else if (overallAverage >= 70) baseGrade = "C";
        else if (overallAverage >= 60) baseGrade = "D";
        else                           baseGrade = "F";

        // keyboard input ask apply +/- grading?
        Scanner keyboard = new Scanner(System.in);
        String choice;

        do {
            System.out.print("Apply +/- grading? (Y/N): ");
            choice = keyboard.nextLine().trim();
        } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));

        String finalGrade = baseGrade;

        if (choice.equalsIgnoreCase("Y")) {
            double decimal = overallAverage % 10; // position within the 10-point band
            if      (decimal >= 7.0) finalGrade = baseGrade + "+";
            else if (decimal <  3.0) finalGrade = baseGrade + "-";
            // middle range keeps base grade
        }

        // console output 
        System.out.println();
        System.out.println("Student: " + firstName + " " + lastName);
        System.out.println("Course:  " + courseName);
        System.out.println();
        System.out.println("Category Results:");
        System.out.printf("  %-12s (%d%%): average = %.2f%n", cat1Name, cat1Weight, avg1);
        System.out.printf("  %-12s (%d%%): average = %.2f%n", cat2Name, cat2Weight, avg2);
        System.out.printf("  %-12s (%d%%): average = %.2f%n", cat3Name, cat3Weight, avg3);
        System.out.println();
        System.out.printf("Overall numeric average: %.2f%n", overallAverage);
        System.out.println("Base letter grade:  " + baseGrade);
        System.out.println("Final letter grade: " + finalGrade);
        System.out.println(defaultUsed ? "Default configuration was used."
                                       : "Custom configuration loaded.");

        // write summary to grades_report.txt 
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("grades_report.txt"));

            writer.println("========================================");
            writer.println("  CMSC203 Project 1 - Grade Calculator  ");
            writer.println("========================================");
            writer.println("Student: " + firstName + " " + lastName);
            writer.println("Course:  " + courseName);
            writer.println();
            writer.println("Category Results:");
            writer.printf("  %-12s (%d%%): average = %.2f%n", cat1Name, cat1Weight, avg1);
            writer.printf("  %-12s (%d%%): average = %.2f%n", cat2Name, cat2Weight, avg2);
            writer.printf("  %-12s (%d%%): average = %.2f%n", cat3Name, cat3Weight, avg3);
            writer.println();
            writer.printf("Overall numeric average: %.2f%n", overallAverage);
            writer.println("Base letter grade:  " + baseGrade);
            writer.println("Final letter grade: " + finalGrade);
            writer.println(defaultUsed ? "Default configuration was used."
                                       : "Custom configuration loaded.");

            writer.close();
            System.out.println("\nSummary written to grades_report.txt");

        } catch (IOException e) {
            System.out.println("Warning: could not write grades_report.txt — " + e.getMessage());
        }

        keyboard.close();
        System.out.println("Program complete. Goodbye!");
    }
}