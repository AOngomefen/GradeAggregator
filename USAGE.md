# Usage Guide for Grade Aggregator

This guide provides a detailed step-by-step process on how to use the Grade Aggregator program.

## Step 1: Prepare Configuration File
- Create a configuration file named `config.json` that includes necessary settings for the program.
  - Example:
    ```json
    {
      "semester": "Spring 2026",
      "course_code": "CS101"
    }
    ```

## Step 2: Prepare Student Data
- Gather all student grades in a CSV file named `students.csv`.
  - Example:
    ```csv
    StudentID,Name,Grade
    001,John Doe,A
    002,Jane Smith,B+
    ```

## Step 3: Run the Program
- Open a terminal and navigate to the directory containing the program files.
- Execute the program with the following command:
  ```bash
  python grade_aggregator.py
  ```

## Step 4: Follow Prompts
- The program will prompt you to provide the path to the configuration file and the student data file.
- Follow the instructions displayed on the screen.

## Step 5: Review Output
- Once the program has finished executing, it will generate an output file named `output.txt`.
- Review this file for the aggregated grades for each student.

## Example Input and Output
- **Input**:
  ```
  StudentID,Name,Grade
  001,John Doe,A
  002,Jane Smith,B+
  ```
- **Output**:
  ```
  John Doe: A
  Jane Smith: B+
  ```

## Grading Scale Reference Table with Plus/Minus Modifiers
| Letter | Grade | Points |
|--------|-------|--------|
| A+     | 90-100| 4.0    |
| A      | 85-89 | 4.0    |
| A-     | 80-84 | 3.7    |
| B+     | 75-79 | 3.3    |
| B      | 70-74 | 3.0    |
| B-     | 65-69 | 2.7    |
| C+     | 60-64 | 2.3    |
| C      | 55-59 | 2.0    |
| C-     | 50-54 | 1.7    |
| D+     | 45-49 | 1.3    |
| D      | 40-44 | 1.0    |
| F      | 0-39  | 0.0    |

## Troubleshooting Section
- **Issue**: Program fails to run.  
  **Solution**: Ensure Python is installed and the script is executed in the correct directory.

- **Issue**: Incorrect grade aggregation.  
  **Solution**: Check that the input CSV file is properly formatted.