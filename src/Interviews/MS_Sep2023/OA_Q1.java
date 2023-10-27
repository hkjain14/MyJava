package Interviews.MS_Sep2023;

class OA_Q1 {
    public static int solution(String S) {
        int pointer;
        for(pointer=0; pointer<S.length(); pointer++) {
            if(S.charAt(pointer) == '1')
                break;
        }
        String modifiedString = S.substring(pointer);

        if(modifiedString.length() == 0)
            return 0;

        int operations = -1;
        for(int i=0; i<modifiedString.length();i++) {
            if(modifiedString.charAt(i) == '1')
                operations++;
        }
        operations += modifiedString.length();
        return operations;
    }

    public static void main(String[] args) {
        String s = "1111010101111";
        System.out.println(solution(s));
    }
}