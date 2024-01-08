import java.util.*;

public class Main {
    public static void main(String args[]) {
        int mod = 3125;
        
        int y2_arr[] = {0,1,2,3,4};
        int y_sq_arr[] = new int[5];
        for(int i=0;i<y2_arr.length;i++) {
            int y = 251+625*y2_arr[i];
            int y_sq = y*y;
            y_sq_arr[i] = y_sq%mod;
//            System.out.println(y_sq_arr[i]);
        }

        int x2_arr[] = {0,1,2,3,4};
        int x_sum_arr[] = new int[5];
        for(int i=0;i<x2_arr.length;i++) {
            int x = 157+625*x2_arr[i];
            int x_sq = x*x%mod;
            int x_4 = x_sq*x_sq%mod;
            int x_8 = x_4*x_4%mod;
//            System.out.println(x_sq + " " + x_4 + " " + x_8 + " ");
            x_sum_arr[i] = (x_sq + x_4 + x_8)%mod;
//            System.out.println(x_sum_arr[i]);
        }

        double x = 1.75;
//        System.out.println(x*x + x*x*x*x + x*x*x*x*x*x*x*x);
        System.out.println(Math.pow(4,6) + Math.pow(4,4) + 1);
        System.out.println(Math.sqrt(6580161));
    }
}
