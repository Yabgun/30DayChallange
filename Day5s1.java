import java.io.*;
import java.util.*;

public class Day5s1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int q = Integer.parseInt(br.readLine().trim());
        
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            
            long[] series = calculateSeries(a, b, n);
            printSeries(series);
        }
        
        br.close();
    }
    
    private static long[] calculateSeries(int a, int b, int n) {
        long[] series = new long[n];
        
        for (int i = 0; i < n; i++) {
            long sum = a;
            
            for (int j = 0; j <= i; j++) {
                sum += (long) Math.pow(2, j) * b;
            }
            
            series[i] = sum;
        }
        
        return series;
    }
    
    private static void printSeries(long[] series) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < series.length; i++) {
            sb.append(series[i]);
            if (i < series.length - 1) {
                sb.append(" ");
            }
        }
        
        System.out.println(sb.toString());
    }
}
