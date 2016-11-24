package befaster;

import befaster.runner.ClientRunner;
import befaster.runner.RunnerAction;

public class BeFasterApp {

    /**
     * ~~~~~~~~~~ Running the system: ~~~~~~~~~~~~~
     *
     *   From command line:
     *          ./gradlew run -Daction=$ACTION
     *
     *   From IDE:
     *      Set the value of the `actionIfNoArgs`
     *      Run this class from IDE.
     *
     *   Available actions:
     *        * getNewRoundDescription - Get the round description (works only once per round).
     *        * connectivityTest       - Test you can connect to the server and you have implemented the right method.
     *        * deployToProduction     - Release the software. Real requests will be sent to your method.
     *
     *
     * ~~~~~~~~~~ The workflow ~~~~~~~~~~~~~
     *
     *   +------+----------------------------------------+-----------------------------------------------+
     *   | Step |      IDE                               |         Web console                           |
     *   +------+----------------------------------------+-----------------------------------------------+
     *   |  1.  |                                        | Start a session: http://run.befaster.io:8111  |
     *   |  2.  |                                        | Configure your email                          |
     *   |  3.  | Set the email variable                 |                                               |
     *   |  4.  |                                        | Start a challenge, should display "Started"   |
     *   |  5.  | Run "getNewRoundDescription"           |                                               |
     *   |  6.  | Read description from ./challenges     |                                               |
     *   |  7.  | Implement the required method in       |                                               |
     *   |      |   ./src/main/java/solutions            |                                               |
     *   |  8.  | Run "connectivityTest", observe output |                                               |
     *   |  9.  | If ready, run "deployToProduction"     |                                               |
     *   | 10.  |                                        | Type "done"                                   |
     *   | 11.  |                                        | Check failed requests                         |
     *   | 12.  |                                        | Go to step 5.                                 |
     *   +------+----------------------------------------+-----------------------------------------------+
     */
    public static void main(String[] args) {
        ClientRunner.forUserWithEmail("your_email_here")
                .withServerHostname("hostname_goes_here")
                .withActionIfNoArgs(RunnerAction.connectivityTest)
                .start(args);
    }
}
