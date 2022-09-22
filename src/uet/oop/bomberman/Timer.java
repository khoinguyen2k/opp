package uet.oop.bomberman;

public class Timer {
    private long start;
    private long end;
    public Timer() {
        start =System.currentTimeMillis();
        end =start;
    }
    public boolean isElapsed(long step) {
        if (end -start >step) {
            start =end;
            return true;
        } else {
            end =System.currentTimeMillis();
            return false;
        }
    }
}
