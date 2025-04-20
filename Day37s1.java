import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Day37s1 {
    public static void main(String[] args) {
        KenKenSolver solver = new KenKenSolver();
        solver.solve();
    }
}

class KenKenSolver {
    private static final int EMPTY = 0;
    private int[][] board;
    private List<Cage> cages;
    private int size;
    private Scanner scanner;

    public KenKenSolver() {
        this.scanner = new Scanner(System.in);
        this.cages = new ArrayList<>();
    }

    public void solve() {
        System.out.println("KenKen Çözücüye Hoş Geldiniz!");
        System.out.println("Bulmaca boyutunu girin (örn: 4):");
        size = scanner.nextInt();
        
        initializeBoard();
        getCages();
        
        System.out.println("\nGirilen Bulmaca:");
        printBoard();
        
        if (solveKenKen(0, 0)) {
            System.out.println("\nBulmaca Çözüldü!");
            printBoard();
        } else {
            System.out.println("\nBu bulmaca çözülemez!");
        }
    }

    private void initializeBoard() {
        board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void getCages() {
        System.out.println("Kafes sayısını girin:");
        int cageCount = scanner.nextInt();
        
        for (int i = 0; i < cageCount; i++) {
            System.out.println("Kafes " + (i + 1) + " için:");
            System.out.println("Hedef değeri girin:");
            int target = scanner.nextInt();
            System.out.println("İşlem türünü girin (+, -, *, /):");
            char operation = scanner.next().charAt(0);
            
            List<Cell> cells = new ArrayList<>();
            System.out.println("Kafesteki hücre sayısını girin:");
            int cellCount = scanner.nextInt();
            
            for (int j = 0; j < cellCount; j++) {
                System.out.println("Hücre " + (j + 1) + " koordinatlarını girin (satır sütun):");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;
                cells.add(new Cell(row, col));
            }
            
            cages.add(new Cage(target, operation, cells));
        }
    }

    private boolean solveKenKen(int row, int col) {
        if (row == size) {
            return true;
        }

        if (col == size) {
            return solveKenKen(row + 1, 0);
        }

        for (int num = 1; num <= size; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                
                if (solveKenKen(row, col + 1)) {
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

        // Kafes kurallarını kontrol et
        for (Cage cage : cages) {
            if (cage.contains(row, col)) {
                if (!cage.isValid(board)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == EMPTY) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

class Cell {
    int row;
    int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Cage {
    int target;
    char operation;
    List<Cell> cells;

    public Cage(int target, char operation, List<Cell> cells) {
        this.target = target;
        this.operation = operation;
        this.cells = cells;
    }

    public boolean contains(int row, int col) {
        for (Cell cell : cells) {
            if (cell.row == row && cell.col == col) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid(int[][] board) {
        List<Integer> values = new ArrayList<>();
        for (Cell cell : cells) {
            if (board[cell.row][cell.col] == 0) {
                return true; // Henüz doldurulmamış hücre var
            }
            values.add(board[cell.row][cell.col]);
        }

        switch (operation) {
            case '+':
                int sum = 0;
                for (int value : values) {
                    sum += value;
                }
                return sum == target;
            case '-':
                return Math.abs(values.get(0) - values.get(1)) == target;
            case '*':
                int product = 1;
                for (int value : values) {
                    product *= value;
                }
                return product == target;
            case '/':
                return (values.get(0) / values.get(1) == target) || 
                       (values.get(1) / values.get(0) == target);
            default:
                return false;
        }
    }
} 