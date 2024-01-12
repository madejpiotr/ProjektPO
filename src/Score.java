public class Score {
    public int key;
    public int overallCookies;
    public int cursorCookiePower;
    public int assistantsCookiePower;

    public Score(int overallCookies, int cursorCookiePower, int assistantsCookiePower) {
        this.overallCookies = overallCookies;
        this.cursorCookiePower = cursorCookiePower;
        this.assistantsCookiePower = assistantsCookiePower;
    }

    public Score(int key, int overallCookies, int cursorCookiePower, int assistantsCookiePower) {
        this.key = key;
        this.overallCookies = overallCookies;
        this.cursorCookiePower = cursorCookiePower;
        this.assistantsCookiePower = assistantsCookiePower;
    }
}
