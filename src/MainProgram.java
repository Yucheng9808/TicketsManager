package src;

import java.io.*;
import java.util.*;
import java.lang.*;

public class MainProgram {
    static SortedArrayList<Customer> customList = new SortedArrayList<>(); // store customer data
    static SortedArrayList<Activity> activeList = new SortedArrayList<>();// store activities data
    static MainProgram mainProgram = new MainProgram();

    public static void main(String[] args) throws IOException {

        if (activeList.size() == 0) {   //read file when list empty
            mainProgram.readDoc();
        }

        String i;
        i = "0";

        while (!(i.equals("f"))) {  // when type "f" quit
            mainProgram.printMenu();
            //collect user input
            Scanner userInput = new Scanner(System.in);
            i = userInput.nextLine();

            //switch loop to deal choose option
            switch (i) {
                case "f":
                    break;
                case "a":
                    mainProgram.displayInfoActi(); //display activities
                    break;
                case "c":
                    mainProgram.displayInfoCus(); // display customers
                    break;
                case "t":
                    mainProgram.UpdateBought(); // update order
                    break;
                case "r":
                    mainProgram.UpdateCancel(); // update cancel
                    break;
                default:
                    //notice if user type no-valid value
                    System.out.println("Please type a valid character.");
            }
            System.out.println(" ");
        }
        System.out.println("The program has finished.");
    }

    // print main menu
    public void printMenu() {
        System.out.println(" ");
        System.out.println("Welcome to Ticket manager!");
        System.out.println(" ");
        System.out.println(
                "f - to finish running the program.\n" +
                        "a - to display on the screen information about all the activities.\n" +
                        "c - to display on the screen information about all the customers.\n" +
                        "t - to update the stored data when tickets are bought by one of the registered customers.\n" +
                        "r - to update the stored data when a registered customer cancels tickets for a booking.");
        System.out.println("Please select one option and type in the character: ");
    }

    // read data form "input.txt"
    public void readDoc() {
        String inputPath;
        inputPath = "src/input.txt";
        File input = new File(inputPath);
        try {
            Scanner s = new Scanner(input);
            ArrayList<String> list = new ArrayList<>();
            while (s.hasNext()) {
                String data = s.nextLine();
                list.add(data); // store data from file in a list.
            }
            readActivity(list); // separate data to 2 ArrayList
            readCustomer(list);
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred. Cannot find " + inputPath);
            e.printStackTrace();
        }
    }

    // store activities data to Arraylist
    public void readActivity(ArrayList<String> data) {
        for (int i = 1; i <= (2 * Integer.parseInt(data.get(0))); i += 2) {
            Activity activity = new Activity(data.get(i), Integer.parseInt(data.get(i + 1)));
            activeList.add(activity);
        }
    }

    //store customs data to Arraylist
    public void readCustomer(ArrayList<String> data) {
        for (int i = ((2 * Integer.parseInt(data.get(0)) + 2)); i < data.size(); i++) {
            String[] name = data.get(i).split(" ");
            Customer customer = new Customer(name[0], name[1],
                    Integer.parseInt(data.get(0)));
            customList.add(customer);
        }
    }

    public void displayInfoActi() {
        for (int i = 0; i < activeList.size(); i++) {
            System.out.println(activeList.get(i).getActivity() + " " + activeList.get(i).getNumber());
        }
    }

    public void displayInfoCus() {
        for (int i = 0; i < customList.size(); i++) {
            System.out.println(customList.get(i).getName());
        }
    }

