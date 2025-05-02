package ru.nsu.anisimov.service

/**
 * Service for loading Groovy configuration files.
 */
class ConfigLoader {
    /**
     * Loads and evaluates a Groovy configuration file.
     *
     * @param configPath Path to the configuration file
     * @return Map
     */
    static Map loadConfig(String configPath) {
        def configFile = new File(configPath)
        if (!configFile.exists()) {
            throw new FileNotFoundException("Config file not found: ${configPath}")
        }

        def binding = new Binding()
        def shell = new GroovyShell(binding)
        return shell.evaluate(configFile) as Map
    }
}