def customFieldManager = ComponentAccessor.getCustomFieldManager()
def field = customFieldManager.getCustomFieldObjectByName("Custom Field Name")
def configuration = field.getRelevantConfigurations()

configuration.each { config ->
    def project = config.getProjectObject()
    if (project.key == "PROJECT_KEY") {
        customFieldManager.removeCustomFieldFromProject(field.getIdAsLong(), project)
    }
}