    public void UpdateBought() throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter customer name: ");
        String name = s.nextLine();
        int a = 0;
        int j = 0;
        while (j < customList.size()) {                         // compare between input and arraylist.name
            if (customList.get(j).getName().equals(name)) {
                a++;
                break;
            }
            j++;
        }
        if (a == 0) {
            System.out.println("The customer is not registered");   //if can't find name in arraylist,
            return;                                                 //report an error
        }
        System.out.println("Please enter the activities and number of tickets you want to order: ");
        String[] act = s.nextLine().split(" ");
        if (act.length < 2) {                       // if user don't enter 2 value, report error
            System.out.println("Didn't enter activities and number of tickets in sametime.");
            return;
        }
        for (int i = act[1].length(); --i >= 0; ) {         // if user don't enter valid number of tickets
            if (!Character.isDigit(act[1].charAt(i))) {     //report error
                System.out.println("Sorry, the number of tickets  you entered is not available.");
                return;
            }
        }
        int actIndex = 0;
        int test = 0;
        for (int i = 0; i < activeList.size(); i++) {
            if (act[0].equals(activeList.get(i).getActivity())) {  // assess weather input activity are same to arraylist
                actIndex = i;
                test++;

                // assess weather enough to sell the tickets of the activities
                if ((activeList.get(i).getNumber() - Integer.parseInt(act[1])) < 0) {
                    System.out.println("Sorry, there are not enough tickets for this activities.");
                    writeLetter(name + ": \n");
                    writeLetter("Sorry, there are not enough tickets of " + act[0] + " activities.\n"
                            + "It is only " + activeList.get(i).getNumber() + " left.\n");
                    return;
                }
            }
        }
        if (test == 0) {        //if enter activity not in file, report error
            System.out.println("Sorry, the activity you entered is not available.");
            return;
        }
        activeList.get(actIndex).setOrderNumber(Integer.parseInt(act[1])); //update (Number of tickets left )arraylist
        customList.get(j).orderTickets(actIndex, Integer.parseInt(act[1])); // update tickets number which custom ordered
        System.out.println("Order successful! Information has updated.");
        writeDoc();     // update input.txt
    }

    public void UpdateCancel() throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter customer name: ");
        String name = s.nextLine();
        int a = 0;
        int j = 0;
        while (j < customList.size()) {   //weather input and arraylist.name are same
            if (customList.get(j).getName().equals(name)) {
                a++;
                break;
            }
            j++;
        }
        if (a == 0) {
            System.out.println("The customer is not registered.");
            return;
        }
        System.out.println("Please enter the activities and number of tickets you want to order: ");
        String[] act = s.nextLine().split(" ");
        if (act.length < 2) {
            System.out.println("Didn't enter activities and number of tickets in sametime.");
            return;
        }
        for (int i = act[1].length(); --i >= 0; ) {
            if (!Character.isDigit(act[1].charAt(i))) {
                System.out.println("Sorry, the number of tickets  you entered is not available.");
                return;
            }
        }
        int actIndex = 0;
        int test = 0;
        for (int i = 0; i < activeList.size(); i++) {
            if (act[0].equals(activeList.get(i).getActivity())) {
                actIndex = i;
                test++;
                if ((customList.get(j).getArray()[actIndex] - Integer.parseInt(act[1])) < 0) {
                    System.out.println("Sorry, Cancellation of tickets in excess of the number previously purchased.");
                    writeLetter(name + ": \n");                  // write into letter
                    writeLetter("Sorry, Cancellation of tickets in excess of the number previously purchased.\n"
                            + "you only purchased " + customList.get(j).getArray()[actIndex] + " ticket(s).\n");
                    return;
                }
            }
        }
        if (test == 0) {
            System.out.println("Sorry, the activity you entered is not available.");
            return;
        }
        activeList.get(actIndex).setCancelNumber(Integer.parseInt(act[1]));
        customList.get(j).cancelTickets(actIndex, Integer.parseInt(act[1]));
        System.out.println("Cancel successful! Information has updated.");
        writeDoc();
    }

    public void writeDoc() {
        String inputPath;
        inputPath = "src/input.txt";
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(inputPath);

            printWriter.println(activeList.size());
            for (int i = 0; i < activeList.size(); i++) {
                printWriter.println(activeList.get(i).getActivity());
                printWriter.println(activeList.get(i).getNumber());
            }
            printWriter.println(customList.size());
            for (int i = 0; i < customList.size(); i++) {
                printWriter.println(customList.get(i).getName());
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void writeLetter(String s) throws IOException {
        String filepath = "src/letter.txt";
        FileWriter fileWriter = new FileWriter(filepath, true);
        fileWriter.write(s);
        fileWriter.close();
    }
}

