import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.config.FieldConfigScheme
import com.atlassian.jira.issue.fields.config.FieldConfigScheme.Builder

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def customField = customFieldManager.getCustomFieldObjectByName("<name-of-custom-field>")
def customFieldConfigScheme = customField.getConfigurationSchemes().find { it.getName() == "<name-of-custom-field-config>" }

if (customFieldConfigScheme) {
    def newCustomFieldConfigScheme = new Builder(customFieldConfigScheme).build()
    def fieldConfigSchemeManager = ComponentAccessor.getFieldConfigSchemeManager()
    fieldConfigSchemeManager.createFieldConfigScheme(newCustomFieldConfigScheme)
    customFieldConfigScheme.getAssociatedIssueTypeObjects().each { issueTypeScheme ->
        def newIssueTypeScheme = newCustomFieldConfigScheme.getAssociatedIssueTypeObject(issueTypeScheme.getIssueTypeId())
        issueTypeScheme.getConfigs().each { fieldConfig ->
            def newFieldConfig = newCustomFieldConfigScheme.getOneAndOnlyConfig().getConfigsByConfig(fieldConfig).first()
            newIssueTypeScheme.addConfig(newFieldConfig, fieldConfig)
        }
    }
    customField.updateFieldLayoutSchemes(customFieldConfigScheme, newCustomFieldConfigScheme)
}
