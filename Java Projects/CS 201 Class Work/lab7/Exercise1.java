package labs.lab7;

import java.util.Arrays;

public class Exercise1 {
// Stefan Urosevic 11/4/2020
	public static void main(String[] args) {
		int list[] = {10, 4, 7, 3, 8, 6, 1, 2, 5, 9};
		
		System.out.println("Before: " + Arrays.toString(list));
		BubbleSort(list);
		System.out.println("After: " + Arrays.toString(list));
	}

	// Bubble Sort Method
	public static void BubbleSort(int[] l) {
		boolean done = true;
		do {
			done = true;
			for (int i = 1; i < l.length; i++) {
				if (l[i-1] > l[i]) {
					//Swaps here
					int temp = l[i-1];
					l[i-1] = l[i];
					l[i] = temp;
					
					done = false;
				}
			}
		} while (!done);
	}

}