import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Day44s1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            YilanOyunu oyun = new YilanOyunu();
            oyun.baslat();
        });
    }
}

class YilanOyunu extends JFrame {
    private static final int PENCERE_GENISLIK = 500;
    private static final int PENCERE_YUKSEKLIK = 500;
    private static final int BIRIM_BOYUT = 20;
    private static final int OYUN_BIRIMI = (PENCERE_GENISLIK * PENCERE_YUKSEKLIK) / (BIRIM_BOYUT * BIRIM_BOYUT);
    private static final int GECIKME = 100;

    private final ArrayList<Point> yilan;
    private Point yem;
    private Timer timer;
    private Random random;
    private int yon;
    private boolean calisiyor;
    private int skor;

    private static final int SOL = 0;
    private static final int SAG = 1;
    private static final int YUKARI = 2;
    private static final int ASAGI = 3;

    private JLabel skorLabel;
    private JPanel oyunPanel;

    public YilanOyunu() {
        yilan = new ArrayList<>();
        random = new Random();
        
        setTitle("Yılan Oyunu");
        setSize(PENCERE_GENISLIK, PENCERE_YUKSEKLIK + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        skorLabel = new JLabel("Skor: 0");
        skorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        oyunPanel = new OyunPanel();
        oyunPanel.setPreferredSize(new Dimension(PENCERE_GENISLIK, PENCERE_YUKSEKLIK));
        oyunPanel.setBackground(Color.BLACK);

        add(oyunPanel, BorderLayout.CENTER);
        add(skorLabel, BorderLayout.SOUTH);
        
        addKeyListener(new YilanKeyAdapter());
        setFocusable(true);
    }
    
    public void baslat() {
        oyunuSifirla();
        setVisible(true);
    }
    
    private void oyunuSifirla() {
        yilan.clear();
        skor = 0;
        skorLabel.setText("Skor: 0");
        
        // Yılanın başlangıç konumu
        yilan.add(new Point(PENCERE_GENISLIK / 2, PENCERE_YUKSEKLIK / 2));
        
        // Başlangıç yönü
        yon = SAG;
        
        // Yem oluştur
        yeniYemOlustur();
        
        calisiyor = true;
        
        // Timer oluştur ve başlat
        if (timer != null) {
            timer.stop();
        }
        
        timer = new Timer(GECIKME, e -> oyunDongusu());
        timer.start();
    }
    
    private void oyunDongusu() {
        if (calisiyor) {
            hareket();
            carpismaKontrol();
            yemKontrol();
        }
        oyunPanel.repaint();
    }
    
    private void hareket() {
        // Yılanın başının yeni konumunu hesapla
        Point bas = yilan.get(0);
        Point yeniBas = (Point) bas.clone();
        
        switch (yon) {
            case SOL:
                yeniBas.x -= BIRIM_BOYUT;
                break;
            case SAG:
                yeniBas.x += BIRIM_BOYUT;
                break;
            case YUKARI:
                yeniBas.y -= BIRIM_BOYUT;
                break;
            case ASAGI:
                yeniBas.y += BIRIM_BOYUT;
                break;
        }
        
        // Yeni başı ekleyip kuyruğu kaldır (eğer yem yemediyse)
        yilan.add(0, yeniBas);
        if (yemKontrol()) {
            // Yem yediyse kuyruk kalır
        } else {
            // Yem yemediyse kuyruğu kaldır
            yilan.remove(yilan.size() - 1);
        }
    }
    
    private boolean yemKontrol() {
        Point bas = yilan.get(0);
        if (bas.equals(yem)) {
            // Yem yenildi, skoru artır
            skor++;
            skorLabel.setText("Skor: " + skor);
            // Yeni yem oluştur
            yeniYemOlustur();
            return true;
        }
        return false;
    }
    
    private void yeniYemOlustur() {
        boolean yerlestirildi = false;
        
        while (!yerlestirildi) {
            int x = random.nextInt(PENCERE_GENISLIK / BIRIM_BOYUT) * BIRIM_BOYUT;
            int y = random.nextInt(PENCERE_YUKSEKLIK / BIRIM_BOYUT) * BIRIM_BOYUT;
            
            Point potansiyelYem = new Point(x, y);
            yerlestirildi = true;
            
            // Yem yılanın üzerine gelmesin
            for (Point segment : yilan) {
                if (segment.equals(potansiyelYem)) {
                    yerlestirildi = false;
                    break;
                }
            }
            
            if (yerlestirildi) {
                yem = potansiyelYem;
            }
        }
    }
    
    private void carpismaKontrol() {
        Point bas = yilan.get(0);
        
        // Duvarlarla çarpışma kontrolü
        if (bas.x < 0 || bas.x >= PENCERE_GENISLIK || bas.y < 0 || bas.y >= PENCERE_YUKSEKLIK) {
            oyunBitti();
            return;
        }
        
        // Kendisiyle çarpışma kontrolü
        for (int i = 1; i < yilan.size(); i++) {
            if (bas.equals(yilan.get(i))) {
                oyunBitti();
                return;
            }
        }
    }
    
    private void oyunBitti() {
        calisiyor = false;
        timer.stop();
        
        int cevap = JOptionPane.showConfirmDialog(
            this,
            "Oyun Bitti! Skorunuz: " + skor + "\nTekrar oynamak ister misiniz?",
            "Oyun Bitti",
            JOptionPane.YES_NO_OPTION
        );
        
        if (cevap == JOptionPane.YES_OPTION) {
            oyunuSifirla();
        } else {
            dispose();
        }
    }
    
    private class OyunPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (calisiyor) {
                // Yemi çiz
                g.setColor(Color.RED);
                g.fillOval(yem.x, yem.y, BIRIM_BOYUT, BIRIM_BOYUT);
                
                // Yılanı çiz
                for (int i = 0; i < yilan.size(); i++) {
                    Point segment = yilan.get(i);
                    if (i == 0) {
                        // Baş
                        g.setColor(Color.GREEN);
                    } else {
                        // Vücut
                        g.setColor(new Color(45, 180, 0));
                    }
                    g.fillRect(segment.x, segment.y, BIRIM_BOYUT, BIRIM_BOYUT);
                }
            } else {
                // Oyun bittiyse ve tekrar başlamayı seçmediyse buraya gelmez
            }
        }
    }
    
    private class YilanKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (yon != SAG) {
                        yon = SOL;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (yon != SOL) {
                        yon = SAG;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (yon != ASAGI) {
                        yon = YUKARI;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (yon != YUKARI) {
                        yon = ASAGI;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (!calisiyor) {
                        oyunuSifirla();
                    }
                    break;
            }
        }
    }
} 