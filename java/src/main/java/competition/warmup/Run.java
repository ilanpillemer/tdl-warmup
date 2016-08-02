package competition.warmup;

import com.google.common.io.Files;
import tdl.client.Client;
import tdl.client.ProcessingRules;
import tdl.client.actions.ClientAction;

import java.io.File;
import java.io.IOException;

import static tdl.client.actions.ClientActions.publish;
import static tdl.client.actions.ClientActions.stop;

// STEP 1. Start the "sum_numbers" challenge from the remote web interface

public class Run {
    // STEP 2. Set the hostname and email
    private static final String HOSTNAME = "server_hostname";
    private static final String EMAIL = "your_email";

    // STEP 3. Run the client in trial mode
    private static final boolean I_AM_READY = false;
    /**
     * ~~~  How to run in trial mode~~~
     *
     * From command line:
     *    If you want to trial run:     ./gradlew run
     *
     * From IDE:
     *    Just Run this class from IDE.
     */

    @SuppressWarnings({"PointlessBooleanExpression", "ConstantConditions"})
    public static void main(String[] args) throws Exception {
        boolean valueFromCommandline = args.length > 0 && Boolean.parseBoolean(args[0]);
        boolean ready = I_AM_READY || valueFromCommandline;
        System.out.println("Ready ? = "+ready);

        startClient(ready);
    }

    // STEP 4. Read the instructions provided on screen. They are also saved as a file under the "challenges" folder.

    private static void startClient(final boolean ready) {
        Client client = new Client.Builder()
                .setHostname(HOSTNAME)
                .setUniqueId(EMAIL)
                .create();

        ProcessingRules processingRules = new ProcessingRules() {{
            on("display_description").call(p -> displayAndSaveDescription(p[0], p[1])).then(publish());
            // STEP 5. Uncomment the following line to register the sum method and run again
//            on("sum").call(p -> App.sum(asInt(p[0]), asInt(p[1]))).then(publishIf(ready));
        }};

        // STEP 6. Run the test (competition.warmup.AppTest) and see it fail
        // STEP 7. Fix the sum method implementation in competition.warmup.App

        client.goLiveWith(processingRules);
    }

    public static ClientAction publishIf(boolean ready) {
        return ready ? publish() : stop();
    }

    // STEP 8. If your are satisfied with the implementation, run the client in live mode !
    /**
     * ~~~  How to run in live mode~~~
     *
     * From command line:
     *    If you are ready to go live:  ./gradlew run -Dready=true
     *
     * From IDE:
     *    Set the I_AM_READY variable to "true" and run this class
     */

    // STEP 9. Go to the web interface and mark the challenge as done


    //~~~~~~~ Provided implementations ~~~~~~~~~~~~~~

    private static String displayAndSaveDescription(String label, String description) {
        System.out.println("Starting round: "+label);
        System.out.println(description);

        //Save description
        File output = new File("challenges" + File.separator + label + ".txt");
        try {
            Files.write(description.getBytes(), output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Challenge description saved to file: "+output.getPath()+".");

        return "OK";
    }

    private static double asNumber(String s) {
        return Double.parseDouble(s);
    }

    private static int asInt(String s) {
        return Integer.parseInt(s);
    }
}
