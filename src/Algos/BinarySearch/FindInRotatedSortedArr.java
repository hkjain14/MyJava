package Algos.BinarySearch;

public class FindInRotatedSortedArr {
    //https://www.geeksforgeeks.org/search-an-element-in-a-sorted-and-pivoted-array/ : Approach 2 : Single pass BS

    static int findNum(int[] arr,int key, int l, int r) {
        if(l>r)
            return -1;

        int mid=(l+r)/2;
        if(arr[mid]==key)
            return mid;

        if (arr[l] <= arr[mid]) {
            if (key >= arr[l] && key <= arr[mid])
                return findNum(arr,key, l, mid - 1);
            return findNum(arr,key, mid + 1, r);
        }

        if (key >= arr[mid] && key <= arr[r])
            return findNum(arr, key,mid + 1, r);
        return findNum(arr,key, l, mid - 1);
    }

    public static void main(String[] args) {
        int[] arr= {5, 6, 7, 8, 9, 10, 1, 2, 3};
        int key = 8;
        System.out.println(findNum(arr,key,0,arr.length-1));
    }

}
