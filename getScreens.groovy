import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.config.FieldConfigScheme
// Get the custom field object for the source field
CustomField sourceField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Source Custom Field Name")
// Get the field configuration scheme for the source field
FieldConfigScheme sourceScheme = sourceField.getConfigurationSchemes().listIterator().next()
// Get the custom field object for the destination field
CustomField destField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("Destination Custom Field Name")
// Create a new field configuration scheme for the destination field
FieldConfigScheme destScheme = ComponentAccessor.getFieldConfigSchemeManager().createFieldConfigScheme(destField)
// Copy the issue type to field configuration mappings from the source scheme to the destination scheme
for (Object obj : sourceScheme.getConfigsByIssueType().entrySet()) {
    Map.Entry entry = (Map.Entry) obj
    def issueType = entry.key
    def fieldConfig = entry.value
    destScheme.getIssueTypeMappings().add(issueType, fieldConfig)
}
// Save the new field configuration scheme for the destination field
ComponentAccessor.getFieldConfigSchemeManager().updateFieldConfigScheme(destScheme)
