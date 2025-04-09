import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Day27s1 {
    public static void main(String[] args) {
        MazeGame game = new MazeGame(15, 15);
        game.play();
    }
}

class MazeGame {
    private static final char WALL = '█';
    private static final char PATH = ' ';
    private static final char PLAYER = 'P';
    private static final char EXIT = 'E';
    private static final char VISITED = '.';
    private static final char TRAP = 'X';
    private static final char KEY = 'K';
    private static final char DOOR = 'D';

    private char[][] maze;
    private int width;
    private int height;
    private int playerX;
    private int playerY;
    private int exitX;
    private int exitY;
    private int keys;
    private int steps;
    private int health;
    private boolean hasKey;
    private Random random;
    private Scanner scanner;

    public MazeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new char[height][width];
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.health = 100;
        this.steps = 0;
        this.keys = 0;
        this.hasKey = false;
        initializeMaze();
    }

    private void initializeMaze() {
        // Labirenti duvarlarla doldur
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = WALL;
            }
        }

        // Labirenti oluştur
        generateMaze();

        // Oyuncuyu yerleştir
        playerX = 1;
        playerY = 1;
        maze[playerY][playerX] = PLAYER;

        // Çıkışı yerleştir
        exitX = width - 2;
        exitY = height - 2;
        maze[exitY][exitX] = EXIT;

        // Kapıyı yerleştir
        maze[height - 3][width - 2] = DOOR;

        // Anahtarı yerleştir
        int keyX, keyY;
        do {
            keyX = random.nextInt(width - 2) + 1;
            keyY = random.nextInt(height - 2) + 1;
        } while (maze[keyY][keyX] != PATH);
        maze[keyY][keyX] = KEY;

        // Tuzakları yerleştir
        for (int i = 0; i < 5; i++) {
            int trapX, trapY;
            do {
                trapX = random.nextInt(width - 2) + 1;
                trapY = random.nextInt(height - 2) + 1;
            } while (maze[trapY][trapX] != PATH);
            maze[trapY][trapX] = TRAP;
        }
    }

    private void generateMaze() {
        Stack<int[]> stack = new Stack<>();
        int startX = 1;
        int startY = 1;
        maze[startY][startX] = PATH;
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            // Komşu hücreleri kontrol et
            int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
            boolean[] visited = new boolean[4];
            boolean hasUnvisited = false;

            for (int i = 0; i < 4; i++) {
                int newX = x + directions[i][0];
                int newY = y + directions[i][1];

                if (newX > 0 && newX < width - 1 && newY > 0 && newY < height - 1 && maze[newY][newX] == WALL) {
                    hasUnvisited = true;
                    break;
                }
            }

            if (hasUnvisited) {
                int direction;
                do {
                    direction = random.nextInt(4);
                } while (visited[direction]);

                int newX = x + directions[direction][0];
                int newY = y + directions[direction][1];

                if (newX > 0 && newX < width - 1 && newY > 0 && newY < height - 1 && maze[newY][newX] == WALL) {
                    maze[y + directions[direction][1] / 2][x + directions[direction][0] / 2] = PATH;
                    maze[newY][newX] = PATH;
                    stack.push(new int[]{newX, newY});
                } else {
                    visited[direction] = true;
                }
            } else {
                stack.pop();
            }
        }
    }

    public void play() {
        System.out.println("Labirent Kaçışına Hoş Geldiniz!");
        System.out.println("Kontroller: W (Yukarı), S (Aşağı), A (Sol), D (Sağ), Q (Çıkış)");
        System.out.println("Anahtarı (K) bulun ve kapıdan (D) geçin!");
        System.out.println("Tuzaklardan (X) kaçının!");

        while (health > 0) {
            displayMaze();
            System.out.println("Adım: " + steps + " | Can: " + health + " | Anahtar: " + (hasKey ? "Var" : "Yok"));
            System.out.print("Hareket (W/S/A/D/Q): ");
            
            char move = scanner.next().toUpperCase().charAt(0);
            if (move == 'Q') {
                System.out.println("Oyundan çıkılıyor...");
                break;
            }

            if (makeMove(move)) {
                steps++;
                checkPosition();
                
                if (playerX == exitX && playerY == exitY && hasKey) {
                    System.out.println("\nTEBRİKLER! Labirentten kaçtınız!");
                    System.out.println("Toplam adım: " + steps);
                    System.out.println("Kalan can: " + health);
                    return;
                }
            }
        }

        System.out.println("\nOyun Bitti! Labirentten kaçamadınız.");
    }

    private boolean makeMove(char direction) {
        int newX = playerX;
        int newY = playerY;

        switch (direction) {
            case 'W': newY--; break;
            case 'S': newY++; break;
            case 'A': newX--; break;
            case 'D': newX++; break;
            default:
                System.out.println("Geçersiz hareket!");
                return false;
        }

        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
            if (maze[newY][newX] != WALL) {
                maze[playerY][playerX] = VISITED;
                playerX = newX;
                playerY = newY;
                maze[playerY][playerX] = PLAYER;
                return true;
            } else {
                System.out.println("Duvara çarptınız!");
            }
        }
        return false;
    }

    private void checkPosition() {
        char current = maze[playerY][playerX];
        
        if (current == TRAP) {
            health -= 20;
            System.out.println("Tuzağa düştünüz! 20 can kaybettiniz.");
            maze[playerY][playerX] = PATH;
        } else if (current == KEY) {
            hasKey = true;
            System.out.println("Anahtarı buldunuz! Artık kapıdan geçebilirsiniz.");
            maze[playerY][playerX] = PATH;
        } else if (current == DOOR) {
            if (hasKey) {
                System.out.println("Kapıyı açtınız!");
                maze[playerY][playerX] = PATH;
            } else {
                System.out.println("Kapıyı açmak için anahtar gerekli!");
                playerX = playerX > 1 ? playerX - 1 : playerX + 1;
            }
        }
    }

    private void displayMaze() {
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
} 