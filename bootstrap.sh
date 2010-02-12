#!/bin/bash

# We need the rake tasks and git library from IntensiBuild:
git clone git://github.com/DanielLukic/IntensiBuild.git modules/IntensiBuild

# Now pull in the rest with the rake tasks:
rake modules:init
