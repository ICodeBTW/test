import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.config.FieldConfig
import com.atlassian.jira.issue.fields.config.FieldConfigScheme

// set the name of the custom fields you want to copy the configuration from and to
def sourceCustomFieldName = "Source Custom Field Name"
def targetCustomFieldName = "Target Custom Field Name"

// get the custom field manager
CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager()

// get the source custom field
def sourceCustomField = customFieldManager.getCustomFieldObjectByName(sourceCustomFieldName)

// get the target custom field
def targetCustomField = customFieldManager.getCustomFieldObjectByName(targetCustomFieldName)

// get the field configuration of the source custom field
FieldConfig sourceFieldConfig = sourceCustomField.getRelevantConfig()

// get the field configuration of the target custom field
FieldConfig targetFieldConfig = targetCustomField.getRelevantConfig()

// get the field configuration scheme of the target custom field
FieldConfigScheme targetFieldConfigScheme = customFieldManager.getFieldConfigScheme(targetCustomField)

// copy the configuration from the source custom field to the target custom field
targetFieldConfigScheme.getIssueTypeIds().each { issueTypeId ->
    targetFieldConfig.setOptions(sourceFieldConfig.getOptions(issueTypeId))
}

// update the target custom field's configuration
customFieldManager.updateCustomField(targetCustomField)

println "Configuration copied from $sourceCustomFieldName to $targetCustomFieldName."
