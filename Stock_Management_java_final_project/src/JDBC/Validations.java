package JDBC;

import java.util.Scanner;

public class Validations {
    Scanner scanner = new Scanner(System.in);

//    public int IntValidation(){
//        int value = 0;
//        while(true){
//            try{
//                value = scanner.nextInt();
//                break;
//            } catch (Exception exception){
//                System.out.println("Input must be number!");
//                break;
//            }
//        }
//        return value;
//    }

    public int IntValidation() {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = scanner.nextInt();
                validInput = true;
            } catch (Exception exception) {
                System.out.println("\nInput must be a number!");
                scanner.nextLine(); // consume the invalid input
                System.out.print("\n-> Choose option (1-5): ");
            }
        }
        scanner.nextLine(); // consume the trailing newline character
        return value;
    }

    public int IntValidationForSearch() {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = scanner.nextInt();
                validInput = true;
            } catch (Exception exception) {
                System.out.println("\nInput must be a number!");
                scanner.nextLine(); // consume the invalid input
                System.out.print("\n-> Choose option (1-3): ");
            }
        }
        scanner.nextLine(); // consume the trailing newline character
        return value;
    }

    public String StringValidation(){
        String value = "";
        boolean validInput = false;
        while(!validInput){
            try{
                value = scanner.nextLine();
                validInput = true;
            } catch(Exception exception){
                System.out.println("\nInvalid input");
                scanner.nextLine();
                System.out.print("\n-> Choose: ");
            }
        }
        return value;
    }

    public double DoubleValidation(){
        double value = 0;
        boolean validInput = false;
        while(!validInput){
            try{
                value = scanner.nextDouble();
                validInput = true;
            } catch (Exception exception){
                System.out.println("\nInvalid Input");
                scanner.nextLine();
                System.out.print("\n-> Choose: ");
            }
        }
        scanner.nextLine(); // consume the trailing newline character
        return value;
    }

}
