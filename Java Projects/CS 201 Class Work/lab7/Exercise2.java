package labs.lab7;

import java.util.Arrays;

public class Exercise2 {
// Stefan Urosevic 11/4/2020
	public static void main(String[] args) {
		String list[] = {"cat", "fat", "dog", "apple", "bat", "egg"};
		
		System.out.println("Before: " + Arrays.toString(list));
		InsertionSort(list);
		System.out.println("After: " + Arrays.toString(list));
	}

	// Insertion Sort
	private static void InsertionSort(String[] l) {
		for (int i = 1; i < l.length; i++) {
			int j = i;
			while (j > 0 && l[j-1].charAt(0) > l[j].charAt(0)) {
				String temp = l[j-1];
				l[j-1] = l[j];
				l[j] = temp;
				j--;
			}
		}
		
	}

}
