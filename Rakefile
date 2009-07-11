#!/usr/bin/rake

$LOAD_PATH << 'lib'

Dir.glob('lib/tasks/**/*.rb').each do |tasks|
  require tasks.sub('lib/', '')
end

task :default => 'release:do'
