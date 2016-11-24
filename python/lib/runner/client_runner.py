import logging
import sys

from tdl.client import Client
from tdl.processing_rules import ProcessingRules

from solutions.sum import sum
from runner.runner_action import RunnerActions


def configure_logging():
    ch = logging.StreamHandler(sys.stdout)
    ch.setLevel(logging.INFO)
    formatter = logging.Formatter(
        '%(asctime)s - %(name)s - %(levelname)s - %(message)s'
    )
    ch.setFormatter(formatter)
    client_logger = logging.getLogger('tdl.client')
    client_logger.setLevel(logging.INFO)
    client_logger.addHandler(ch)
    stomp_logger = logging.getLogger('stomp')
    stomp_logger.setLevel(logging.WARN)
    stomp_logger.addHandler(ch)


# ~~~~~~~~ Runner ~~~~~~~~~~

def start_client(args, email, hostname='run.befaster.io', action_if_no_args="connectivity_test"):
    configure_logging()

    value_from_args = extract_action_from(args)
    runner_action = value_from_args if value_from_args is not None else action_if_no_args
    print("Chosen action is: {}".format(runner_action.name))

    client = Client(hostname, unique_id=email)

    rules = ProcessingRules()
    rules.on("display_description").call(display_and_save_description).then("publish")
    rules.on("sum").call(sum).then(runner_action.client_action)

    client.go_live_with(rules)


def extract_action_from(args):
    if len(args) > 0:
        firstArg = args[0]
        return get_first([action for action in RunnerActions.all if action.name.lower() == firstArg.lower()])
    else:
        return None


def get_first(iterable, default=None):
    if iterable:
        for item in iterable:
            return item
    return default


# ~~~~~~~~~ Provided implementations ~~~~~~~~~

def display_and_save_description(label, description):
    print('Starting round: '.format(label))
    print(description)

    output = open("challenges/{}.txt".format(label), "w")
    output.write(description)
    output.close()
    print "Challenge description saved to file: {}.".format(output.name)

    return 'OK'
