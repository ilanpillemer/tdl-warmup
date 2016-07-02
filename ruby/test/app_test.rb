# noinspection RubyResolve,RubyResolve
require_relative 'test_helper'
require 'logging'

Logging.logger.root.appenders = Logging.appenders.stdout

class ClientTest < Minitest::Test

  def setup
    @app = App.new
  end

  attr_reader :app

  def test_sum
    assert_equal 3, app.sum(1, 2), 'App should add two numbers'
  end

end
