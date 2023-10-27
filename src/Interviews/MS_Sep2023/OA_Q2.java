package Interviews.MS_Sep2023;

import java.util.*;

public class OA_Q2 {

    public static int solution(int[] T) {
        int iterations = 0, position = 0;
        List<Integer> inputArrayList = new ArrayList<>();
        for (int j : T)
            inputArrayList.add(j);

        int totalTime = 0;
        int modulo = 1000000000;

        while(!inputArrayList.isEmpty()) {
            int presentElement = inputArrayList.get(position);
            iterations++;

            if(presentElement == 1) {
               inputArrayList.remove(position);
               totalTime = (totalTime % modulo + iterations % modulo) % modulo;
            } else {
                position++;
                inputArrayList.set(position - 1, presentElement - 1);
            }
            if(inputArrayList.size() == position)
                position=0;
        }
        return totalTime;
    }

    public static void main(String[] args) {
        int[] T = {3,1,2};
        System.out.println(solution(T));
    }
}
