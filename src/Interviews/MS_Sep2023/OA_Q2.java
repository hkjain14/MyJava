package Interviews.MS_Sep2023;

import java.util.*;

public class OA_Q2 {

    public static int solution(int[] T) {
        int ans = 0,pos=0,iterations=0,m=1000000000;
        List<Integer> list = new ArrayList<>(T.length);
        for (int j : T)
            list.add(j);

        while(!list.isEmpty()) {
            iterations++;
            int currValue = list.get(pos);
            if(currValue == 1) {
               ans = (ans%m + iterations%m)%m;
               list.remove(pos);
            } else {
                list.set(pos,currValue-1);
                pos++;
            }
            if(pos == list.size())
                pos=0;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] T = {7,7,7};
        System.out.println(solution(T));
    }
}
