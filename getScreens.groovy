configSchemes.each { FieldConfigurationScheme configScheme ->
    def configs = configScheme.getConfigsByProject(project)
    if (configs.size() == 0) {
        def fieldConfig = configScheme.getOneAndOnlyConfig()
        if (fieldConfig && !fieldManager.isFieldHidden(customField, project, fieldConfig)) {
            return
        }
        configScheme.addConfiguration(project, fieldConfig)
    }
}
