import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.FieldManager
import com.atlassian.jira.issue.fields.CustomField

def fieldManager = ComponentAccessor.getFieldManager() as FieldManager
def customField = fieldManager.getCustomFieldObjectByName("Name of Field to Disable")
def configurationSchemeManager = ComponentAccessor.getConfigurationSchemeManager()

configurationSchemeManager.getConfigurationSchemes().each { scheme ->
    def config = configurationSchemeManager.getConfiguration(scheme, customField)
    if (config) {
        def fieldConfigScheme = configurationSchemeManager.getFieldConfigScheme(config)
        def fieldConfig = fieldConfigScheme.getOneAndOnlyConfig()
        fieldConfigScheme.removeConfiguration(fieldConfig)
    }
}

customField.getConfigurationSchemes().each { scheme ->
    def config = customField.getRelevantConfig(scheme)
    if (config) {
        customField.getConfigurationSchemes().removeSchemeAssociation(config.getSchemeId())
    }
}
