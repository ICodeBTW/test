import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.FieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.project.Project
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.issue.fields.layout.field.FieldLayout
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager

def fieldManager = ComponentAccessor.getFieldManager() as FieldManager
def customField = fieldManager.getCustomFieldObjectByName("Name of Field to Disable")
def projectKey = "PROJECT_KEY" // Replace with the key of the project where the field should be disabled
def projectManager = ComponentAccessor.getComponent(ProjectManager)
def project = projectManager.getProjectObjByKey(projectKey)
def fieldLayoutManager = ComponentAccessor.getFieldLayoutManager()

def fieldLayout = fieldLayoutManager.getFieldLayout(project)
def fieldLayoutItem = fieldLayout.getFieldLayoutItem(customField)

if (fieldLayoutItem) {
    fieldLayout.removeFieldLayoutItem(fieldLayoutItem)
    fieldLayoutManager.storeFieldLayout(fieldLayout)
}
