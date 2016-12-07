package your.package.debug;

import android.util.Log;

public class PerformanceMonitor {

    private static final String PERFORMANCE = "PERFORMANCE";

    private static final int ERROR_THRESHOLD = 3000;
    private static final int WARNING_THRESHOLD = 1000;


    private long startTime;
    private String section = null;

    public PerformanceMonitor() {
    }
    public PerformanceMonitor(String section) {
        this.section = section;
    }


    public void startTimer(){
        startTime = System.nanoTime();
    }

       public void stopTimer(){
        long endTime = System.nanoTime();
        long diff = endTime - startTime;
        long milliseconds = diff /1000000;
        String profileLocation;
        if(section != null){
             profileLocation = String.format("Custom Section %s",section);
        } else {
            final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
            String className = ste[3].getFileName().replace(".java","");
            profileLocation = String.format("%s:%s",className,ste[3].getMethodName());
        }

        if(milliseconds > ERROR_THRESHOLD){
            logError(milliseconds, profileLocation);
        } else if(milliseconds > WARNING_THRESHOLD) {
            logWarning(milliseconds, profileLocation);
        } else {
            logVerbose(milliseconds, profileLocation);
        }

    }

    private void logVerbose(long milliseconds, String profileLocation) {
        if(GlobalDebug.getInstance().getDebugLevel().equals(DebugLevel.VERBOSE)) {
            Log.d(PERFORMANCE, profileLocation + " took " + milliseconds + " milliseconds");
        }
    }

    private void logWarning(long milliseconds, String profileLocation) {
        if(GlobalDebug.getInstance().getDebugLevel().ordinal() >= DebugLevel.WARNINGS.ordinal()) {
            Log.w(PERFORMANCE, profileLocation + " took " + milliseconds + " milliseconds");
        }
    }

    private void logError(long milliseconds, String profileLocation) {
        if(GlobalDebug.getInstance().getDebugLevel().ordinal() >= DebugLevel.ERRORS.ordinal()) {
            Log.e(PERFORMANCE, profileLocation + " took " + milliseconds + " milliseconds");
        }
    }

}
