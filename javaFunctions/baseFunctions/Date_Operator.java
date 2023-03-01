package baseFunctions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Date_Operator {
	static DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		calculateDurationDay();

		calculateEndDate();

		scan.close();
	}

	private static void calculateEndDate() {
		System.out.println("Please input the starting Date (yyyyMMdd):");
		LocalDate startDate = getDate();

		System.out.println("Please input the month number:");
		int durationMonth = getMonth();
		
		LocalDate closeDate = startDate.plusMonths(durationMonth);

		System.out.println("The closing Date is: " + closeDate.toString());
	}

	private static int getMonth() {
		int inputMonth = 0;
		boolean isNumber = false;

		while (!isNumber) {
			try {
				inputMonth = scan.nextInt();
				isNumber = true;
			} catch (InputMismatchException e) {
				System.out.println("Your input month number is invalid, please re-input the number:");
				scan.next();
			}
		}
		return inputMonth;
	}

	private static LocalDate getDate() {
		LocalDate date = null;
		String inputDate = "";

		while (date == null) {
			try {
				inputDate = scan.nextLine();
				date = LocalDate.parse(inputDate, dateFormatter);
			} catch (Exception e) {
				System.out.println("Your input date is invalid, please input Date in format (yyyyMMdd):");
			}
		}
		return date;
	}

	private static void calculateDurationDay() {
		System.out.println("Please input the starting Date (yyyyMMdd):");
		LocalDate startDate = getDate();

		System.out.println("Please input the closing Date (yyyyMMdd):");
		LocalDate closeDate = getDate();

		System.out.println("Duration date is: " + Math.abs(ChronoUnit.DAYS.between(startDate, closeDate)) + "day(s)");
	}
}
