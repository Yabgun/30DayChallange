import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Day32s1 {
    public static void main(String[] args) {
        MazeSolver solver = new MazeSolver();
        solver.solve();
    }
}

class MazeSolver {
    private static final char WALL = '■';
    private static final char PATH = '·';
    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SOLUTION = '★';
    
    private char[][] maze;
    private int rows;
    private int cols;
    private int startX, startY;
    private int endX, endY;
    private Scanner scanner;

    public MazeSolver() {
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("Labirent Çözücüye Hoş Geldiniz!");
        System.out.println("Labirent boyutlarını girin (satır sütun):");
        rows = scanner.nextInt();
        cols = scanner.nextInt();
        scanner.nextLine(); // Yeni satır karakterini temizle

        maze = new char[rows][cols];
        System.out.println("Labirenti girin (■: duvar, ·: yol, S: başlangıç, E: bitiş):");

        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < cols; j++) {
                maze[i][j] = line.charAt(j);
                if (maze[i][j] == START) {
                    startX = i;
                    startY = j;
                } else if (maze[i][j] == END) {
                    endX = i;
                    endY = j;
                }
            }
        }

        System.out.println("\nGirilen Labirent:");
        printMaze();

        if (findPath()) {
            System.out.println("\nLabirent Çözüldü!");
            printMaze();
        } else {
            System.out.println("\nBu labirent çözülemez!");
        }
    }

    private boolean findPath() {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        int[][] parentX = new int[rows][cols];
        int[][] parentY = new int[rows][cols];

        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        int[] dx = {-1, 1, 0, 0}; // Yukarı, aşağı, sol, sağ
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                // Yolu işaretle
                while (x != startX || y != startY) {
                    int tempX = parentX[x][y];
                    int tempY = parentY[x][y];
                    if (maze[x][y] != START && maze[x][y] != END) {
                        maze[x][y] = SOLUTION;
                    }
                    x = tempX;
                    y = tempY;
                }
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (isValid(newX, newY) && !visited[newX][newY] && maze[newX][newY] != WALL) {
                    queue.add(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    parentX[newX][newY] = x;
                    parentY[newX][newY] = y;
                }
            }
        }

        return false;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private void printMaze() {
        System.out.println();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }
} 