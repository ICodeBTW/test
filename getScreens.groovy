import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomFieldManager
import com.atlassian.jira.issue.fields.config.FieldConfigScheme
import com.atlassian.jira.issue.fields.config.manager.FieldConfigSchemeManager
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager
import com.atlassian.jira.project.Project

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def projectManager = ComponentAccessor.getProjectManager()
def fieldConfigSchemeManager = ComponentAccessor.getComponent(FieldConfigSchemeManager)
def fieldLayoutManager = ComponentAccessor.getComponent(FieldLayoutManager)

//Replace the custom field ID with your custom field's ID
def customField = customFieldManager.getCustomFieldObject("customfield_10000")

//Replace the project key with the key of the project to which you want to add the custom field
def project = projectManager.getProjectByCurrentKey("PROJECT_KEY")

//Get the project's field configuration scheme
def fieldConfigScheme = fieldConfigSchemeManager.getConfigScheme(project)

//Create a new field configuration for the custom field
def fieldConfig = customFieldManager.createFieldConfig(customField)

//Add the custom field to the project's field configuration scheme
fieldConfigScheme.getOneAndOnlyConfig().get().addFieldConfig(customField)

//Update the project's field layout to include the custom field
def fieldLayout = fieldLayoutManager.getFieldLayout(project)
fieldLayoutManager.addCustomFieldToFieldLayout(fieldConfig, fieldLayout)

//Associate the updated field configuration scheme with the project
fieldConfigSchemeManager.updateFieldConfigScheme(fieldConfigScheme)
