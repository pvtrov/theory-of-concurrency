public class Fork{
    /* Class represents fork */
    public boolean isTaken;
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

    public boolean take() {
        isTaken = true;
        return false;
    }

    public void release() {
        isTaken = false;
    }
}