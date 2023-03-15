import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomFieldManager
import com.atlassian.jira.project.Project
import com.atlassian.jira.project.ProjectFieldLayoutSchemeHelper

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def projectManager = ComponentAccessor.getProjectManager()

//Replace the custom field ID with your custom field's ID
def customField = customFieldManager.getCustomFieldObject("customfield_10000")

//Replace the project key with the key of the project to which you want to add the custom field
def project = projectManager.getProjectByCurrentKey("PROJECT_KEY")

//Add the custom field to the project's field configuration
def fieldConfig = customField.getRelevantConfig(project)
customFieldManager.createCustomFieldConfig(customField, [fieldConfig])

def projectFieldLayoutSchemeHelper = ComponentAccessor.getComponent(ProjectFieldLayoutSchemeHelper)
def fieldLayoutScheme = projectFieldLayoutSchemeHelper.getEffectiveFieldLayoutScheme(project)
def fieldLayout = fieldLayoutScheme.getFieldLayout(project.id)
fieldLayout.addFieldLayoutItem(customField.id, 0, 0)
projectFieldLayoutSchemeHelper.updateFieldLayoutScheme(fieldLayoutScheme, fieldLayout)
