import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Day45s1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SayiTahminOyunu oyun = new SayiTahminOyunu();
            oyun.baslat();
        });
    }
}

class SayiTahminOyunu extends JFrame {
    private static final int PENCERE_GENISLIK = 400;
    private static final int PENCERE_YUKSEKLIK = 500;
    private static final int MIN_SAYI = 1;
    private static final int MAX_SAYI = 100;
    
    private int hedefSayi;
    private int tahminSayisi;
    private Random random;
    private JTextField tahminField;
    private JTextArea sonucArea;
    private JButton tahminButton;
    private JButton yeniOyunButton;
    private JLabel ipucuLabel;
    
    public SayiTahminOyunu() {
        random = new Random();
        tahminSayisi = 0;
        
        setTitle("Sayı Tahmin Oyunu");
        setSize(PENCERE_GENISLIK, PENCERE_YUKSEKLIK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Ana panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Üst panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        tahminField = new JTextField(10);
        tahminField.setFont(new Font("Arial", Font.PLAIN, 20));
        tahminField.addActionListener(e -> tahminYap());
        
        tahminButton = new JButton("Tahmin Et");
        tahminButton.setFont(new Font("Arial", Font.BOLD, 14));
        tahminButton.addActionListener(e -> tahminYap());
        
        yeniOyunButton = new JButton("Yeni Oyun");
        yeniOyunButton.setFont(new Font("Arial", Font.BOLD, 14));
        yeniOyunButton.addActionListener(e -> yeniOyun());
        
        topPanel.add(new JLabel("Tahmininiz: "));
        topPanel.add(tahminField);
        topPanel.add(tahminButton);
        topPanel.add(yeniOyunButton);
        
        // Orta panel
        sonucArea = new JTextArea(10, 30);
        sonucArea.setEditable(false);
        sonucArea.setFont(new Font("Arial", Font.PLAIN, 16));
        sonucArea.setLineWrap(true);
        sonucArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(sonucArea);
        
        // Alt panel
        ipucuLabel = new JLabel("1 ile 100 arasında bir sayı seçildi!");
        ipucuLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ipucuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panelleri ana panele ekle
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(ipucuLabel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Yeni oyun başlat
        yeniOyun();
    }
    
    public void baslat() {
        setVisible(true);
    }
    
    private void yeniOyun() {
        hedefSayi = random.nextInt(MAX_SAYI - MIN_SAYI + 1) + MIN_SAYI;
        tahminSayisi = 0;
        tahminField.setText("");
        sonucArea.setText("");
        ipucuLabel.setText("1 ile 100 arasında bir sayı seçildi!");
        tahminField.setEnabled(true);
        tahminButton.setEnabled(true);
        tahminField.requestFocus();
    }
    
    private void tahminYap() {
        try {
            int tahmin = Integer.parseInt(tahminField.getText());
            
            if (tahmin < MIN_SAYI || tahmin > MAX_SAYI) {
                sonucArea.append("Lütfen " + MIN_SAYI + " ile " + MAX_SAYI + " arasında bir sayı girin!\n");
                return;
            }
            
            tahminSayisi++;
            
            if (tahmin == hedefSayi) {
                sonucArea.append("Tebrikler! " + tahminSayisi + " denemede doğru sayıyı buldunuz!\n");
                ipucuLabel.setText("Oyun bitti! Yeni oyun başlatmak için 'Yeni Oyun' butonuna tıklayın.");
                tahminField.setEnabled(false);
                tahminButton.setEnabled(false);
            } else if (tahmin < hedefSayi) {
                sonucArea.append(tahmin + " sayısı hedef sayıdan KÜÇÜK!\n");
                ipucuLabel.setText("Daha büyük bir sayı deneyin!");
            } else {
                sonucArea.append(tahmin + " sayısı hedef sayıdan BÜYÜK!\n");
                ipucuLabel.setText("Daha küçük bir sayı deneyin!");
            }
            
            tahminField.setText("");
            tahminField.requestFocus();
            
        } catch (NumberFormatException e) {
            sonucArea.append("Lütfen geçerli bir sayı girin!\n");
            tahminField.setText("");
            tahminField.requestFocus();
        }
    }
} 