import java.util.Random;
import java.util.Scanner;

public class Day25s1 {
    public static void main(String[] args) {
        TreasureHunt game = new TreasureHunt();
        game.play();
    }
}

class TreasureHunt {
    private char[][] map;
    private boolean[][] revealed;
    private int playerX;
    private int playerY;
    private int treasureX;
    private int treasureY;
    private int energy;
    private int mapSize;
    private Random random;
    private Scanner scanner;
    private int traps;
    private int powerUps;

    public TreasureHunt() {
        mapSize = 8;
        map = new char[mapSize][mapSize];
        revealed = new boolean[mapSize][mapSize];
        random = new Random();
        scanner = new Scanner(System.in);
        energy = 20;
        traps = 5;
        powerUps = 3;
        initializeGame();
    }

    private void initializeGame() {
        // Haritayı temizle
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = '.';
                revealed[i][j] = false;
            }
        }

        // Oyuncuyu yerleştir
        playerX = random.nextInt(mapSize);
        playerY = random.nextInt(mapSize);
        map[playerY][playerX] = 'P';
        revealed[playerY][playerX] = true;

        // Hazineyi yerleştir
        do {
            treasureX = random.nextInt(mapSize);
            treasureY = random.nextInt(mapSize);
        } while (treasureX == playerX && treasureY == playerY);
        map[treasureY][treasureX] = 'T';

        // Tuzakları yerleştir
        for (int i = 0; i < traps; i++) {
            int trapX, trapY;
            do {
                trapX = random.nextInt(mapSize);
                trapY = random.nextInt(mapSize);
            } while (map[trapY][trapX] != '.');
            map[trapY][trapX] = 'X';
        }

        // Enerji bonuslarını yerleştir
        for (int i = 0; i < powerUps; i++) {
            int powerX, powerY;
            do {
                powerX = random.nextInt(mapSize);
                powerY = random.nextInt(mapSize);
            } while (map[powerY][powerX] != '.');
            map[powerY][powerX] = 'E';
        }
    }

    public void play() {
        System.out.println("Hazine Avı Oyununa Hoş Geldiniz!");
        System.out.println("Hazineyi (T) bulmak için haritada hareket edin.");
        System.out.println("Tuzaklardan (X) kaçının ve enerji bonuslarını (E) toplayın.");
        System.out.println("Kontroller: W (Yukarı), S (Aşağı), A (Sol), D (Sağ), Q (Çıkış)");
        System.out.println("Her hareket 1 enerji harcar. Enerjiniz biterse oyun biter.");

        while (energy > 0) {
            displayGame();
            System.out.println("Enerji: " + energy);
            System.out.println("Hazineye olan uzaklık: " + calculateDistance(playerX, playerY, treasureX, treasureY) + " birim");
            System.out.print("Hareket yönü (W/S/A/D/Q): ");
            
            char move = scanner.next().toUpperCase().charAt(0);
            if (move == 'Q') {
                System.out.println("Oyundan çıkılıyor...");
                break;
            }

            if (makeMove(move)) {
                energy--;
                
                // Tuzak kontrolü
                if (map[playerY][playerX] == 'X') {
                    System.out.println("Bir tuzağa düştünüz! 3 enerji kaybettiniz.");
                    energy -= 3;
                }
                
                // Enerji bonusu kontrolü
                if (map[playerY][playerX] == 'E') {
                    System.out.println("Enerji bonusu buldunuz! 5 enerji kazandınız.");
                    energy += 5;
                    map[playerY][playerX] = '.';
                }
                
                // Hazine kontrolü
                if (playerX == treasureX && playerY == treasureY) {
                    System.out.println("\nTEBRİKLER! Hazineyi buldunuz!");
                    System.out.println("Kalan enerji: " + energy);
                    return;
                }
            }

            if (energy <= 0) {
                System.out.println("\nEnerjiniz bitti! Oyun bitti.");
                System.out.println("Hazine burada gizliydi: Satır " + (treasureY + 1) + ", Sütun " + (treasureX + 1));
            }
        }
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

        if (newX >= 0 && newX < mapSize && newY >= 0 && newY < mapSize) {
            map[playerY][playerX] = '.';
            playerX = newX;
            playerY = newY;
            revealed[playerY][playerX] = true;
            return true;
        } else {
            System.out.println("Harita sınırları dışına çıkamazsınız!");
            return false;
        }
    }

    private void displayGame() {
        System.out.println("\n  1 2 3 4 5 6 7 8");
        for (int i = 0; i < mapSize; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < mapSize; j++) {
                if (j == playerX && i == playerY) {
                    System.out.print("P ");
                } else if (revealed[i][j]) {
                    System.out.print(map[i][j] + " ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
} 