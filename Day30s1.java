
import java.util.Scanner;

import SudokuSolver;

public class Day30s1 {
    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();
        solver.solve();
    }
}

class SudokuSolver {
    private static final int SIZE = 9;
    private static final int EMPTY = 0;
    private int[][] board;
    private Scanner scanner;

    public SudokuSolver() {
        this.board = new int[SIZE][SIZE];
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("Sudoku Çözücüye Hoş Geldiniz!");
        System.out.println("Lütfen Sudoku bulmacasını girin (0 boş hücreleri temsil eder):");
        
        // Sudoku bulmacasını kullanıcıdan al
        for (int i = 0; i < SIZE; i++) {
            System.out.println("Satır " + (i + 1) + " için 9 sayı girin (0-9, boşlukla ayrılmış):");
            String[] input = scanner.nextLine().split(" ");
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Integer.parseInt(input[j]);
            }
        }

        System.out.println("\nGirilen Sudoku Bulmacası:");
        printBoard();

        if (solveSudoku()) {
            System.out.println("\nSudoku Çözüldü!");
            printBoard();
        } else {
            System.out.println("\nBu Sudoku bulmacası çözülemez!");
        }
    }

    private boolean solveSudoku() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num;
                            
                            // Adım adım çözümü göster
                            System.out.println("\nAdım:");
                            printBoard();
                            
                            if (solveSudoku()) {
                                return true;
                            } else {
                                board[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int num) {
        // Satır kontrolü
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Sütun kontrolü
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // 3x3 kutu kontrolü
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void printBoard() {
        System.out.println("+-------+-------+-------+");
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("+-------+-------+-------+");
            }
            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] == EMPTY ? ". " : board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }
} 