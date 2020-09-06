#!/bin/bash

if ! command -v ruby &> /dev/null
then
    echo "Ruby could not be found!"
    echo "Please install ruby first."
    echo "Exiting..."
    exit
fi

if ! command -v gem &> /dev/null
then
    echo "Ruby Gems could not be found!"
    echo "Please install ruby Gems first."
    echo "Exiting..."
    exit
fi

if ! command -v xterm &> /dev/null
then
    echo "xterm could not be found!"
    echo "Please install xterm first."
    echo "Exiting..."
    exit
fi

if ! gem query -i -n httparty > /dev/null 2>&1;
then
  echo "Installing HTTParty Gem";
  gem install httparty;
else
  echo "HTTParty Gem already installed"
fi

if ! gem query -i -n json > /dev/null 2>&1;
then
  echo "Installing JSON Gem";
  gem install json
else
  echo "JSON Gem already installed"
fi

if [ ! -f ./launcher.rb ]; then
    echo "Launcher not found!"
    echo "Downloading..."
    wget https://raw.githubusercontent.com/GrannyFrame/GrannyFrame-Scala/master/launcher.rb
fi

xterm -e ruby ./launcher.rb