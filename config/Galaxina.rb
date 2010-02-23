# Name of the resulting jar/jad bundle (no special chars).
@name = "Galaxina"

# Possible targets are defined in file _targets.rb.
@targets = [ Generic_J2ME, Touch_J2ME ]

# Possible sizes correspond to the image folders in the res directory.
# You may use arbitrary folder names (no spaces or special chars) here -
# as long as they exist.
@sizes = [ "240x320" ]

# Use this to activate global - not target-specific - settings. Used
# primarly for debug output and effects.
@symbols = [ :FULL_FX ]
