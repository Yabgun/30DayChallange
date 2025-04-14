import java.util.Scanner;

public class Day31s1 {
    public static void main(String[] args) {
        EightQueensSolver solver = new EightQueensSolver();
        solver.solve();
    }
}

class EightQueensSolver {
    private static final int SIZE = 8;
    private int[] queens; // queens[i] = j, i. satırdaki vezirin j. sütunda olduğunu gösterir
    private int solutionCount;
    private Scanner scanner;

    public EightQueensSolver() {
        this.queens = new int[SIZE];
        this.solutionCount = 0;
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("8 Vezir Bulmacası Çözücüsüne Hoş Geldiniz!");
        System.out.println("Bu program, 8 vezirin satranç tahtası üzerinde birbirini tehdit etmeden yerleştirilmesinin tüm çözümlerini bulur.");
        System.out.println("Kaç çözüm görmek istersiniz? (1-92):");
        
        int requestedSolutions = scanner.nextInt();
        if (requestedSolutions < 1 || requestedSolutions > 92) {
            System.out.println("Geçersiz sayı! Varsayılan olarak 1 çözüm gösterilecek.");
            requestedSolutions = 1;
        }

        System.out.println("\nÇözümler aranıyor...");
        findSolutions(0, requestedSolutions);
        
        if (solutionCount == 0) {
            System.out.println("Hiç çözüm bulunamadı!");
        } else {
            System.out.println("\nToplam " + solutionCount + " çözüm bulundu.");
        }
    }

    private void findSolutions(int row, int requestedSolutions) {
        if (row == SIZE) {
            solutionCount++;
            printSolution();
            return;
        }

        for (int col = 0; col < SIZE; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                findSolutions(row + 1, requestedSolutions);
                
                if (solutionCount >= requestedSolutions) {
                    return;
                }
            }
        }
    }

    private boolean isSafe(int row, int col) {
        // Aynı sütunda başka vezir var mı kontrol et
        for (int i = 0; i < row; i++) {
            if (queens[i] == col) {
                return false;
            }
        }

        // Sol üst çaprazda vezir var mı kontrol et
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (queens[i] == j) {
                return false;
            }
        }

        // Sağ üst çaprazda vezir var mı kontrol et
        for (int i = row, j = col; i >= 0 && j < SIZE; i--, j++) {
            if (queens[i] == j) {
                return false;
            }
        }

        return true;
    }

    private void printSolution() {
        System.out.println("\nÇözüm #" + solutionCount + ":");
        System.out.println("+-----------------+");
        
        for (int i = 0; i < SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < SIZE; j++) {
                if (queens[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("|");
        }
        
        System.out.println("+-----------------+");
        
        // Çözümün koordinatlarını göster
        System.out.print("Koordinatlar: ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("(" + (i + 1) + "," + (queens[i] + 1) + ") ");
        }
        System.out.println("\n");
    }
} 