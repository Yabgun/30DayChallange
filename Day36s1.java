import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Day36s1 {
    public static void main(String[] args) {
        NonogramSolver solver = new NonogramSolver();
        solver.solve();
    }
}

class NonogramSolver {
    private static final int EMPTY = 0;
    private static final int FILLED = 1;
    private static final int UNKNOWN = -1;
    private int[][] board;
    private List<List<Integer>>[] rowClues;
    private List<List<Integer>>[] colClues;
    private int size;
    private Scanner scanner;

    public NonogramSolver() {
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("Nonogram Çözücüye Hoş Geldiniz!");
        System.out.println("Bulmaca boyutunu girin (örn: 5):");
        size = scanner.nextInt();
        
        initializeBoard();
        getClues();
        
        System.out.println("\nGirilen Bulmaca:");
        printBoard();
        
        if (solveNonogram(0, 0)) {
            System.out.println("\nBulmaca Çözüldü!");
            printBoard();
        } else {
            System.out.println("\nBu bulmaca çözülemez!");
        }
    }

    private void initializeBoard() {
        board = new int[size][size];
        rowClues = new ArrayList[size];
        colClues = new ArrayList[size];
        
        for (int i = 0; i < size; i++) {
            rowClues[i] = new ArrayList<>();
            colClues[i] = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                board[i][j] = UNKNOWN;
            }
        }
    }

    private void getClues() {
        System.out.println("Satır ipuçlarını girin (her satır için sayıları boşlukla ayırarak, bitirmek için -1):");
        for (int i = 0; i < size; i++) {
            System.out.println("Satır " + (i + 1) + " ipuçları:");
            while (true) {
                int clue = scanner.nextInt();
                if (clue == -1) break;
                rowClues[i].add(clue);
            }
        }

        System.out.println("Sütun ipuçlarını girin (her sütun için sayıları boşlukla ayırarak, bitirmek için -1):");
        for (int i = 0; i < size; i++) {
            System.out.println("Sütun " + (i + 1) + " ipuçları:");
            while (true) {
                int clue = scanner.nextInt();
                if (clue == -1) break;
                colClues[i].add(clue);
            }
        }
    }

    private boolean solveNonogram(int row, int col) {
        if (row == size) {
            return true;
        }

        if (col == size) {
            return solveNonogram(row + 1, 0);
        }

        // Hücreyi doldur
        board[row][col] = FILLED;
        if (isValid(row, col) && solveNonogram(row, col + 1)) {
            return true;
        }

        // Hücreyi boş bırak
        board[row][col] = EMPTY;
        if (isValid(row, col) && solveNonogram(row, col + 1)) {
            return true;
        }

        board[row][col] = UNKNOWN;
        return false;
    }

    private boolean isValid(int row, int col) {
        return isValidRow(row) && isValidColumn(col);
    }

    private boolean isValidRow(int row) {
        List<Integer> currentBlocks = new ArrayList<>();
        int currentBlock = 0;

        for (int j = 0; j < size; j++) {
            if (board[row][j] == FILLED) {
                currentBlock++;
            } else if (currentBlock > 0) {
                currentBlocks.add(currentBlock);
                currentBlock = 0;
            }
        }

        if (currentBlock > 0) {
            currentBlocks.add(currentBlock);
        }

        return currentBlocks.equals(rowClues[row]);
    }

    private boolean isValidColumn(int col) {
        List<Integer> currentBlocks = new ArrayList<>();
        int currentBlock = 0;

        for (int i = 0; i < size; i++) {
            if (board[i][col] == FILLED) {
                currentBlock++;
            } else if (currentBlock > 0) {
                currentBlocks.add(currentBlock);
                currentBlock = 0;
            }
        }

        if (currentBlock > 0) {
            currentBlocks.add(currentBlock);
        }

        return currentBlocks.equals(colClues[col]);
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == FILLED) {
                    System.out.print("■ ");
                } else if (board[i][j] == EMPTY) {
                    System.out.print(". ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }
} 