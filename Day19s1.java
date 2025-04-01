import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * Basit HTTP Sunucusu ve REST API Örneği
 * Bu program, temel bir HTTP sunucusu oluşturur ve basit bir REST API sunar.
 * Kullanıcı bilgilerini yönetmek için CRUD operasyonları içerir.
 */
public class Day19s1 {
    private static final int PORT = 8080;
    private static final Map<Integer, User> users = new ConcurrentHashMap<>();
    private static int nextUserId = 1;
    
    public static void main(String[] args) {
        System.out.println("*** BASİT HTTP SUNUCUSU VE REST API ***");
        System.out.println("Sunucu " + PORT + " portunda başlatılıyor...");
        
        // Örnek kullanıcılar ekle
        users.put(nextUserId++, new User("Ahmet", "ahmet@email.com"));
        users.put(nextUserId++, new User("Ayşe", "ayse@email.com"));
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Sunucu başlatıldı. Bağlantılar bekleniyor...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Sunucu hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            
            // HTTP isteğini oku
            String requestLine = in.readLine();
            if (requestLine == null) return;
            
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];
            
            // İstek gövdesini oku
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                body.append(line).append("\n");
            }
            
            // Yanıt oluştur
            String response = handleRequest(method, path, body.toString());
            
            // HTTP yanıtını gönder
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/json");
            out.println("Access-Control-Allow-Origin: *");
            out.println();
            out.println(response);
            
        } catch (IOException e) {
            System.err.println("İstemci hatası: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static String handleRequest(String method, String path, String body) {
        try {
            switch (method) {
                case "GET":
                    return handleGet(path);
                case "POST":
                    return handlePost(body);
                case "PUT":
                    return handlePut(path, body);
                case "DELETE":
                    return handleDelete(path);
                default:
                    return createErrorResponse("Desteklenmeyen HTTP metodu: " + method);
            }
        } catch (Exception e) {
            return createErrorResponse("İşlem hatası: " + e.getMessage());
        }
    }
    
    private static String handleGet(String path) {
        if (path.equals("/api/users")) {
            return createSuccessResponse(users.values());
        } else if (path.startsWith("/api/users/")) {
            int id = Integer.parseInt(path.substring("/api/users/".length()));
            User user = users.get(id);
            if (user != null) {
                return createSuccessResponse(user);
            }
            return createErrorResponse("Kullanıcı bulunamadı");
        }
        return createErrorResponse("Geçersiz endpoint");
    }
    
    private static String handlePost(String body) {
        try {
            User newUser = parseUser(body);
            int id = nextUserId++;
            users.put(id, newUser);
            return createSuccessResponse("Kullanıcı oluşturuldu. ID: " + id);
        } catch (Exception e) {
            return createErrorResponse("Kullanıcı oluşturma hatası: " + e.getMessage());
        }
    }
    
    private static String handlePut(String path, String body) {
        if (!path.startsWith("/api/users/")) {
            return createErrorResponse("Geçersiz endpoint");
        }
        
        try {
            int id = Integer.parseInt(path.substring("/api/users/".length()));
            if (!users.containsKey(id)) {
                return createErrorResponse("Kullanıcı bulunamadı");
            }
            
            User updatedUser = parseUser(body);
            users.put(id, updatedUser);
            return createSuccessResponse("Kullanıcı güncellendi");
        } catch (Exception e) {
            return createErrorResponse("Kullanıcı güncelleme hatası: " + e.getMessage());
        }
    }
    
    private static String handleDelete(String path) {
        if (!path.startsWith("/api/users/")) {
            return createErrorResponse("Geçersiz endpoint");
        }
        
        try {
            int id = Integer.parseInt(path.substring("/api/users/".length()));
            if (users.remove(id) != null) {
                return createSuccessResponse("Kullanıcı silindi");
            }
            return createErrorResponse("Kullanıcı bulunamadı");
        } catch (Exception e) {
            return createErrorResponse("Kullanıcı silme hatası: " + e.getMessage());
        }
    }
    
    private static User parseUser(String json) {
        // Basit JSON parsing
        json = json.replaceAll("[{}\"]", "");
        String[] parts = json.split(",");
        String name = parts[0].split(":")[1].trim();
        String email = parts[1].split(":")[1].trim();
        return new User(name, email);
    }
    
    private static String createSuccessResponse(Object data) {
        return "{\"success\": true, \"data\": " + data + "}";
    }
    
    private static String createErrorResponse(String message) {
        return "{\"success\": false, \"error\": \"" + message + "\"}";
    }
    
    private static class User {
        private String name;
        private String email;
        
        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        @Override
        public String toString() {
            return "{\"name\": \"" + name + "\", \"email\": \"" + email + "\"}";
        }
    }
} 