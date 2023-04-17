import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.config.FieldConfigSchemeImpl

// Get the custom fields by their IDs
def customField1 = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10000")
def customField2 = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10001")

// Get the field configuration scheme for the first custom field
def fieldConfigScheme1 = customField1.getConfigurationSchemes().find {
    it.getName() == "Field Configuration Scheme Name"
}

// Create a new field configuration scheme for the second custom field
def fieldConfigScheme2 = new FieldConfigSchemeImpl(ComponentAccessor.getFieldConfigSchemeManager(), customField2)

fieldConfigScheme2.setName("New Field Configuration Scheme Name")
fieldConfigScheme2.setDescription("Description of the new field configuration scheme")
fieldConfigScheme2.store()

// Copy the field configuration from the first scheme to the second scheme
fieldConfigScheme2.copyFrom(fieldConfigScheme1)

// Associate the new field configuration scheme with the second custom field
customField2.setConfigurationScheme(fieldConfigScheme2)
ComponentAccessor.getCustomFieldManager().updateCustomField(customField2)
