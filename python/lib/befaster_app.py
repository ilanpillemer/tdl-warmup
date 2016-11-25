import sys

from runner.client_runner import start_client
from runner.runner_action import RunnerActions

"""
 ~~~~~~~~~~ Running the system: ~~~~~~~~~~~~~

 From command line:
    PYTHONPATH=lib python lib/client_runner.py $ACTION

 From IDE:
    Set the value of the `action_if_no_args`
    Run this class from IDE.

 Available actions:
     * get_new_round_description - Get the round description (works only once per round).
     * test_connectivity         - Test you can connect to the server (call any number of time)
     * deploy_to_production      - Release your code. Real requests will be used to test your solution.
                                   If your solution is wrong you get a penalty of 10 minutes.
                                   After you fix the problem, you should deploy a new version into production.

 ~~~~~~~~~~ The workflow ~~~~~~~~~~~~~

 +------+-----------------------------------------+-----------------------------------------------+
 | Step |      IDE                                |         Web console                           |
 +------+-----------------------------------------+-----------------------------------------------+
 |  1.  |                                         | Start a session: http://run.befaster.io:8111  |
 |  2.  |                                         | Configure your email                          |
 |  3.  | Set the email variable                  |                                               |
 |  4.  |                                         | Start a challenge, should display "Started"   |
 |  5.  | Run "get_new_round_description"         |                                               |
 |  6.  | Read description from ./challenges      |                                               |
 |  7.  | Implement the required method in        |                                               |
 |      |   ./lib/solutions                       |                                               |
 |  8.  | Run "test_connectivity", observe output |                                               |
 |  9.  | If ready, run "deploy_to_production"    |                                               |
 | 10.  |                                         | Type "done"                                   |
 | 11.  |                                         | Check failed requests                         |
 | 12.  |                                         | Go to step 5.                                 |
 +------+-----------------------------------------+-----------------------------------------------+
"""
start_client(sys.argv[1:],
             email="your_email_here",
             hostname="hostname_goes_here",
             action_if_no_args=RunnerActions.test_connectivity)
