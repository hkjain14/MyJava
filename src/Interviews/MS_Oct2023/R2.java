package Interviews.MS_Oct2023;

import java.util.Stack;

public class R2 {
// https://leetcode.com/problems/basic-calculator/

    public static int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        int currValue = 0;
        int prevSign = 1;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                currValue = 10 * currValue + (c - '0');
            }else if(c == '+'){
                result += prevSign * currValue;
                currValue = 0;
                prevSign = 1;
            }else if(c == '-'){
                result += prevSign * currValue;
                currValue = 0;
                prevSign = -1;
            }else if(c == '('){
                //we push the result first, then sign;
                stack.push(result);
                stack.push(prevSign);
                //reset the sign and result for the value in the parenthesis
                result = 0;
                currValue = 0;
                prevSign = 1;
            }else if(c == ')'){
                result += prevSign * currValue;
                currValue = 0;
                result *= stack.pop();    //stack.pop() is the sign before the parenthesis
                result += stack.pop();   //stack.pop() now is the result calculated before the parenthesis

            }
        }
        if(currValue != 0)
            result += prevSign * currValue;
        return result;
    }

    public static void main(String [] args) {
        // you can write to stdout for debugging purposes, e.g.
        String s = "(1+(4+5+2)-3)+(6+8)";
        System.out.println(calculate(s));

    }
}
