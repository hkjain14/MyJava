package LLD;

public class UrlShortener {
    static String genUrl(int n) {
        String ans = "";
        String map = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        while(n>0) {
            ans+=map.charAt(n%62);
            n/=62;
        }
        return ans;
    }

    static int numFromUrl(String url) {
        int num = 0;
        for(int i=0;i<url.length();i++) {
            char c = url.charAt(i);
            if(c>='a' && c<='z') {
                num+=Math.pow(62,i)*(c-'a');
            } else if(c>='A' && c<='Z') {
                num+=Math.pow(62,i)*(c-'A'+26);
            } else if(c>='0' && c<='9') {
                num+=Math.pow(62,i)*(c-'0'+52);
            }
        }
        return num;
    }

    public static void main(String[] args) {
        int n = 665762;
        String url = genUrl(n);
        int num = numFromUrl(url);
        System.out.println(url+" "+ num);
    }
}
