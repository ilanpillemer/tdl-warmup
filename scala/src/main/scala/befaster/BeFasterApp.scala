package befaster

import befaster.runner.ClientRunner
import befaster.runner.RunnerAction._

object BeFasterApp extends App {
  ClientRunner.forUserWithEmail("your_email_here")
    .withServerHostname("hostname_goes_here")
    .withActionIfNoArgs(connectivityTest)
    .start(args)
}
