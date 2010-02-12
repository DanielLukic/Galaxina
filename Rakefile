#!/usr/bin/rake

$LOAD_PATH << 'modules/IntensiBuild/lib'

Dir.glob('modules/IntensiBuild/lib/tasks/**/*.rb').each do |tasks|
  require tasks.sub('modules/IntensiBuild/lib/', '')
end

task :default => 'release:do'
