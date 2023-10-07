package Interviews.MS_Sep2023;

class OA_Q1 {
    public static int solution(String S) {
        int ans = 0,i;
        for(i=0;i<S.length();i++) {
            if(S.charAt(i)=='1')
                break;
        }
        String newStri = S.substring(i);
        if(newStri.length() == 0)
            return 0;
        ans+=newStri.length()-1;
        for(int j=0;j<newStri.length();j++) {
            if(newStri.charAt(j) == '1')
                ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "1";
        System.out.println(solution(s));
    }
}