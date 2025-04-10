import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Day28s1 {
    public static void main(String[] args) {
        PuzzleWorld game = new PuzzleWorld();
        game.play();
    }
}

class PuzzleWorld {
    private static final char PLAYER = '☺';
    private static final char EMPTY = '·';
    private static final char WALL = '■';
    private static final char BOX = '□';
    private static final char TARGET = '×';
    private static final char BOX_ON_TARGET = '▣';
    private static final char TELEPORT = '○';
    private static final char ICE = '░';
    
    private char[][] board;
    private int playerX;
    private int playerY;
    private int width;
    private int height;
    private int level;
    private int moves;
    private List<int[]> targets;
    private List<int[]> teleports;
    private Random random;
    private Scanner scanner;
    
    public PuzzleWorld() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.targets = new ArrayList<>();
        this.teleports = new ArrayList<>();
        this.level = 1;
        this.moves = 0;
        initializeGame();
    }
    
    private void initializeGame() {
        // Oyun seviyesine göre boyut ayarla
        height = 10 + level;
        width = 20 + level;
        board = new char[height][width];
        targets.clear();
        teleports.clear();
        
        // Tahtayı boş alanlarla doldur
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = EMPTY;
            }
        }
        
        // Duvarları oluştur
        createWalls();
        
        // Hedefleri yerleştir
        int targetCount = 2 + (level / 2);
        for (int i = 0; i < targetCount; i++) {
            int x, y;
            do {
                x = random.nextInt(width - 4) + 2;
                y = random.nextInt(height - 4) + 2;
            } while (board[y][x] != EMPTY);
            board[y][x] = TARGET;
            targets.add(new int[]{x, y});
        }
        
        // Kutuları yerleştir
        for (int i = 0; i < targetCount; i++) {
            int x, y;
            do {
                x = random.nextInt(width - 4) + 2;
                y = random.nextInt(height - 4) + 2;
            } while (board[y][x] != EMPTY);
            board[y][x] = BOX;
        }
        
        // Teleport noktalarını yerleştir
        if (level >= 2) {
            int teleportCount = 2;
            for (int i = 0; i < teleportCount; i++) {
                int x, y;
                do {
                    x = random.nextInt(width - 4) + 2;
                    y = random.nextInt(height - 4) + 2;
                } while (board[y][x] != EMPTY);
                board[y][x] = TELEPORT;
                teleports.add(new int[]{x, y});
            }
        }
        
        // Buz alanları yerleştir
        if (level >= 3) {
            int iceCount = level * 2;
            for (int i = 0; i < iceCount; i++) {
                int x, y;
                do {
                    x = random.nextInt(width - 4) + 2;
                    y = random.nextInt(height - 4) + 2;
                } while (board[y][x] != EMPTY);
                board[y][x] = ICE;
            }
        }
        
        // Oyuncuyu yerleştir
        do {
            playerX = random.nextInt(width - 4) + 2;
            playerY = random.nextInt(height - 4) + 2;
        } while (board[playerY][playerX] != EMPTY);
        board[playerY][playerX] = PLAYER;
    }
    
    private void createWalls() {
        // Dış duvarları oluştur
        for (int i = 0; i < height; i++) {
            board[i][0] = WALL;
            board[i][width-1] = WALL;
        }
        for (int j = 0; j < width; j++) {
            board[0][j] = WALL;
            board[height-1][j] = WALL;
        }
        
        // İç duvarları oluştur
        int wallCount = 10 + level * 2;
        for (int i = 0; i < wallCount; i++) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            
            // Yatay veya dikey duvar oluştur
            if (random.nextBoolean()) {
                // Yatay duvar
                int length = random.nextInt(4) + 2;
                for (int j = 0; j < length; j++) {
                    if (x + j < width - 1) {
                        board[y][x + j] = WALL;
                    }
                }
            } else {
                // Dikey duvar
                int length = random.nextInt(3) + 2;
                for (int j = 0; j < length; j++) {
                    if (y + j < height - 1) {
                        board[y + j][x] = WALL;
                    }
                }
            }
        }
    }
    
    public void play() {
        System.out.println("Bulmaca Diyarına Hoş Geldiniz! - Seviye " + level);
        System.out.println("Kontroller: W (Yukarı), S (Aşağı), A (Sol), D (Sağ), R (Seviyeyi Yenile), Q (Çıkış)");
        System.out.println("Tüm kutuları (" + BOX + ") hedeflere (" + TARGET + ") itmelisiniz.");
        System.out.println("Teleport noktaları (" + TELEPORT + ") sizi başka bir teleport noktasına ışınlar.");
        System.out.println("Buz bölgelerde (" + ICE + ") hareket ederken kayarsınız, dikkatli olun!");
        
        boolean continuePlaying = true;
        
        while (continuePlaying) {
            displayBoard();
            System.out.println("Seviye: " + level + " | Hamle Sayısı: " + moves);
            System.out.print("Hareket (W/S/A/D/R/Q): ");
            
            char move = scanner.next().toUpperCase().charAt(0);
            
            switch (move) {
                case 'Q':
                    System.out.println("Oyundan çıkılıyor...");
                    continuePlaying = false;
                    break;
                case 'R':
                    System.out.println("Seviye yenileniyor...");
                    initializeGame();
                    moves = 0;
                    break;
                default:
                    if (makeMove(move)) {
                        moves++;
                        if (checkWin()) {
                            displayBoard();
                            System.out.println("\nTEBRİKLER! Seviye " + level + " tamamlandı!");
                            System.out.println("Toplam hamle: " + moves);
                            
                            System.out.print("Bir sonraki seviyeye geçmek istiyor musunuz? (E/H): ");
                            char choice = scanner.next().toUpperCase().charAt(0);
                            
                            if (choice == 'E') {
                                level++;
                                moves = 0;
                                initializeGame();
                                System.out.println("\nSeviye " + level + " başlıyor!");
                            } else {
                                continuePlaying = false;
                            }
                        }
                    }
                    break;
            }
        }
        
        System.out.println("\nOyun bitti! Ulaşılan seviye: " + level);
    }
    
    private boolean makeMove(char direction) {
        int dx = 0, dy = 0;
        
        switch (direction) {
            case 'W': dy = -1; break;
            case 'S': dy = 1; break;
            case 'A': dx = -1; break;
            case 'D': dx = 1; break;
            default:
                System.out.println("Geçersiz hareket!");
                return false;
        }
        
        int newX = playerX + dx;
        int newY = playerY + dy;
        
        // Sınırlar içinde mi kontrol et
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return false;
        }
        
        // Yeni pozisyona göre hareket et
        if (board[newY][newX] == EMPTY || board[newY][newX] == TARGET) {
            // Boş alana veya hedefe hareket
            board[playerY][playerX] = board[playerY][playerX] == PLAYER ? EMPTY : TARGET;
            playerX = newX;
            playerY = newY;
            board[playerY][playerX] = PLAYER;
            return true;
        } else if (board[newY][newX] == BOX || board[newY][newX] == BOX_ON_TARGET) {
            // Kutuyu itme
            int boxNewX = newX + dx;
            int boxNewY = newY + dy;
            
            if (boxNewX >= 0 && boxNewX < width && boxNewY >= 0 && boxNewY < height) {
                if (board[boxNewY][boxNewX] == EMPTY || board[boxNewY][boxNewX] == TARGET) {
                    // Kutu boş alana veya hedefe itilebilir
                    boolean isBoxOnTarget = board[newY][newX] == BOX_ON_TARGET;
                    boolean isTargetAhead = board[boxNewY][boxNewX] == TARGET;
                    
                    // Kutuyu ileri taşı
                    board[boxNewY][boxNewX] = isTargetAhead ? BOX_ON_TARGET : BOX;
                    
                    // Oyuncuyu kutunun yerine taşı
                    board[newY][newX] = isBoxOnTarget ? TARGET : EMPTY;
                    
                    // Oyuncunun eski yerini güncelle
                    board[playerY][playerX] = board[playerY][playerX] == PLAYER ? EMPTY : TARGET;
                    
                    playerX = newX;
                    playerY = newY;
                    board[playerY][playerX] = PLAYER;
                    return true;
                }
            }
        } else if (board[newY][newX] == TELEPORT) {
            // Teleport noktasına hareket
            teleportPlayer(newX, newY);
            return true;
        } else if (board[newY][newX] == ICE) {
            // Buz üzerinde kayma
            board[playerY][playerX] = board[playerY][playerX] == PLAYER ? EMPTY : TARGET;
            playerX = newX;
            playerY = newY;
            
            // Kaymaya devam et
            while (true) {
                int iceNewX = playerX + dx;
                int iceNewY = playerY + dy;
                
                if (iceNewX < 0 || iceNewX >= width || iceNewY < 0 || iceNewY >= height) {
                    break;
                }
                
                if (board[iceNewY][iceNewX] == EMPTY) {
                    board[playerY][playerX] = ICE;
                    playerX = iceNewX;
                    playerY = iceNewY;
                } else if (board[iceNewY][iceNewX] == TARGET) {
                    board[playerY][playerX] = ICE;
                    playerX = iceNewX;
                    playerY = iceNewY;
                } else {
                    break;
                }
            }
            
            board[playerY][playerX] = PLAYER;
            return true;
        }
        
        return false;
    }
    
    private void teleportPlayer(int sourceX, int sourceY) {
        // Başka bir teleport noktası bul
        for (int[] teleport : teleports) {
            if (teleport[0] != sourceX || teleport[1] != sourceY) {
                // Oyuncuyu eski konumundan kaldır
                board[playerY][playerX] = board[playerY][playerX] == PLAYER ? EMPTY : TARGET;
                
                // Oyuncuyu yeni teleport noktasına taşı
                playerX = teleport[0];
                playerY = teleport[1];
                board[playerY][playerX] = PLAYER;
                
                System.out.println("Işınlandınız!");
                return;
            }
        }
    }
    
    private boolean checkWin() {
        // Tüm hedeflerin üzerinde kutu var mı kontrol et
        for (int[] target : targets) {
            if (board[target[1]][target[0]] != BOX_ON_TARGET) {
                return false;
            }
        }
        return true;
    }
    
    private void displayBoard() {
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
} 