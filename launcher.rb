#!/bin/ruby

require 'bundler/inline'

ENV['BUNDLE_PATH'] = "./gems"
ENV_KEY = "TELEGRAM_TOKEN"
PROG_DIR = "GrannyFrame-Scala"
TEMP_FILE = "grannyframe_latest.tgz"
VERSION_FILE = "version.txt"
curr_vers = nil
latest_vers = nil
tarball_url = nil

gemfile do
  source 'https://rubygems.org'
  gem 'json'
  gem 'httparty'
end

puts "Starting GrannyFrame Launcher..."

puts "Checking for Telegram Token..."
if ENV.has_key? ENV_KEY
  puts "Token is present: #{ENV[ENV_KEY]}"
else
  STDERR.puts "No Telegram Token has been supplied! Cannot start..."
  sleep 10
  exit
end

if File.file? VERSION_FILE
  curr_vers = File.read(VERSION_FILE)
end

puts "Currently installed version: #{curr_vers ? curr_vers : 'NONE!'}"

response = HTTParty.get("https://api.github.com/repos/GrannyFrame/GrannyFrame-Scala/releases/latest")

if response.code == 200
  json = JSON.parse response.body
  latest_vers = json["tag_name"]
  tarball_url = json["assets"][0]["browser_download_url"]
  puts "Latest Release:"
  puts "Tag: #{latest_vers}"
else
  abort "Error requesting GitHub... Is there an internet Connection?"
end

if curr_vers != latest_vers
  puts "Updating to latest version #{latest_vers} ..."
  `rm -rf #{PROG_DIR}`
  `wget --max-redirect=5 #{tarball_url} -O #{TEMP_FILE}`
  `mkdir #{PROG_DIR}`
  `tar -xzvf #{TEMP_FILE} --strip 1 --directory ./#{PROG_DIR}`
  `rm #{TEMP_FILE}`
  File.write(VERSION_FILE, latest_vers)
end

puts "Launching GrannyFrame..."
pid = spawn("./GrannyFrame-Scala/bin/grannyframescala")
Process.wait pid