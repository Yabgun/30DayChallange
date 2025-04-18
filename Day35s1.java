import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Day35s1 {
    public static void main(String[] args) {
        KakuroSolver solver = new KakuroSolver();
        solver.solve();
    }
}

class KakuroSolver {
    private static final int EMPTY = 0;
    private static final int BLOCK = -1;
    private int[][] board;
    private int[][] horizontalSums;
    private int[][] verticalSums;
    private int size;
    private Scanner scanner;

    public KakuroSolver() {
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("Kakuro Çözücüye Hoş Geldiniz!");
        System.out.println("Bulmaca boyutunu girin (örn: 5):");
        size = scanner.nextInt();
        
        initializeBoard();
        getClues();
        
        System.out.println("\nGirilen Bulmaca:");
        printBoard();
        
        if (solveKakuro(0, 0)) {
            System.out.println("\nBulmaca Çözüldü!");
            printBoard();
        } else {
            System.out.println("\nBu bulmaca çözülemez!");
        }
    }

    private void initializeBoard() {
        board = new int[size][size];
        horizontalSums = new int[size][size];
        verticalSums = new int[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = EMPTY;
                horizontalSums[i][j] = 0;
                verticalSums[i][j] = 0;
            }
        }
    }

    private void getClues() {
        System.out.println("Engel bloklarını girin (1: engel, 0: boş):");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int block = scanner.nextInt();
                if (block == 1) {
                    board[i][j] = BLOCK;
                }
            }
        }

        System.out.println("Yatay toplamları girin (0: toplam yok):");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == BLOCK) {
                    horizontalSums[i][j] = scanner.nextInt();
                }
            }
        }

        System.out.println("Dikey toplamları girin (0: toplam yok):");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == BLOCK) {
                    verticalSums[i][j] = scanner.nextInt();
                }
            }
        }
    }

    private boolean solveKakuro(int row, int col) {
        if (row == size) {
            return true;
        }

        if (col == size) {
            return solveKakuro(row + 1, 0);
        }

        if (board[row][col] == BLOCK) {
            return solveKakuro(row, col + 1);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                
                if (solveKakuro(row, col + 1)) {
                    return true;
                }
                
                board[row][col] = EMPTY;
            }
        }

        return false;
    }

    private boolean isValid(int row, int col, int num) {
        // Aynı satırda tekrar var mı kontrol et
        for (int j = 0; j < size; j++) {
            if (j != col && board[row][j] == num) {
                return false;
            }
        }

        // Aynı sütunda tekrar var mı kontrol et
        for (int i = 0; i < size; i++) {
            if (i != row && board[i][col] == num) {
                return false;
            }
        }

        // Yatay toplam kontrolü
        int startCol = col;
        while (startCol > 0 && board[row][startCol - 1] != BLOCK) {
            startCol--;
        }

        int sum = 0;
        int count = 0;
        for (int j = startCol; j < size && board[row][j] != BLOCK; j++) {
            if (j == col) {
                sum += num;
            } else if (board[row][j] != EMPTY) {
                sum += board[row][j];
            }
            count++;
        }

        if (count > 0 && horizontalSums[row][startCol] != 0 && sum > horizontalSums[row][startCol]) {
            return false;
        }

        // Dikey toplam kontrolü
        int startRow = row;
        while (startRow > 0 && board[startRow - 1][col] != BLOCK) {
            startRow--;
        }

        sum = 0;
        count = 0;
        for (int i = startRow; i < size && board[i][col] != BLOCK; i++) {
            if (i == row) {
                sum += num;
            } else if (board[i][col] != EMPTY) {
                sum += board[i][col];
            }
            count++;
        }

        if (count > 0 && verticalSums[startRow][col] != 0 && sum > verticalSums[startRow][col]) {
            return false;
        }

        return true;
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == BLOCK) {
                    System.out.print("■ ");
                } else if (board[i][j] == EMPTY) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
} 