package dhbw.datamodel;

import dhbw.AlertBox;
import dhbw.KassensystemManagerController;
import dhbw.sa.kassensystem_rest.exceptions.DataException;
import dhbw.sa.kassensystem_rest.exceptions.MySQLServerConnectionException;
import dhbw.sa.kassensystem_rest.exceptions.NoContentException;
import dhbw.sa.kassensystem_rest.exceptions.OrderNotFoundException;
import org.joda.time.DateTime;

import java.io.*;
import java.util.ArrayList;


public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String filename = "errorlog.txt";

    /**
     * Alle Exceptions des {@link KassensystemManagerController} werden hier abgefangen.
     * Die Exceptions {@link DataException}, {@link MySQLServerConnectionException}, {@link NoContentException},
     * und {@link OrderNotFoundException} werden in einer {@link AlertBox} dem Nutzer dargestellt. Außerdem werden
     * diese und alle anderen Exceptions in einer logging-Datei mit dem Namen {@param filename} abgespeichert.
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        try {
            ArrayList<String> logging = null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                logging = new ArrayList<>();
                while(reader.ready()) {
                    logging.add(reader.readLine());
                }
            } catch (IOException e1) {
                //datei existiert noch nicht, also auch kein zu speichernder Inhalt
                e1.printStackTrace();
            }

            BufferedWriter logger = new BufferedWriter(new FileWriter(filename));
            if (logging != null) {
                for(String line: logging) {
                    logger.write(line);
                    logger.newLine();
                }
            }

            Throwable rootCause = getRootCause(e);

            String loggingText = rootCause.getMessage();

            String date = DateTime.now().toString("dd.MM.yyyy kk:mm:ss");
            logger.write(date + "   " + loggingText);

            //ermitteln des StackTraces als String
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            rootCause.printStackTrace(pw);
            String stackTraceString = sw.toString();

            logger.newLine();
            logger.write(stackTraceString);

            logger.close();

            if(rootCause instanceof DataException
                    || rootCause instanceof MySQLServerConnectionException
                    || rootCause instanceof OrderNotFoundException
                    || rootCause instanceof NoContentException)
                AlertBox.display("Error", rootCause.getMessage());
            else
                AlertBox.display("DEBUGGING ERROR", "DEBUGGING ERROR\n" + rootCause.getMessage());

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Ermittelt den ursprünglichen Grund (RootCause) für eine Exception.
     * @param e Exception, deren RootCause ermittelt werden soll.
     * @return {@link Throwable} RootCause der Exception e.
     */
    Throwable getRootCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;

        while(null != (cause = result.getCause())  && (result != cause) ) {
            result = cause;
        }
        return result;
    }
}
