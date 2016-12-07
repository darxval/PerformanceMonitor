package your.package.debug;

public class GlobalDebug {
    private DebugLevel debugLevel = DebugLevel.OFF;

    private static GlobalDebug ourInstance = new GlobalDebug();

    public static GlobalDebug getInstance() {
        return ourInstance;
    }

    private GlobalDebug() {
    }

    public DebugLevel getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(DebugLevel debugLevel) {
        this.debugLevel = debugLevel;
    }
}
