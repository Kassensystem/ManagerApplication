package dhbw.datamodel;

import dhbw.AlertBox;
import dhbw.sa.kassensystem_rest.exceptions.DataException;
import dhbw.sa.kassensystem_rest.exceptions.MySQLServerConnectionException;
import dhbw.sa.kassensystem_rest.exceptions.NoContentException;
import dhbw.sa.kassensystem_rest.exceptions.OrderNotFoundException;
import org.joda.time.DateTime;

import java.io.*;
import java.util.ArrayList;


public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String filename = "errorlog.txt";

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //Die Fehlermeldung in eine logging Datei speichern
        String date = DateTime.now().toString("dd.MM.yyyy kk:mm:ss");

        try {
            ArrayList<String> logging = null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                logging = new ArrayList<>();
                while(reader.ready()) {
                    logging.add(reader.readLine());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            BufferedWriter logger = new BufferedWriter(new FileWriter(filename));
            if (logging != null) {
                for(String line: logging) {
                    logger.write(line);
                    logger.newLine();
                }
            }

            Throwable ex = getCause(e);

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string

            String loggingText = ex.getMessage();
            logger.newLine();
            logger.write(sStackTrace);

            logger.write(date + "   " + loggingText);

            logger.close();

            if(ex instanceof DataException
                    || ex instanceof MySQLServerConnectionException
                    || ex instanceof OrderNotFoundException
                    || ex instanceof NoContentException)
                AlertBox.display("Error", ex.getMessage());
            else
                AlertBox.display("DEBUGGING ERROR", "DEBUGGING ERROR\n" + ex.getMessage());

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //AlertBox alertBox = new AlertBox();

        //alertBox.display("Error", e.getCause().getMessage() + "\n" + e.getCause().getStackTrace().toString());
    }

    Throwable getCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;

        while(null != (cause = result.getCause())  && (result != cause) ) {
            result = cause;
        }
        return result;
    }
}
