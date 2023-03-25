package src;

public class Activity implements Comparable<Activity> {
    private final String activity;
    private int number;

    //contractor
    public Activity(String s) {
        this.activity = s;
        this.number = 0;
    }

    public Activity(String activity, int number) {
        this.activity = activity;
        this.number = number;
    }

    //getter
    public String getActivity() {
        return activity;
    }

    public int getNumber() {
        return number;
    }

    //setter
    public void setOrderNumber(int number) {
        this.number -= number;
    }

    public void setCancelNumber(int number) {
        this.number += number;
    }

    @Override
    public int compareTo(Activity o) {
        return activity.compareTo(o.activity);
    }

}

