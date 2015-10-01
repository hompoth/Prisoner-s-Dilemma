package proj;

public class Sort {
	public static void QuickSort(Other[] array){
		int left = 0;
		        int right = array.length-1;
		        quickSort(left, right, array);
	}
    private static void quickSort(int left, int right, Other[] a){
        if(left >= right)
            return;
        float pivot = a[right].getV();
        int partition = partition(left, right, pivot, a);
        quickSort(0, partition-1, a);
        quickSort(partition+1, right, a);
    }
    private static int partition(int left, int right, float pivot, Other[] a){
        int leftCursor = left-1;
        int rightCursor = right;
        while(leftCursor < rightCursor){
                while(a[++leftCursor].getV() < pivot);
                while(rightCursor > 0 && a[--rightCursor].getV() > pivot);
            if(leftCursor < rightCursor){
                break;
            }else{
                swap(a, leftCursor, rightCursor);
            }
        }
        swap(a, leftCursor, right);
        return leftCursor;
    }
    private static int N;
    public static void HeapSort(Other arr[])
    {       
        heapify(arr);        
        for (int i = N; i > 0; i--)
        {
            swap(arr,0, i);
            N = N-1;
            maxheap(arr, 0);
        }
    }     
    private static void heapify(Other arr[])
    {
        N = arr.length-1;
        for (int i = N/2; i >= 0; i--)
            maxheap(arr, i);        
    }        
    private static void maxheap(Other arr[], int i)
    { 
        int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= N && arr[left].getV() < arr[i].getV())
            max = left;
        if (right <= N && arr[right].getV() < arr[max].getV())        
            max = right;
 
        if (max != i)
        {
            swap(arr, i, max);
            maxheap(arr, max);
        }
    } 
    private static void swap(Other arr[], int i, int j)
    {
        Other tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp; 
    }    
    public static void BubbleSort(Other arr[]){
    	for(int i = 0; i < arr.length; ++i){
			for(int j = i+1; j < arr.length; ++j){
				if(arr[i].getV()<arr[j].getV()){
					Other tmp = arr[i];
					arr[i]=arr[j];
					arr[j]=tmp;
				}
			}
		}
    }
}
