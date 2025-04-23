import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Day40s1 {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        player.start();
    }
}

class MusicPlayer {
    private List<Song> playlist;
    private int currentSongIndex;
    private boolean isPlaying;
    private Scanner scanner;
    private Random random;

    public MusicPlayer() {
        this.playlist = new ArrayList<>();
        this.currentSongIndex = -1;
        this.isPlaying = false;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void start() {
        System.out.println("Müzik Çalara Hoş Geldiniz!");
        
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            switch (choice) {
                case 1:
                    addSong();
                    break;
                case 2:
                    play();
                    break;
                case 3:
                    pause();
                    break;
                case 4:
                    next();
                    break;
                case 5:
                    previous();
                    break;
                case 6:
                    shuffle();
                    break;
                case 7:
                    showPlaylist();
                    break;
                case 8:
                    removeSong();
                    break;
                case 9:
                    System.out.println("Müzik çalar kapatılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Müzik Çalar Menüsü ===");
        System.out.println("1. Şarkı Ekle");
        System.out.println("2. Çal");
        System.out.println("3. Duraklat");
        System.out.println("4. Sonraki");
        System.out.println("5. Önceki");
        System.out.println("6. Karıştır");
        System.out.println("7. Çalma Listesini Göster");
        System.out.println("8. Şarkı Sil");
        System.out.println("9. Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private void addSong() {
        System.out.print("Şarkı adı: ");
        String title = scanner.nextLine();
        System.out.print("Sanatçı: ");
        String artist = scanner.nextLine();
        System.out.print("Süre (dakika): ");
        int duration = scanner.nextInt();
        
        Song song = new Song(title, artist, duration);
        playlist.add(song);
        System.out.println("Şarkı başarıyla eklendi!");
    }

    private void play() {
        if (playlist.isEmpty()) {
            System.out.println("Çalma listesi boş!");
            return;
        }

        if (currentSongIndex == -1) {
            currentSongIndex = 0;
        }

        isPlaying = true;
        Song current = playlist.get(currentSongIndex);
        System.out.println("Çalınıyor: " + current);
    }

    private void pause() {
        if (!isPlaying) {
            System.out.println("Zaten duraklatılmış!");
            return;
        }

        isPlaying = false;
        System.out.println("Duraklatıldı: " + playlist.get(currentSongIndex));
    }

    private void next() {
        if (playlist.isEmpty()) {
            System.out.println("Çalma listesi boş!");
            return;
        }

        currentSongIndex = (currentSongIndex + 1) % playlist.size();
        if (isPlaying) {
            System.out.println("Sonraki şarkı: " + playlist.get(currentSongIndex));
        }
    }

    private void previous() {
        if (playlist.isEmpty()) {
            System.out.println("Çalma listesi boş!");
            return;
        }

        currentSongIndex = (currentSongIndex - 1 + playlist.size()) % playlist.size();
        if (isPlaying) {
            System.out.println("Önceki şarkı: " + playlist.get(currentSongIndex));
        }
    }

    private void shuffle() {
        if (playlist.isEmpty()) {
            System.out.println("Çalma listesi boş!");
            return;
        }

        for (int i = playlist.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Song temp = playlist.get(i);
            playlist.set(i, playlist.get(j));
            playlist.set(j, temp);
        }

        System.out.println("Çalma listesi karıştırıldı!");
        showPlaylist();
    }

    private void showPlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("Çalma listesi boş!");
            return;
        }

        System.out.println("\n=== Çalma Listesi ===");
        for (int i = 0; i < playlist.size(); i++) {
            Song song = playlist.get(i);
            String marker = (i == currentSongIndex && isPlaying) ? "▶ " : "  ";
            System.out.println(marker + (i + 1) + ". " + song);
        }
    }

    private void removeSong() {
        if (playlist.isEmpty()) {
            System.out.println("Çalma listesi boş!");
            return;
        }

        showPlaylist();
        System.out.print("Silmek istediğiniz şarkının numarasını girin: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < playlist.size()) {
            Song removed = playlist.remove(index);
            System.out.println("Şarkı silindi: " + removed);
            
            if (currentSongIndex == index) {
                currentSongIndex = -1;
                isPlaying = false;
            } else if (currentSongIndex > index) {
                currentSongIndex--;
            }
        } else {
            System.out.println("Geçersiz şarkı numarası!");
        }
    }
}

class Song {
    private String title;
    private String artist;
    private int duration;

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return title + " - " + artist + " (" + duration + " dakika)";
    }
} 