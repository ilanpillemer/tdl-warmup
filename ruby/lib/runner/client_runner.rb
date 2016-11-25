require 'tdl'
require 'logging'
Logging.logger.root.appenders = Logging.appenders.stdout
Logging.logger.root.level = :info

require_relative '../../lib/solutions/sum'
require_relative '../../lib/runner/runner_action'

include RunnerActions

# ~~~~~~~~~ Setup ~~~~~~~~~

def start_client(argv, email, hostname='run.befaster.io', action_if_no_args=RunnerActions.test_connectivity)
  value_from_argv = extract_action_from(argv)
  runner_action = value_from_argv !=  nil ? value_from_argv : action_if_no_args
  puts("Chosen action is: #{runner_action.name}")

  client = TDL::Client.new(hostname: hostname, unique_id: email)

  rules = TDL::ProcessingRules.new
  rules.on('display_description').call(method(:display_and_save_description)).then(publish)
  rules.on('sum').call(Sum.new.method(:sum)).then(runner_action.client_action)

  client.go_live_with(rules)
end

def extract_action_from(argv)
  first_arg = argv.length > 0 ? argv[0] : ''
  RunnerActions.all.select { |action| action.name.casecmp(first_arg) == 0 }.first
end


# ~~~~~~~~~ Provided implementations ~~~~~~~~~

def display_and_save_description(label, description)
  puts "Starting round: #{label}"
  puts description

  output = File.open("challenges/#{label}.txt", 'w')
  output << description
  output.close
  puts "Challenge description saved to file: #{output.path}."

  'OK'
end