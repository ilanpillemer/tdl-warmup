package befaster

import befaster.runner.ClientRunner
import befaster.runner.RunnerAction._

object BeFasterApp extends App {
  /**
    * ~~~~~~~~~~ Running the system: ~~~~~~~~~~~~~
    *
    * From command line:
    *     sbt "run $ACTION"
    *
    * From IDE:
    *     Set the value of the `actionIfNoArgs`
    *     Run this class from IDE.
    *
    * Available actions:
    *     * getNewRoundDescription - Get the round description (works only once per round).
    *     * testConnectivity       - Test you can connect to the server and you have implemented the right method.
    *     * deployToProduction     - Release your code. Real requests will be used to test your solution.
    *                                If your solution is wrong you get a penalty of 10 minutes.
    *                                After you fix the problem, you should deploy a new version into production.
    *
    *
    *
    * ~~~~~~~~~~ The workflow ~~~~~~~~~~~~~
    *
    * +------+----------------------------------------+-----------------------------------------------+
    * | Step |      IDE                               |         Web console                           |
    * +------+----------------------------------------+-----------------------------------------------+
    * |  1.  |                                        | Start a session: http://run.befaster.io:8111  |
    * |  2.  |                                        | Configure your email                          |
    * |  3.  | Set the email variable                 |                                               |
    * |  4.  |                                        | Start a challenge, should display "Started"   |
    * |  5.  | Run "getNewRoundDescription"           |                                               |
    * |  6.  | Read description from ./challenges     |                                               |
    * |  7.  | Implement the required method in       |                                               |
    * |      |   ./src/main/scala/befaster/solutions  |                                               |
    * |  8.  | Run "testConnectivity", observe output |                                               |
    * |  9.  | If ready, run "deployToProduction"     |                                               |
    * | 10.  |                                        | Type "done"                                   |
    * | 11.  |                                        | Check failed requests                         |
    * | 12.  |                                        | Go to step 5.                                 |
    * +------+----------------------------------------+-----------------------------------------------+
    */
  ClientRunner.forUserWithEmail("your_email_here")
    .withServerHostname("run.befaster.io")
    .withActionIfNoArgs(testConnectivity)
    .start(args)
}
