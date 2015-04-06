f#summary How to Build Your Own Version of CIM

# Introduction #

Chatter Image Magic (CIM) is an open source tool and as such, you can modify the code and compile your own version.  I would love to have your contributions!

# Details #

  1. Checkout the code:
```
svn checkout http://chatter-image-magic.googlecode.com/svn/trunk/ chatter-image-magic-read-only
```
  1. Modify the code in the src directory as you desire
  1. Run the build task
```
$ ant build
```
  1. Change to the cim directory
```
$ cd cim
```
  1. Edit config.properties and execute run.sh