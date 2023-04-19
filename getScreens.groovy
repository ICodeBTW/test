import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.config.FieldConfig
import com.atlassian.jira.issue.fields.config.manager.FieldConfigSchemeManager
import com.atlassian.jira.issue.context.JiraContextNode

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def fieldConfigSchemeManager = ComponentAccessor.getFieldConfigSchemeManager()

// Specify the ID of the source custom field and the ID of the destination custom field
def sourceCustomFieldId = 12345
def destinationCustomFieldId = 67890

// Get the FieldConfig object for the source custom field
def sourceCustomField = customFieldManager.getCustomFieldObject(sourceCustomFieldId)
def sourceFieldConfig = sourceCustomField.getRelevantConfig()

// Get the FieldConfigScheme for the destination custom field
def destinationCustomField = customFieldManager.getCustomFieldObject(destinationCustomFieldId)
def destinationFieldConfigScheme = fieldConfigSchemeManager.getFieldConfigScheme(destinationCustomField)

// Create a new FieldConfig based on the source custom field's configuration
def destinationFieldConfig = fieldConfigSchemeManager.createWithDefaultValues(sourceFieldConfig, destinationCustomField.name)

// Associate the new FieldConfig with the destination custom field
def contexts = destinationFieldConfigScheme.getContexts()
fieldConfigSchemeManager.updateFieldConfigScheme(destinationFieldConfigScheme, contexts, destinationCustomField)

// Update the Field Configuration Scheme
fieldConfigSchemeManager.updateFieldConfigScheme(destinationFieldConfigScheme, contexts, destinationCustomField)
