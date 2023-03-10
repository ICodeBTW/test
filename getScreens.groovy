 if (configs.find { it.getProjectObject()?.id == project.id }) {
        return
    }
    def fieldConfig = configScheme.getOneAndOnlyConfig()
    if (fieldConfig && !fieldManager.isFieldHidden(customField, project, fieldConfig)) {
        return
    }
    configScheme.addConfiguration(project, fieldConfig)
