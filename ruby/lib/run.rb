require 'tdl'
require 'logging'
Logging.logger.root.appenders = Logging.appenders.stdout
Logging.logger.root.level = :info

require_relative './app'

include TDL::ClientActions


# STEP 1. Start the "sum_numbers" challenge from the remote web interface

# ~~~~~~~~~ Setup ~~~~~~~~~

# STEP 2. Set the hostname and username
HOSTNAME = 'server_hostname'
USERNAME = 'your_username'

# STEP 3. Run the client in trial mode and read instructions
# ~~~  How to run ~~~
#
# From command line:
#     If you want to trial run:     rake run
#
# From IDE:
#     Run this file from your IDE
#


I_AM_READY = false
# STEP 7. If your are satisfied with the implementation, run the client in live mode !
# ~~~  How to run ~~~
#
# From command line:
#     If you are ready to go live:  rake run ready=true
#
# From IDE:
#     Set the I_AM_READY variable to "true" and run this file
#
# STEP 8. Go to the web interface and mark the challenge as done
def main(argv)
  value_from_commandline = (argv.length > 0 and argv[0] == 'true')
  ready = (I_AM_READY or value_from_commandline)
  puts("Ready ? = #{ready}")

  start_client(ready)
end

def start_client(ready)
  client = TDL::Client.new(hostname: HOSTNAME, username: USERNAME)

  rules = TDL::ProcessingRules.new
  rules.on('display_description').call(method(:display_and_save_description)).then(publish)
  # STEP 4. Uncomment the following line to register the sum method and run again
  # rules.on('sum').call(App.new.method(:sum)).then(publish_if(ready))

  # STEP 5. Fix the sum method implementation in lib/app.rb

  client.go_live_with(rules)
end

def publish_if(ready)
  ready ? publish : stop
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


# ~~~~~~~~~ Run ~~~~~~~~~

main(ARGV)
