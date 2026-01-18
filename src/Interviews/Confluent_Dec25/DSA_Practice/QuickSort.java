package Interviews.Confluent_Dec25.DSA_Practice;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static void quickSort(int[] arr) {
        quickSortRecursion(arr, 0, arr.length - 1);
    }

    private static void quickSortRecursion(int[] arr, int low, int high) {
        if (low >= high) return;
        // int pivotIndex = partitionRandom(arr, low, high);
        int pivotIndex = medianOfThree(arr, low, high);
        quickSortRecursion(arr, low, pivotIndex - 1);
        quickSortRecursion(arr, pivotIndex + 1, high);
    }

    // Last element pivot.
    // Not good, because O(n*n) when array already sorted.
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // pivot at end
        int i = low;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    // Optimisation 1: Random partitioning. Worst case O(n*n) -> less probable.
    private static int partitionRandom(int[] arr, int low, int high) {
        Random rand = new Random();
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, pivotIndex, high); // move random pivot to end
        return partition(arr, low, high);
    }

    // Optimisation 2: Pivot is the median of three values (at start, mid, end) of unsorted array.
    private static int medianOfThree(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        if (arr[low] > arr[mid]) swap(arr, low, mid);
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);

        swap(arr, mid, high); // move median to end
        return partition(arr, low, high);
    }

    // Optimisation 3: Dual pivot. Array divided into three partitions. Used in Java Arrays.sort() function.

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {3,1,9,2,8,6,4};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
