import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Day38s1 {
    public static void main(String[] args) {
        HashiSolver solver = new HashiSolver();
        solver.solve();
    }
}

class HashiSolver {
    private static final int EMPTY = 0;
    private static final int ISLAND = 1;
    private static final int HORIZONTAL_BRIDGE = 2;
    private static final int VERTICAL_BRIDGE = 3;
    private static final int DOUBLE_HORIZONTAL_BRIDGE = 4;
    private static final int DOUBLE_VERTICAL_BRIDGE = 5;
    
    private int[][] board;
    private List<Island> islands;
    private int size;
    private Scanner scanner;

    public HashiSolver() {
        this.scanner = new Scanner(System.in);
        this.islands = new ArrayList<>();
    }

    public void solve() {
        System.out.println("Hashi Çözücüye Hoş Geldiniz!");
        System.out.println("Bulmaca boyutunu girin (örn: 5):");
        size = scanner.nextInt();
        
        initializeBoard();
        getIslands();
        
        System.out.println("\nGirilen Bulmaca:");
        printBoard();
        
        if (solveHashi(0)) {
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

    private void getIslands() {
        System.out.println("Ada sayısını girin:");
        int islandCount = scanner.nextInt();
        
        for (int i = 0; i < islandCount; i++) {
            System.out.println("Ada " + (i + 1) + " için:");
            System.out.println("Koordinatları girin (satır sütun):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            System.out.println("Bağlantı sayısını girin (1-8):");
            int connections = scanner.nextInt();
            
            islands.add(new Island(row, col, connections));
            board[row][col] = ISLAND;
        }
    }

    private boolean solveHashi(int islandIndex) {
        if (islandIndex == islands.size()) {
            return isSolved();
        }

        Island current = islands.get(islandIndex);
        if (current.connections == 0) {
            return solveHashi(islandIndex + 1);
        }

        // Yatay köprüleri dene
        for (int i = 0; i < islands.size(); i++) {
            Island other = islands.get(i);
            if (canConnectHorizontally(current, other)) {
                // Tek köprü
                if (addHorizontalBridge(current, other)) {
                    if (solveHashi(islandIndex)) {
                        return true;
                    }
                    removeHorizontalBridge(current, other);
                }
                
                // Çift köprü
                if (addDoubleHorizontalBridge(current, other)) {
                    if (solveHashi(islandIndex)) {
                        return true;
                    }
                    removeDoubleHorizontalBridge(current, other);
                }
            }
        }

        // Dikey köprüleri dene
        for (int i = 0; i < islands.size(); i++) {
            Island other = islands.get(i);
            if (canConnectVertically(current, other)) {
                // Tek köprü
                if (addVerticalBridge(current, other)) {
                    if (solveHashi(islandIndex)) {
                        return true;
                    }
                    removeVerticalBridge(current, other);
                }
                
                // Çift köprü
                if (addDoubleVerticalBridge(current, other)) {
                    if (solveHashi(islandIndex)) {
                        return true;
                    }
                    removeDoubleVerticalBridge(current, other);
                }
            }
        }

        return false;
    }

    private boolean canConnectHorizontally(Island a, Island b) {
        if (a.row != b.row) return false;
        if (a.col >= b.col) return false;
        
        for (int col = a.col + 1; col < b.col; col++) {
            if (board[a.row][col] != EMPTY) return false;
        }
        
        return true;
    }

    private boolean canConnectVertically(Island a, Island b) {
        if (a.col != b.col) return false;
        if (a.row >= b.row) return false;
        
        for (int row = a.row + 1; row < b.row; row++) {
            if (board[row][a.col] != EMPTY) return false;
        }
        
        return true;
    }

    private boolean addHorizontalBridge(Island a, Island b) {
        if (a.connections < 1 || b.connections < 1) return false;
        
        for (int col = a.col + 1; col < b.col; col++) {
            board[a.row][col] = HORIZONTAL_BRIDGE;
        }
        
        a.connections--;
        b.connections--;
        return true;
    }

    private boolean addDoubleHorizontalBridge(Island a, Island b) {
        if (a.connections < 2 || b.connections < 2) return false;
        
        for (int col = a.col + 1; col < b.col; col++) {
            board[a.row][col] = DOUBLE_HORIZONTAL_BRIDGE;
        }
        
        a.connections -= 2;
        b.connections -= 2;
        return true;
    }

    private boolean addVerticalBridge(Island a, Island b) {
        if (a.connections < 1 || b.connections < 1) return false;
        
        for (int row = a.row + 1; row < b.row; row++) {
            board[row][a.col] = VERTICAL_BRIDGE;
        }
        
        a.connections--;
        b.connections--;
        return true;
    }

    private boolean addDoubleVerticalBridge(Island a, Island b) {
        if (a.connections < 2 || b.connections < 2) return false;
        
        for (int row = a.row + 1; row < b.row; row++) {
            board[row][a.col] = DOUBLE_VERTICAL_BRIDGE;
        }
        
        a.connections -= 2;
        b.connections -= 2;
        return true;
    }

    private void removeHorizontalBridge(Island a, Island b) {
        for (int col = a.col + 1; col < b.col; col++) {
            board[a.row][col] = EMPTY;
        }
        
        a.connections++;
        b.connections++;
    }

    private void removeDoubleHorizontalBridge(Island a, Island b) {
        for (int col = a.col + 1; col < b.col; col++) {
            board[a.row][col] = EMPTY;
        }
        
        a.connections += 2;
        b.connections += 2;
    }

    private void removeVerticalBridge(Island a, Island b) {
        for (int row = a.row + 1; row < b.row; row++) {
            board[row][a.col] = EMPTY;
        }
        
        a.connections++;
        b.connections++;
    }

    private void removeDoubleVerticalBridge(Island a, Island b) {
        for (int row = a.row + 1; row < b.row; row++) {
            board[row][a.col] = EMPTY;
        }
        
        a.connections += 2;
        b.connections += 2;
    }

    private boolean isSolved() {
        // Tüm adaların bağlantıları tamamlanmış mı kontrol et
        for (Island island : islands) {
            if (island.connections != 0) {
                return false;
            }
        }
        
        // Tüm adalar birbirine bağlı mı kontrol et
        boolean[] visited = new boolean[islands.size()];
        dfs(0, visited);
        
        for (boolean v : visited) {
            if (!v) return false;
        }
        
        return true;
    }

    private void dfs(int islandIndex, boolean[] visited) {
        visited[islandIndex] = true;
        Island current = islands.get(islandIndex);
        
        for (int i = 0; i < islands.size(); i++) {
            if (!visited[i]) {
                Island other = islands.get(i);
                if (isConnected(current, other)) {
                    dfs(i, visited);
                }
            }
        }
    }

    private boolean isConnected(Island a, Island b) {
        if (a.row == b.row) {
            for (int col = Math.min(a.col, b.col) + 1; col < Math.max(a.col, b.col); col++) {
                if (board[a.row][col] != HORIZONTAL_BRIDGE && 
                    board[a.row][col] != DOUBLE_HORIZONTAL_BRIDGE) {
                    return false;
                }
            }
            return true;
        }
        
        if (a.col == b.col) {
            for (int row = Math.min(a.row, b.row) + 1; row < Math.max(a.row, b.row); row++) {
                if (board[row][a.col] != VERTICAL_BRIDGE && 
                    board[row][a.col] != DOUBLE_VERTICAL_BRIDGE) {
                    return false;
                }
            }
            return true;
        }
        
        return false;
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (board[i][j]) {
                    case EMPTY:
                        System.out.print(". ");
                        break;
                    case ISLAND:
                        System.out.print("O ");
                        break;
                    case HORIZONTAL_BRIDGE:
                        System.out.print("- ");
                        break;
                    case VERTICAL_BRIDGE:
                        System.out.print("| ");
                        break;
                    case DOUBLE_HORIZONTAL_BRIDGE:
                        System.out.print("=");
                        break;
                    case DOUBLE_VERTICAL_BRIDGE:
                        System.out.print("‖");
                        break;
                }
            }
            System.out.println();
        }
    }
}

class Island {
    int row;
    int col;
    int connections;

    public Island(int row, int col, int connections) {
        this.row = row;
        this.col = col;
        this.connections = connections;
    }
} 