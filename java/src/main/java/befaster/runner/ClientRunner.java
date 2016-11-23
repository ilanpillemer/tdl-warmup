package befaster.runner;

import befaster.solutions.Sum;
import com.google.common.io.Files;
import tdl.client.Client;
import tdl.client.ProcessingRules;
import tdl.client.actions.ClientAction;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static tdl.client.actions.ClientActions.publish;
import static tdl.client.actions.ClientActions.stop;

public class ClientRunner {
    private String hostname = "run.befaster.io";
    private RunnerAction defaultRunnerAction = RunnerAction.connectivityTest;
    private final String email;

    public static ClientRunner forUser(String email) {
        return new ClientRunner(email);
    }

    private ClientRunner(String email) {
        this.email = email;
    }

    public ClientRunner withServerHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ClientRunner withActionIfNoArgs(RunnerAction actionIfNoArgs) {
        defaultRunnerAction = actionIfNoArgs;
        return this;
    }

    public void start(String[] args) {
        RunnerAction runnerAction = extractActionFrom(args).orElse(defaultRunnerAction);

        Client client = new Client.Builder()
                .setHostname(hostname)
                .setUniqueId(email)
                .create();

        ProcessingRules processingRules = new ProcessingRules() {{
            on("display_description").call(p -> displayAndSaveDescription(asString(p[0]), asString(p[1]))).then(publish());
            on("sum").call(p -> Sum.sum(asInt(p[0]), asInt(p[1]))).then(publishIf(runnerAction.isShouldPublish()));
        }};
        client.goLiveWith(processingRules);
    }

    private static Optional<RunnerAction> extractActionFrom(String[] args) {
        String firstArg = args.length > 0 ? args[0] : null;
        return Arrays.stream(RunnerAction.values())
                .map(Enum::name)
                .filter(s -> s.equalsIgnoreCase(firstArg))
                .findFirst()
                .map(RunnerAction::valueOf);
    }

    private static ClientAction publishIf(boolean ready) {
        return ready ? publish() : stop();
    }

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

    private static String asString(String s) {
        return s;
    }

    private static int asInt(String s) {
        return Integer.parseInt(s);
    }

}
