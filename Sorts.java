/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class Sorts
{
    // bubble sort
    public static void bubbleSort(int[] A)
    {
		// start of modification (Author: Cassandra Jacklya)
		// 4th August 2020
		int pass, temp;
		boolean sorted;
		pass = 0;
		do
		{
			//used as a loop breaker to stop sorting when no swap is needed
			sorted = true;
			for (int ii = 0; ii < A.length-1-pass; ii++) 
				//does not check the last P sorted values
				// ...as the larger values are already in place after each pass
			{
				if (A[ii] > A[ii+1])
				{
					temp = A[ii];		//start of swap operation
					A[ii] = A[ii+1];
					A[ii+1] = temp;
					sorted = false;
				}
			}
			pass = pass + 1;
		} while (sorted == false);
    }//bubbleSort()

    // selection sort
    public static void selectionSort(int[] A)
    {
		int temp, minIdx;
		for (int nn = 0; nn < A.length; nn++)
		{
			minIdx = nn;		//to be used for comparison each pass
			for (int jj = nn+1; jj < A.length; jj++)
			{
				if (A[jj] < A[minIdx])
				{
					minIdx = jj;	//minimum number changes each time a number is deemed smaller
				}					// ...when compared to current minIdx
			}
			temp = A[minIdx];
			A[minIdx] = A[nn];
			A[nn] = temp;
		}
    }// selectionSort()

    // insertion sort
    public static void insertionSort(int[] A)
    {
		int ii, temp;
		for (int nn = 1; nn < A.length; nn++)
		{
			ii = nn;
			while ((ii > 0) && (A[ii-1] > A[ii])) 
			{
				temp = A[ii];
				A[ii] = A[ii-1];
				A[ii-1] = temp;
				ii = ii - 1;
			}
		}
		//end of modification (Cassandra Jacklya)
		// 4th August 2020
    }// insertionSort()

    // mergeSort - front-end for kick-starting the recursive algorithm
    public static void mergeSort(int[] A)
    {
		mergeSortRecurse(A,0,A.length-1);
    }//mergeSort()
	
	//start of modification (Cassandra Jacklya)
	//30th September 2020
    private static void mergeSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
		int midIdx;
		if (leftIdx < rightIdx) {
			midIdx = (leftIdx + rightIdx) / 2;
			mergeSortRecurse(A,leftIdx,midIdx);
			mergeSortRecurse(A,midIdx+1,rightIdx);
			merge(A,leftIdx,midIdx,rightIdx);
		}
    }//mergeSortRecurse()
	
    private static void merge(int[] A, int leftIdx, int midIdx, int rightIdx)
    {
		int ii, jj, kk;
		int[] tempArr = new int[rightIdx-leftIdx+1];
		ii = leftIdx;
		jj = midIdx + 1;
		kk = 0;
		while (ii <= midIdx && jj <= rightIdx) {
			if (A[ii] <= A[jj]) {
				tempArr[kk] = A[ii];
				ii++;
			}
			else {
				tempArr[kk] = A[jj];
				jj++;
			}
			kk++;
		}
		
		for (int i = ii; i <= midIdx; i++) {
			tempArr[kk] = A[i];
			kk++;
		}
		
		for (int j = jj; j <= rightIdx; j++) {
			tempArr[kk] = A[j];
			kk++;
		}
		
		for (int k = leftIdx; k <= rightIdx; k++) {
			A[k] = tempArr[k-leftIdx];
		}
    }//merge()
	//end of modification (Cassandra Jacklya) 30th September 2020

    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSort(int[] A)
    {
		quickSortRecurse(A,0,A.length-1);
    }//quickSort()
	
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
		int pivotIdx, newPivotIdx;
		if (rightIdx > leftIdx) {
			//pivotIdx = leftIdx;
			newPivotIdx = doPartitioning(A,leftIdx,rightIdx);
			quickSortRecurse(A,leftIdx,newPivotIdx);
			quickSortRecurse(A,newPivotIdx + 1, rightIdx);
		}
    }//quickSortRecurse()
	
	//implemented the inward searching and swapping if values are in incorrect order
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx)
    {
		int newPivotIdx, pivotVal, curIdx, temp;
		pivotVal = A[leftIdx];
		
		int i = leftIdx - 1;
		int j = rightIdx + 1;
		
		while (i < j) {
			do {
				i++;
			} while (A[i] < pivotVal);
			do {
				j--;
			} while (A[j] > pivotVal);
			if (i < j) {
				swap(A,i,j);
			}
		} 
		swap(A,leftIdx,j);
		return j;	// TEMP - Replace this when you implement QuickSort
	} //doPartitioning
	
	//--------------------------------quickSort3way sorting algorithm methods--------------------------
	/***************************************************************************************************
	* Code concept from https://www.techiedelight.com/quicksort-using-dutch-national-flag-algorithm/
	*
	***************************************************************************************************/
	//since java only can be passed as value, a class needs to be created for start and end indexes
	static class pair {
	private int x;
	private int y;
	
	pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}

	public static void quickSort3way(int[] A)
    {
		quickSort3wayRec(A,0,A.length-1);
    }
	
    private static void quickSort3wayRec(int[] A, int leftIdx, int rightIdx)
    {
		if (rightIdx > leftIdx) {
			pair pivot = partition3way(A,leftIdx,rightIdx);
			quickSort3wayRec(A,leftIdx,pivot.getX());	//recur on sub-array elements less than pivot
			quickSort3wayRec(A,pivot.getY(),rightIdx);	//recur on sub-array elements more than pivot
		}
    }
	
	//partitions using the Dutch National Flag Algorithm
    private static pair partition3way(int[] A, int leftIdx, int rightIdx)
    {
		int mid = leftIdx;
		int pivot = A[rightIdx];
		while (mid <= rightIdx) {
			if (A[mid] < pivot) {
				swap(A,leftIdx,mid);
				++leftIdx;
				++mid;
			}
			else if (A[mid] == pivot) {
				++mid;
			}
			else if (A[mid] > pivot) {
				swap(A,mid,rightIdx);
				--rightIdx;
			}
		}
			
		//handles two elements separately
		if (rightIdx - leftIdx <= 1) {
			if (A[leftIdx] < A[rightIdx]) {
				swap(A,leftIdx,rightIdx);
			}
		}
		return new pair(leftIdx-1,mid);	//[start,mid-1] containes all the occurrences of the selected pivot	
										//hence, a new pair of start and end index for next recursion procedure
	}
	//------------------------------end of quickSort3way methods--------------------------------------
	
	//------------------------------quickSortMedian sorting algorithm methods----------------------------
	public static void quickSortMedian(int[] A)
    {
		quickSortMedianRec(A,0,A.length-1);
    }
	
    private static void quickSortMedianRec(int[] A, int leftIdx, int rightIdx)
    {
		int newPivotIdx;
		int median;
		if (leftIdx < rightIdx) {
			median = median3(A,leftIdx,rightIdx);
			newPivotIdx = partitionMed(A,leftIdx,rightIdx, median);
			quickSortMedianRec(A,leftIdx,newPivotIdx);
			quickSortMedianRec(A,newPivotIdx + 1, rightIdx);
		}
    }
	
	private static int partitionMed(int[] A, int leftIdx, int rightIdx, int pivot)
    {
		int i = leftIdx - 1;
		int j = rightIdx;
		
		while (i < j) {
			do {
				i++;
			} while (A[i] < pivot);
			do {
				j--;
			} while (A[j] > pivot && j > 0);
			if (i < j) {
				swap(A,i,j);
			}
		} 
		swap(A,i,rightIdx);
		return i;
	}
	
	private static int median3(int[] A, int leftIdx, int rightIdx) {
		int center = (leftIdx + rightIdx) / 2;
		
		if (A[leftIdx] > A[center]) {
			swap(A,leftIdx,center);
		}
		if (A[leftIdx] > A[rightIdx]) {
			swap(A,leftIdx,rightIdx);
		}
		if (A[center] > A[rightIdx]) {
			swap(A,center,rightIdx);
		}
		swap(A,center,rightIdx);
		return (A[rightIdx]);
	}
	//------------------------------------end of quickSortMedian methods------------------------------
	
	//swap method
	private static void swap(int[] A, int swapOne, int swapTwo) {
		int temp;
		temp = A[swapOne];
		A[swapOne] = A[swapTwo];
		A[swapTwo] = temp;
	}
	
	/***************************************************
	* The code below is another way of sorting the data
	****************************************************/
		//curIdx = leftIdx;
		/* for (int i = leftIdx; i < rightIdx; i++) {
			if (A[i] < pivotVal) {
				temp = A[i];
				A[i] = A[curIdx];
				A[curIdx] = temp;
				curIdx++;
			}
		} */
		/* newPivotIdx = curIdx;
		A[rightIdx] = A[newPivotIdx];
		A[newPivotIdx] = pivotVal;
		return newPivotIdx;	*/
		
}//end Sorts class
