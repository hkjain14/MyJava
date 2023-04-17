package Algos;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1,3,5,7,9,11,13,15,19};
        int numToFind = 19, h = arr.length - 1, l = 0, mid = (l+h)/2, iterations = 0;
        while(true) {
            if(arr[mid] == numToFind) {
                System.out.println("Number found at position: " + (mid+1) + ". In " + iterations + " iterations");
                break;
            }
            if(l>=h) {
                System.out.println("Number absent");
                break;
            }
            if(arr[mid] > numToFind) {
                h = mid;
            } else if(arr[mid] < numToFind) {
                l = mid+1;
            }
            mid = (l+h)/2;
            iterations++;
        }
    }
}
