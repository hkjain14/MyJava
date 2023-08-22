package Algos.BinarySearch;

public class BS {
    public static void main(String[] args) {
        int[] arr = {1,3,5,7,9,11,13,19};
        int numToFind = 11, r = arr.length - 1, l = 0, mid = (l+r)/2, iterations = 0;
        while(true) {
            if(arr[mid] == numToFind) {
                System.out.println("Number found at position: " + (mid+1) + ". In " + iterations + " iterations");
                break;
            }
            if(l>=r) {
                System.out.println("Number absent");
                break;
            }
            if(arr[mid] > numToFind) {
                r = mid-1; // or r=mid; same thing
            } else {
                l = mid+1;
            }
            mid = (l+r)/2;
            iterations++;
        }
    }
}
