require "minitest/autorun"
require "./autoindent.rb"

class TestAutoindent < Minitest::Test
  def test_basic_input
    expected = File.read('output')
    assert_equal expected, DinoSetoSpace::autoindent(File.read('input'))
  end
end
