# Name of the resulting jar/jad bundle (no special chars).
@name = "Galaxina"

# Possible targets are defined in file _targets.rb.
@targets = [ Opengl_ANDROID ]

# Possible sizes correspond to the image folders in the res directory.
# You may use arbitrary folder names (no spaces or special chars) here -
# as long as they exist.
load_config '_320x480.rb'

# Use this to activate global - not target-specific - settings. Used
# primarly for debug output and effects.
@symbols = [ :FULL_FX, :SENSORS ]

# IntensiGame symbols follow.
@symbols << :INFO      # activate INFO level logging
@symbols << :STATS     # show IntensiGame EngineStats
@symbols << :TRACKBALL # support for trackball control

@symbols << :PROFILING
@symbols << :CONSOLE
@symbols << :TIMING

# Force mod music format:
@properties['target.music_suffix'] = '.mod'
@properties['target.music_type'] = 'audio/mod'
