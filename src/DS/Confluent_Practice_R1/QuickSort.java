package DS.Confluent_Practice_R1;


public class QuickSort {

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high) return;   // ✅ base case

        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);   // ✅ correct bounds
        quickSort(arr, pivotIndex + 1, high);
    }

    // Lomuto partition
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

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
