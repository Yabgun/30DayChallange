import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Day26s1 {
    public static void main(String[] args) {
        SpaceBattle game = new SpaceBattle();
        game.play();
    }
}

class SpaceBattle {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private char[][] space;
    private int playerX;
    private int playerY;
    private List<Enemy> enemies;
    private List<PowerUp> powerUps;
    private int score;
    private int health;
    private int shield;
    private int level;
    private Random random;
    private Scanner scanner;
    private boolean gameOver;

    public SpaceBattle() {
        space = new char[HEIGHT][WIDTH];
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
        random = new Random();
        scanner = new Scanner(System.in);
        initializeGame();
    }

    private void initializeGame() {
        // Uzayı temizle
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                space[i][j] = ' ';
            }
        }

        // Oyuncuyu yerleştir
        playerX = WIDTH / 2;
        playerY = HEIGHT - 2;
        space[playerY][playerX] = 'A';

        // Oyun değişkenlerini başlat
        score = 0;
        health = 100;
        shield = 50;
        level = 1;
        gameOver = false;

        // İlk düşmanları oluştur
        spawnEnemies();
    }

    private void spawnEnemies() {
        int enemyCount = 3 + level;
        for (int i = 0; i < enemyCount; i++) {
            int x = random.nextInt(WIDTH);
            enemies.add(new Enemy(x, 0));
        }

        // Güç artırıcıları oluştur
        if (random.nextInt(100) < 30) { // %30 şans
            int x = random.nextInt(WIDTH);
            powerUps.add(new PowerUp(x, 0));
        }
    }

    public void play() {
        System.out.println("Uzay Savaşına Hoş Geldiniz!");
        System.out.println("Kontroller: A (Sol), D (Sağ), S (Ateş), Q (Çıkış)");
        System.out.println("Düşmanlardan kaçının ve puan kazanın!");
        System.out.println("Güç artırıcıları (P) toplayarak kalkanınızı güçlendirin.");

        while (!gameOver) {
            updateGame();
            displayGame();
            System.out.println("Seviye: " + level + " | Puan: " + score + " | Can: " + health + " | Kalkan: " + shield);
            System.out.print("Hareket (A/D/S/Q): ");
            
            char move = scanner.next().toUpperCase().charAt(0);
            if (move == 'Q') {
                System.out.println("Oyundan çıkılıyor...");
                break;
            }

            processMove(move);
            checkCollisions();
            moveEnemies();
            checkLevelUp();
        }

        System.out.println("\nOyun Bitti!");
        System.out.println("Final Puanı: " + score);
        System.out.println("Ulaşılan Seviye: " + level);
    }

    private void updateGame() {
        // Uzayı temizle
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                space[i][j] = ' ';
            }
        }

        // Oyuncuyu yerleştir
        space[playerY][playerX] = 'A';

        // Düşmanları yerleştir
        for (Enemy enemy : enemies) {
            if (enemy.y < HEIGHT && enemy.y >= 0) {
                space[enemy.y][enemy.x] = 'V';
            }
        }

        // Güç artırıcılarını yerleştir
        for (PowerUp powerUp : powerUps) {
            if (powerUp.y < HEIGHT && powerUp.y >= 0) {
                space[powerUp.y][powerUp.x] = 'P';
            }
        }
    }

    private void processMove(char move) {
        switch (move) {
            case 'A':
                if (playerX > 0) playerX--;
                break;
            case 'D':
                if (playerX < WIDTH - 1) playerX++;
                break;
            case 'S':
                shoot();
                break;
        }
    }

    private void shoot() {
        // Ateş etme simülasyonu
        System.out.println("Ateş edildi!");
        score += 5;
    }

    private void moveEnemies() {
        // Düşmanları hareket ettir
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.y++;
            if (enemy.y >= HEIGHT) {
                enemies.remove(i);
                health -= 10;
                if (health <= 0) gameOver = true;
            }
        }

        // Güç artırıcılarını hareket ettir
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.y++;
            if (powerUp.y >= HEIGHT) {
                powerUps.remove(i);
            }
        }
    }

    private void checkCollisions() {
        // Düşman çarpışmalarını kontrol et
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy.x == playerX && enemy.y == playerY) {
                if (shield > 0) {
                    shield -= 20;
                    if (shield < 0) shield = 0;
                } else {
                    health -= 20;
                }
                enemies.remove(i);
                if (health <= 0) gameOver = true;
            }
        }

        // Güç artırıcı çarpışmalarını kontrol et
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            if (powerUp.x == playerX && powerUp.y == playerY) {
                shield = Math.min(100, shield + 30);
                powerUps.remove(i);
                score += 20;
            }
        }
    }

    private void checkLevelUp() {
        if (score >= level * 100) {
            level++;
            System.out.println("\nTebrikler! Seviye " + level + "'e yükseldiniz!");
            spawnEnemies();
        }
    }

    private void displayGame() {
        System.out.println("\n+" + "-".repeat(WIDTH) + "+");
        for (int i = 0; i < HEIGHT; i++) {
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(space[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("+" + "-".repeat(WIDTH) + "+");
    }
}

class Enemy {
    int x, y;
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class PowerUp {
    int x, y;
    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
    }
} 