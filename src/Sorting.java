import java.io.IOException;
import java.util.*;

/**
 * Created by Lemekh on 04.12.2016.
 */
public class Sorting {
    private static void coctailsorting(int [] array){
        int left = 0;
        int right = array.length - 1;
        do
        {
            for (int i = left; i < right; i++)
            {
                if(array[i] > array[i+1])
                {
                    array[i] ^= array[i+1];
                    array[i+1] ^= array[i];
                    array[i] ^= array[i+1];
                }
            }
            right--;
            for (int i = right; i > left ; i--)
            {
                if(array[i] < array[i-1])
                {
                    array[i] ^= array[i-1];
                    array[i-1] ^= array[i];
                    array[i] ^= array[i-1];
                }
            }
            left++;
        } while (left <= right);

        for (int i : array) System.out.print(i + " ");
        System.out.println();   }

    private static void heapSort(final int[] array,  int count) {
        heapify(array, count);

        int end = count - 1;
        while (end > 0) {
            swap(array, end, 0);
            siftDown(array, 0, --end);
        }
    }

    private static void swap(final int[] array, final int a, final int b) {
        final int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private static void heapify(final int[] array, final int count) {
        int start = count / 2 - 1;

        while (start >= 0) {
            siftDown(array, start, count - 1);
            start--;
        }
    }

    private static void siftDown(final int[] array, final int start, final int end) {
        int root = start;

        while (root * 2 + 1 <= end) {
            int child = root * 2 + 1;
            int swap = root;
            if (array[swap] < array[child]) {
                swap = child;
            }
            if (child + 1 <= end && array[swap] < array[child + 1]) {
                swap = child + 1;
            }
            if (swap != root) {
                swap(array, root, swap);
                root = swap;
            } else {
                return;
            }
        }
    }
    public static int[] selectionsort(int[] numbers){
        for (int i = 0; i < numbers.length-1; i++)
        {
            int index = i;
            for (int j = i+1; j < numbers.length; j++)
                if (numbers[j] < numbers[index]) //Finds smallest number
                    index = j;

            int smallerNumber = numbers[index];  //Swap
            numbers[index] = numbers[i];
            numbers[i] = smallerNumber;

        }
        return numbers;
    }
    public static void sort(int[] a, int maxVal) {
        int [] bucket=new int[maxVal+1];

        for (int i=0; i<bucket.length; i++) {
            bucket[i]=0;
        }

        for (int i=0; i<a.length; i++) {
            bucket[Math.abs(a[i])]--;
        }

        int outPos=a.length;
        for (int i=0; i<bucket.length; i++) {
            for (int j=0; j<bucket[i]; j++) {
                a[outPos--]=i;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        int maxVal=10000000;
        int [] data= new int[10];
        for (int i=0; i<data.length; i++){
            Random random = new Random();
            data[i]= (int)((Math.random()*100));
        }
        Map<Integer, Long> hashMap = new HashMap<>();
        System.out.println(Arrays.toString(data));
     int key = 0;
        for (int i=1; i<=4; i++) {
            key = i;

            switch (key) {
                case 1: {
                    long strt = System.nanoTime();
                    System.out.println("Coctail sorting");
                    coctailsorting(data);
                    long end = System.nanoTime();
                    long result = end - strt;
                    hashMap.put(1,result);
                    break;
                }

                case 2: {
                    long strt = System.nanoTime();
                    System.out.println("Heap sorting");
                    heapSort(data, data.length);
                    System.out.println(Arrays.toString(data));
                    long end = System.nanoTime();
                    long result = end - strt;
                    hashMap.put(2,result);
                    break;
                }



                case 3: {
                    long strt = System.nanoTime();
                    System.out.println("Selection sort");
                    selectionsort(data);
                    System.out.println(Arrays.toString(data));
                    long end = System.nanoTime();
                    long result = end - strt;
                    hashMap.put(3,result);
                    break;
                }

                case 4: {
                    long strt = System.nanoTime();
                    System.out.println("Bucket sort");
                    sort(data, maxVal);
                    System.out.println(Arrays.toString(data));
                    long end = System.nanoTime();
                    long result = end - strt;
                    hashMap.put(4,result);
                    break;
                }

                default:
                    System.out.println("Error");
            }

        }
        long min = hashMap.get(1);
        for (long i:hashMap.values()){
            if (min>i) min=i;
        }
        int m=0;
        for (Map.Entry<Integer, Long> entry:hashMap.entrySet()){
            if (entry.getValue()==min) m=entry.getKey();
        }
        System.out.println("Найшвидший ");
        switch (m){
            case 1: {
                System.out.println("Coctail sorting");
                break;
            }
            case 2: {
                System.out.println("Heap sorting");
                break;
            }
            case 3: {
                System.out.println("Selection sort");
                break;
            }
            case 4: {
                System.out.println("Bucket sort");
                break;
            }
        }
    }
}
