#!/usr/bin/env bash

sudo apt-get update

#Install  python
sudo apt-get install python
#Install  ruby
sudo apt-get install ruby
#Install php
sudo apt-get install php5-common php5-cli
#Install java
sudo apt-get install default-jdk
#Install C#
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF
echo "deb http://download.mono-project.com/repo/debian wheezy main" | sudo tee /etc/apt/sources.list.d/mono-xamarin.list
sudo apt-get update
sudo apt-get install mono-complete mono-devel