module Git

  class Module

    attr_reader :repository
    attr_reader :path

    def initialize(repository, path)
      @repository = repository
      @path = path
    end

    def folder_exists?
      File.exist?(path)
    end

  end

end
