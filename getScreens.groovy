import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomFieldManager
import com.atlassian.jira.issue.fields.config.FieldConfigScheme
import com.atlassian.jira.issue.fields.config.FieldConfigScheme.Builder

def sourceCustomFieldName = "<name-of-source-custom-field>"
def targetCustomFieldName = "<name-of-target-custom-field>"
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def sourceCustomField = customFieldManager.getCustomFieldObjectByName(sourceCustomFieldName)
def targetCustomField = customFieldManager.getCustomFieldObjectByName(targetCustomFieldName)

// Loop through each existing configuration scheme for the source custom field
sourceCustomField.getConfigurationSchemes().each { oldFieldConfigScheme ->
    def newFieldConfigScheme = new Builder(oldFieldConfigScheme).build()
    def fieldConfigSchemeManager = ComponentAccessor.getFieldConfigSchemeManager()
    fieldConfigSchemeManager.createFieldConfigScheme(newFieldConfigScheme)
    
    targetCustomField.updateConfigurations()
    def newFieldConfigSchemeId = targetCustomField.getConfigurationSchemes().find { it.getName() == newFieldConfigScheme.getName() }.getId()
    targetCustomField.getRelevantConfig(getFieldConfigSchemeId: { newFieldConfigSchemeId })
}
