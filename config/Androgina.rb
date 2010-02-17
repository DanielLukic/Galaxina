# Name of the resulting jar/jad bundle (no special chars).
@name = "Galaxina"

# Possible targets are defined in file _targets.rb.
@targets = [ Generic_ANDROID ]
Generic_ANDROID.music_suffix            = '.mod'
Generic_ANDROID.music_type              = 'audio/mod'

# Possible sizes correspond to the image folders in the res directory.
# You may use arbitrary folder names (no spaces or special chars) here -
# as long as they exist.
@sizes = [ "320x480" ]

# Use this to activate global - not target-specific - settings. Used
# primarly for debug output and effects.
@symbols = [ :NO_KEY_REPEAT, :MORE_FX, :FULL_FX, :TOUCH_SUPPORTED, :HANDLE_SLOW_DOWN, :CHEAT, :MULTI_BG ]
