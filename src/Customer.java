package src;

public class Customer implements Comparable<Customer> {
    private final String firstName;
    private final String surName;

    int[] customTickets; // store the number of tickets which customers have bought.

    //contractor
    public Customer(String firstName, String surName, int num) {
        this.firstName = firstName;
        this.surName = surName;
        this.customTickets = new int[num];
    }

    //getter
    public String getName() {
        return firstName + " " + surName;
    }

    public int[] getArray() {
        return customTickets;
    }

    //setter
    public void orderTickets(int i, int num) {
        this.customTickets[i] += num;
    }

    public void cancelTickets(int i, int num) {
        this.customTickets[i] -= num;
    }

    @Override
    public int compareTo(Customer o) {
        return firstName.compareTo(o.firstName);
    }
}
