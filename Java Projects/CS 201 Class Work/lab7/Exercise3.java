package labs.lab7;

import java.util.Scanner;

public class Exercise3 {
// Stefan Urosevic 11/4/2020
	public static void main(String[] args) {
		String list[] = {"c", "html", "java", "python", "ruby", "scala"};
		
		Scanner in = new Scanner(System.in);
		System.out.print("Search a programming language: ");
		String search = in.nextLine();
		in.close();
		
		int binarySearch = binarySearch(list, search);
		
		if (binarySearch < 0) {
			System.out.println("No Results Found");
		} else {
			System.out.println("'" + list[binarySearch] + "' was found as a match for '" + search + "'");
		}
	}

	// Binary Search
	private static int binarySearch(String[] l, String s) {
		return binarySearch(l, s, 0, l.length - 1);
	}

	private static int binarySearch(String[] l, String s, int left, int right) {
		if (left > right) {
			return -1;
		}
		
		int middle = left + (right - left) / 2;
		int result = l[middle].compareToIgnoreCase(s);
		if (result == 0) {
			return middle;
		} else if (result > 0) {
			return binarySearch(l, s, left, middle - 1);
		}
		return binarySearch(l, s, middle + 1, right);
	}
}
