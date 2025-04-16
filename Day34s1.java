import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day34s1 {
    public static void main(String[] args) {
        LogicPuzzleSolver solver = new LogicPuzzleSolver();
        solver.solve();
    }
}

class LogicPuzzleSolver {
    private static final String[] COLORS = {"KIRMIZI", "MAVİ", "YEŞİL", "SARI", "BEYAZ"};
    private static final String[] NATIONALITIES = {"İNGİLİZ", "İSPANYOL", "NORVEÇLİ", "JAPON", "ALMAN"};
    private static final String[] DRINKS = {"ÇAY", "KAHVE", "SU", "SÜT", "PORTAKAL SUYU"};
    private static final String[] CIGARETTES = {"MARLBORO", "DUNHILL", "PALL MALL", "ROTHMANS", "WINDFIELD"};
    private static final String[] PETS = {"KÖPEK", "KUŞ", "KEDİ", "AT", "BALIK"};
    
    private List<House> houses;
    private Scanner scanner;

    public LogicPuzzleSolver() {
        this.houses = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("Mantık Bulmacası Çözücüye Hoş Geldiniz!");
        System.out.println("Bu program, Einstein Bulmacası tarzındaki mantık bulmacalarını çözer.");
        
        // 5 ev oluştur
        for (int i = 0; i < 5; i++) {
            houses.add(new House());
        }

        // Bulmacayı çöz
        solvePuzzle();

        // Sonucu göster
        printSolution();
    }

    private void solvePuzzle() {
        // 1. İngiliz kırmızı evde yaşıyor
        setAttribute(0, "NATIONALITY", "İNGİLİZ");
        setAttribute(0, "COLOR", "KIRMIZI");

        // 2. İspanyol köpeğe sahip
        setAttribute(1, "NATIONALITY", "İSPANYOL");
        setAttribute(1, "PET", "KÖPEK");

        // 3. Norveçli ilk evde yaşıyor
        setAttribute(0, "NATIONALITY", "NORVEÇLİ");

        // 4. Sarı evde Marlboro içiliyor
        for (int i = 0; i < 5; i++) {
            if (houses.get(i).getAttribute("COLOR").equals("SARI")) {
                setAttribute(i, "CIGARETTE", "MARLBORO");
            }
        }

        // 5. Dunhill içen kişinin komşusu kedisi var
        for (int i = 0; i < 5; i++) {
            if (houses.get(i).getAttribute("CIGARETTE").equals("DUNHILL")) {
                if (i > 0) setAttribute(i-1, "PET", "KEDİ");
                if (i < 4) setAttribute(i+1, "PET", "KEDİ");
            }
        }

        // 6. Pall Mall içen kişi kuş besliyor
        for (int i = 0; i < 5; i++) {
            if (houses.get(i).getAttribute("CIGARETTE").equals("PALL MALL")) {
                setAttribute(i, "PET", "KUŞ");
            }
        }

        // 7. Alman Rothmans içiyor
        for (int i = 0; i < 5; i++) {
            if (houses.get(i).getAttribute("NATIONALITY").equals("ALMAN")) {
                setAttribute(i, "CIGARETTE", "ROTHMANS");
            }
        }

        // 8. Yeşil evin sahibi kahve içiyor
        for (int i = 0; i < 5; i++) {
            if (houses.get(i).getAttribute("COLOR").equals("YEŞİL")) {
                setAttribute(i, "DRINK", "KAHVE");
            }
        }

        // 9. Japon Windfield içiyor
        for (int i = 0; i < 5; i++) {
            if (houses.get(i).getAttribute("NATIONALITY").equals("JAPON")) {
                setAttribute(i, "CIGARETTE", "WINDFIELD");
            }
        }

        // 10. Ortadaki evde süt içiliyor
        setAttribute(2, "DRINK", "SÜT");

        // Kalan özellikleri tahmin et
        guessRemainingAttributes();
    }

    private void setAttribute(int houseIndex, String attribute, String value) {
        houses.get(houseIndex).setAttribute(attribute, value);
    }

    private void guessRemainingAttributes() {
        // Kalan özellikleri tahmin et
        for (int i = 0; i < 5; i++) {
            House house = houses.get(i);
            
            // Renk tahmini
            if (house.getAttribute("COLOR").equals("?")) {
                for (String color : COLORS) {
                    if (!isColorUsed(color)) {
                        setAttribute(i, "COLOR", color);
                        break;
                    }
                }
            }

            // İçecek tahmini
            if (house.getAttribute("DRINK").equals("?")) {
                for (String drink : DRINKS) {
                    if (!isDrinkUsed(drink)) {
                        setAttribute(i, "DRINK", drink);
                        break;
                    }
                }
            }

            // Sigara tahmini
            if (house.getAttribute("CIGARETTE").equals("?")) {
                for (String cigarette : CIGARETTES) {
                    if (!isCigaretteUsed(cigarette)) {
                        setAttribute(i, "CIGARETTE", cigarette);
                        break;
                    }
                }
            }

            // Evcil hayvan tahmini
            if (house.getAttribute("PET").equals("?")) {
                for (String pet : PETS) {
                    if (!isPetUsed(pet)) {
                        setAttribute(i, "PET", pet);
                        break;
                    }
                }
            }
        }
    }

    private boolean isColorUsed(String color) {
        for (House house : houses) {
            if (house.getAttribute("COLOR").equals(color)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDrinkUsed(String drink) {
        for (House house : houses) {
            if (house.getAttribute("DRINK").equals(drink)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCigaretteUsed(String cigarette) {
        for (House house : houses) {
            if (house.getAttribute("CIGARETTE").equals(cigarette)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPetUsed(String pet) {
        for (House house : houses) {
            if (house.getAttribute("PET").equals(pet)) {
                return true;
            }
        }
        return false;
    }

    private void printSolution() {
        System.out.println("\nBulmaca Çözümü:");
        System.out.println("+------------------+------------------+------------------+------------------+------------------+");
        System.out.println("|      Ev 1        |      Ev 2        |      Ev 3        |      Ev 4        |      Ev 5        |");
        System.out.println("+------------------+------------------+------------------+------------------+------------------+");
        
        printAttribute("Renk", "COLOR");
        printAttribute("Milliyet", "NATIONALITY");
        printAttribute("İçecek", "DRINK");
        printAttribute("Sigara", "CIGARETTE");
        printAttribute("Evcil Hayvan", "PET");
        
        System.out.println("+------------------+------------------+------------------+------------------+------------------+");
    }

    private void printAttribute(String label, String attribute) {
        System.out.print("| " + label + ": ");
        for (int i = 0; i < 5; i++) {
            String value = houses.get(i).getAttribute(attribute);
            System.out.print(String.format("%-14s", value) + " | ");
        }
        System.out.println();
    }
}

class House {
    private Map<String, String> attributes;

    public House() {
        this.attributes = new HashMap<>();
        attributes.put("COLOR", "?");
        attributes.put("NATIONALITY", "?");
        attributes.put("DRINK", "?");
        attributes.put("CIGARETTE", "?");
        attributes.put("PET", "?");
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }
} 