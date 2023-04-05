import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.config.FieldConfig
import com.atlassian.jira.issue.fields.config.FieldConfigScheme

// set the name of the custom fields you want to copy configurations from and to
def sourceCustomFieldName = "Source Custom Field Name"
def targetCustomFieldName = "Target Custom Field Name"

// get the custom field manager
CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager()

// get the source custom field
def sourceCustomField = customFieldManager.getCustomFieldObjectByName(sourceCustomFieldName)

// get the target custom field
def targetCustomField = customFieldManager.getCustomFieldObjectByName(targetCustomFieldName)

// get the source custom field configuration schemes
List<FieldConfigScheme> sourceFieldConfigSchemes = sourceCustomField.getConfigurationSchemes()

// iterate through each source custom field configuration scheme
sourceFieldConfigSchemes.each { sourceFieldConfigScheme ->

    // get the source custom field configuration for the current scheme
    FieldConfig sourceFieldConfig = sourceCustomField.getFieldConfig(sourceFieldConfigScheme)

    // create a new configuration scheme for the target custom field, copying the name and description from the source scheme
    FieldConfigScheme targetFieldConfigScheme = new FieldConfigScheme.Builder().setName(sourceFieldConfigScheme.getName()).setDescription(sourceFieldConfigScheme.getDescription()).build()

    // create a new configuration for the target custom field, copying the values from the source configuration
    FieldConfig targetFieldConfig = customFieldManager.createFieldConfig(targetFieldConfigScheme, targetCustomField)

    targetFieldConfig.setOptions(sourceFieldConfig.getOptions())
    targetFieldConfig.setContexts(sourceFieldConfig.getContexts())

    // associate the new configuration with the target custom field configuration scheme
    targetFieldConfigScheme.addRelevantConfig(targetFieldConfig)

    // associate the target custom field configuration scheme with the target custom field
    targetCustomField.getConfigurationSchemesManager().addSchemeAssociation(targetCustomField, targetFieldConfigScheme)

    // associate the same contexts to the target custom field as for the source field
    targetCustomField.updateIssueTypesForConfigScheme(targetFieldConfigScheme.getId(), sourceCustomField.getAssociatedIssueTypes())

    // update the target custom field's configuration
    customFieldManager.updateCustomField(targetCustomField)

    println "Configuration copied for $sourceCustomFieldName scheme ${sourceFieldConfigScheme.name} to $targetCustomFieldName."
}
