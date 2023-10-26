package lab4;

public class Fork{
    /* Class represents fork */
    private boolean isTaken;
    private int ID;

    public Fork(int ID) {
        this.ID = ID;
        isTaken = false;
    }

    public int getID() {
        return ID;
    }
    public Fork() {
        isTaken = false;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void take() {
        isTaken = true;
    }

    public void release() {
        isTaken = false;
    }
}