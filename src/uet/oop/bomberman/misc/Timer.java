package uet.oop.bomberman.misc;

public class Timer {
    private long start = System.currentTimeMillis();
    private long end;

    public Timer() {
        start = System.currentTimeMillis();
        end = start;
    }

    public boolean isElapsed(long step) {
        if (end - start > step) {
            start = end;
            return true;
        } else {
            end = System.currentTimeMillis();
            return false;
        }
    }

    public long timeElapse() {
        end = System.currentTimeMillis();
        return end - start;
    }
}
