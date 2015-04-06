# Introduction #

Currently, CIM has been tested to run only on Unix (Linux and Mac OS X).

Windows coming soon, but you can easily port the run.sh to a Windows bat file.

# Details #

  1. Download and extract CIM.
  1. Edit the config.properties file to your liking (see note below)
  1. Run run.sh

Enjoy your Chatter images!

config.properties fields:
  * username is required
  * If password is blank, you will be prompted to enter it
  * image\_directory is the directory where to download your images
  * endpoint is used to set an alternate server such as https://test.salesforce.com for Sandbox instances