import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class KLLogger {
    public static void main(String[] args) {
        final IKLLogger logger1 = KLLogger.getLogger(KLLogger.class);
        // TODO:
        logger1.setLogLevel(DebugLevel.ERROR);
        logger1.debug("debug 1...");
        logger1.debug("debug 2...");
        logger1.info("info 1...");
        logger1.warn("warn 1...");
        logger1.error("error 1...");

        final IKLLogger logger2 = KLLogger.getLogger(KLLogger.class);
        logger2.setLogLevel(DebugLevel.INFO);

        logger2.debug("debug 1...");
        logger2.debug("debug 2...");
        logger2.info("info 1...");
        logger2.warn("warn 1...");
        logger2.error("error 1...");

    }

    private static IKLLogger getLogger(Class<?> logclass) {
            return new AIKLLogger(logclass.getName());
    }

}
interface IKLLogger{
    void debug(String log);
    void info(String log);
    void warn(String log);
    void error(String log);
    void setLogLevel(DebugLevel level);

    void addAppend(Appender append);
    void setAppend(Appender append);
}
class AIKLLogger implements IKLLogger{
    private DebugLevel level;
    private List<Appender> appenders;
    private String name;

    public AIKLLogger(String name) {
        this.name = name;
        appenders = new ArrayList<>();
        appenders.add(new ConsoleAppender());
    }

    public  void debug(String log){
        if(level.compareTo(DebugLevel.DEBUG) <= 0){

            for (Appender appender : appenders) {
                appender.print(log);
            }
        }
    }
    public void info(String log){
        if(level.compareTo(DebugLevel.INFO) <= 0){
            for (Appender appender : appenders) {
                appender.print(log);
            }
        }
    };
    public void warn(String log){
        if(level.compareTo(DebugLevel.WARN) <= 0){
            for (Appender appender : appenders) {
                appender.print(log);
            }
        }
    }
    public void error(String log){
        if(level.compareTo(DebugLevel.ERROR) <= 0){
            for (Appender appender : appenders) {
                appender.print(log);
            }
        }
    }

    @Override
    public void setLogLevel(DebugLevel level) {
        this.level = level;
    }

    @Override
    public void addAppend(Appender append) {
        appenders.add(append);
    }

    @Override
    public void setAppend(Appender append) {
        appenders.clear();
        appenders.add(append);
    }
}
interface   Appender{
    void print(String log);
    PrintWriter getWriter();
}

class ConsoleAppender implements Appender{
    private PrintWriter writer;

    public ConsoleAppender() {
        this.writer = new PrintWriter(System.out,true);
    }
    @Override
    public void print(String log) {
        writer.println(log);
    }

    public PrintWriter getWriter() {
        return writer;
    }
}

class  FileAppender implements Appender{
    private PrintWriter writer;
    private File targetFile;
    public FileAppender(String filePath,boolean append) throws FileNotFoundException {
        targetFile= new File(filePath);
        this.writer = new PrintWriter(new FileOutputStream(targetFile,append),true);
    }
    @Override
    public void print(String log) {
        writer.println(log);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }
}

enum DebugLevel {
    DEBUG, INFO, WARN, ERROR;
}
