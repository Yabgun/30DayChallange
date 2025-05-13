import java.io.*;
import java.net.*;
import java.util.*;

public class Day55s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Güvenlik Zaafiyet Tarayıcısına Hoş Geldiniz!");
        System.out.print("Taranacak hedef URL'yi girin: ");
        String targetUrl = scanner.nextLine();

        try {
            // URL kontrolü
            URL url = new URL(targetUrl);
            System.out.println("\n[+] URL Kontrolü:");
            System.out.println("    Protokol: " + url.getProtocol());
            System.out.println("    Host: " + url.getHost());
            System.out.println("    Port: " + url.getPort());

            // Port taraması
            System.out.println("\n[+] Port Taraması:");
            int[] commonPorts = {21, 22, 23, 25, 53, 80, 443, 3306, 3389, 8080};
            for (int port : commonPorts) {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(url.getHost(), port), 1000);
                    System.out.println("    Port " + port + " açık");
                    socket.close();
                } catch (Exception e) {
                    System.out.println("    Port " + port + " kapalı");
                }
            }

            // HTTP başlık kontrolü
            System.out.println("\n[+] HTTP Başlık Kontrolü:");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null) {
                    System.out.println("    " + entry.getKey() + ": " + entry.getValue());
                }
            }

            // Güvenlik önerileri
            System.out.println("\n[+] Güvenlik Önerileri:");
            if (!url.getProtocol().equals("https")) {
                System.out.println("    [!] HTTPS kullanmanız önerilir");
            }
            if (headers.containsKey("X-Frame-Options")) {
                System.out.println("    [+] X-Frame-Options başlığı mevcut");
            } else {
                System.out.println("    [!] X-Frame-Options başlığı eksik");
            }
            if (headers.containsKey("X-XSS-Protection")) {
                System.out.println("    [+] X-XSS-Protection başlığı mevcut");
            } else {
                System.out.println("    [!] X-XSS-Protection başlığı eksik");
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
} 