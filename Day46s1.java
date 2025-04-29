import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.security.*;

public class Day46s1 {
    private static final List<String> GUCLU_SIFRE_PATTERNS = Arrays.asList(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{12,}$"
    );
    
    private static final List<String> HASSAS_DOSYA_PATTERNS = Arrays.asList(
        "\\.env$",
        "config\\.(json|xml|yml|yaml|properties)$",
        "password\\.txt$",
        "secret\\.txt$",
        "credentials\\.txt$"
    );
    
    private static final List<String> TEHLIKELI_ICERIK_PATTERNS = Arrays.asList(
        "password\\s*=\\s*['\"].*['\"]",
        "secret\\s*=\\s*['\"].*['\"]",
        "api_key\\s*=\\s*['\"].*['\"]",
        "token\\s*=\\s*['\"].*['\"]",
        "private_key\\s*=\\s*['\"].*['\"]"
    );

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Kullanım: java Day46s1 <taranacak_dizin>");
            return;
        }

        String dizinYolu = args[0];
        File dizin = new File(dizinYolu);

        if (!dizin.exists() || !dizin.isDirectory()) {
            System.out.println("Hata: Belirtilen dizin bulunamadı veya bir dizin değil!");
            return;
        }

        System.out.println("Güvenlik Taraması Başlatılıyor...");
        System.out.println("Dizin: " + dizinYolu);
        System.out.println("----------------------------------------");

        try {
            List<File> hassasDosyalar = hassasDosyalariBul(dizin);
            List<File> zayifSifreliDosyalar = zayifSifreleriKontrolEt(dizin);
            List<File> tehlikeIcerikliDosyalar = tehlikeIcerikleriniKontrolEt(dizin);

            raporOlustur(hassasDosyalar, zayifSifreliDosyalar, tehlikeIcerikliDosyalar);
        } catch (IOException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private static List<File> hassasDosyalariBul(File dizin) throws IOException {
        List<File> hassasDosyalar = new ArrayList<>();
        Files.walk(dizin.toPath())
            .filter(Files::isRegularFile)
            .forEach(path -> {
                String dosyaAdi = path.getFileName().toString();
                for (String pattern : HASSAS_DOSYA_PATTERNS) {
                    if (Pattern.matches(pattern, dosyaAdi)) {
                        hassasDosyalar.add(path.toFile());
                        break;
                    }
                }
            });
        return hassasDosyalar;
    }

    private static List<File> zayifSifreleriKontrolEt(File dizin) throws IOException {
        List<File> zayifSifreliDosyalar = new ArrayList<>();
        Files.walk(dizin.toPath())
            .filter(Files::isRegularFile)
            .forEach(path -> {
                try {
                    String icerik = new String(Files.readAllBytes(path));
                    for (String pattern : GUCLU_SIFRE_PATTERNS) {
                        if (!Pattern.matches(pattern, icerik)) {
                            zayifSifreliDosyalar.add(path.toFile());
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Dosya okuma hatası: " + path);
                }
            });
        return zayifSifreliDosyalar;
    }

    private static List<File> tehlikeIcerikleriniKontrolEt(File dizin) throws IOException {
        List<File> tehlikeIcerikliDosyalar = new ArrayList<>();
        Files.walk(dizin.toPath())
            .filter(Files::isRegularFile)
            .forEach(path -> {
                try {
                    String icerik = new String(Files.readAllBytes(path));
                    for (String pattern : TEHLIKELI_ICERIK_PATTERNS) {
                        if (Pattern.compile(pattern).matcher(icerik).find()) {
                            tehlikeIcerikliDosyalar.add(path.toFile());
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Dosya okuma hatası: " + path);
                }
            });
        return tehlikeIcerikliDosyalar;
    }

    private static void raporOlustur(List<File> hassasDosyalar, 
                                   List<File> zayifSifreliDosyalar,
                                   List<File> tehlikeIcerikliDosyalar) {
        System.out.println("\n=== GÜVENLİK TARAMA RAPORU ===");
        
        System.out.println("\n1. Hassas Dosyalar:");
        if (hassasDosyalar.isEmpty()) {
            System.out.println("Hassas dosya bulunamadı.");
        } else {
            hassasDosyalar.forEach(dosya -> 
                System.out.println("- " + dosya.getAbsolutePath()));
        }

        System.out.println("\n2. Zayıf Şifre İçeren Dosyalar:");
        if (zayifSifreliDosyalar.isEmpty()) {
            System.out.println("Zayıf şifre içeren dosya bulunamadı.");
        } else {
            zayifSifreliDosyalar.forEach(dosya -> 
                System.out.println("- " + dosya.getAbsolutePath()));
        }

        System.out.println("\n3. Tehlikeli İçerik İçeren Dosyalar:");
        if (tehlikeIcerikliDosyalar.isEmpty()) {
            System.out.println("Tehlikeli içerik içeren dosya bulunamadı.");
        } else {
            tehlikeIcerikliDosyalar.forEach(dosya -> 
                System.out.println("- " + dosya.getAbsolutePath()));
        }

        System.out.println("\n=== ÖNERİLER ===");
        System.out.println("1. Hassas dosyaları güvenli bir konuma taşıyın");
        System.out.println("2. Zayıf şifreleri güçlü şifrelerle değiştirin");
        System.out.println("3. Tehlikeli içerikleri temizleyin veya güvenli hale getirin");
        System.out.println("4. Düzenli güvenlik taramaları yapın");
    }
} 