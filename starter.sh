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

if : >/dev/tcp/8.8.8.8/53; then
  echo 'Internet available.'
  echo "Downloading newest launcher..."
  wget https://raw.githubusercontent.com/GrannyFrame/GrannyFrame-Scala/master/launcher.rb -O launcher.rb

  xterm -e ruby ./launcher.rb
else
  echo 'No Internet available!'
  echo 'Exiting...'
fi

