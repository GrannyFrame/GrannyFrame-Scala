#!/bin/bash

if ! command -v rbenv &> /dev/null
then
    echo "rbenv could not be found!"
    echo "Please install rbenv first."
    echo "Exiting..."
    exit
fi

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

if : >/dev/tcp/8.8.8.8/53; then
  echo 'Internet available.'

  if ! gem query -i -n bundler > /dev/null 2>&1;
  then
    echo "Installing bundler Gem";
    gem install bundler
  else
    echo "Bundler Gem already installed"
  fi

  echo "Downloading newest launcher..."
  wget https://raw.githubusercontent.com/GrannyFrame/GrannyFrame-Scala/master/launcher.rb -O launcher.rb

  xterm -e ruby ./launcher.rb
else
  echo 'No Internet available!'
  echo 'Exiting...'
fi

