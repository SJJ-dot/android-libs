package sjj.alog;

import android.util.Log;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sjj.alog.file.LogFile;

/**
 * Created by SJJ on 2017/3/5.
 */

class Logger {

    private Config config;
    private LogFile logFile;
    private String lineSeparator = getLineSeparator();
    private SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss ", Locale.CHINA);

    Logger(Config config) {
        this.config = config;
        if (config.hold) {
            logFile = new LogFile(config.getDir());
            if (config.deleteOldLog)
                logFile.deleteOldLogFile();
        }
    }

    private boolean isEnable(int lev) {
        return config.enable && (config.multiple && lev >= config.lev || !config.multiple && config.lev == lev);
    }

    void l(int lev, String tag, String msg) {
        l(lev, tag, msg, null);
    }

    void l(int lev, String tag, String msg, Throwable throwable) {
        String s = msg + (throwable == null ? "" : lineSeparator) + throwable(throwable);
        writeToFile(lev, tag, s);
        if (!isEnable(lev)) return;
        print(lev, tag, s);
    }

    private void print(int lev, String tag, String s) {
        if (s.length() > 3500) {
            print(lev, tag, s.substring(0, 3000));
            print(lev, tag, s.substring(3000,s.length()));
        } else {
            tag = "Log:" + tag;
            switch (lev) {
                case Config.INFO:
                    Log.i(tag, s);
                    break;
                case Config.DEBUG:
                    Log.d(tag, s);
                    break;
                case Config.WARN:
                    Log.w(tag, s);
                    break;
                case Config.ERROR:
                    Log.e(tag, s);
                    break;
            }
        }
    }

    private boolean isHoldLog(int lev) {
        return config.hold && (lev >= config.holdLev && config.holdMultiple || !config.holdMultiple && lev == config.holdLev);
    }

    private void writeToFile(int lev, String tag, String msg) {
        if (!isHoldLog(lev)) return;
        StringBuilder sb = new StringBuilder(hms.format(new Date()));
        switch (lev) {
            case Config.INFO:
                sb.append("I:");
                break;
            case Config.DEBUG:
                sb.append("D:");
                break;
            case Config.WARN:
                sb.append("W:");
                break;
            case Config.ERROR:
                sb.append("E:");
                break;
        }
        if (logFile != null)
            logFile.push(sb
                    .append(tag)
                    .append(lineSeparator)
                    .append(msg)
                    .toString());
    }

    private String getLineSeparator() {
        try {
            String property = System.getProperty("line.separator");
            if (property == null) {
                return "\n";
            }
            return property;
        } catch (Exception e) {
            return "\n";
        }
    }

    private String throwable(Throwable throwable) {
        if (throwable == null) return "";
        if (throwable instanceof UnknownHostException) return "UnknownHostException";
        return Log.getStackTraceString(throwable);
//        StringBuilder buffer = new StringBuilder();
//        buffer.append(throwable.getClass()).append(", ").append(throwable.getMessage()).append("\n");
//        while (throwable != null) {
//            for (StackTraceElement element : throwable.getStackTrace()) {
//                buffer.append(element.toString()).append("\n");
//            }
//            throwable = throwable.getCause();
//            if (throwable != null)
//                buffer.append("caused by ");
//        }
//        return buffer.toString();
    }
}
