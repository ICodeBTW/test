import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomFieldManager
import com.atlassian.jira.issue.fields.FieldManager
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.IssueFieldConstants
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.context.ProjectContext
import com.atlassian.jira.issue.context.GlobalIssueContext

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issueTypeSchemeManager = ComponentAccessor.getIssueTypeSchemeManager()
def projectManager = ComponentAccessor.getProjectManager()

// Change the following values to match your custom field and project
def customFieldName = "My Custom Field"
def projectKey = "PROJECTKEY"

def customField = customFieldManager.createCustomField(
    customFieldName,
    "My Custom Field Description",
    customFieldManager.getCustomFieldType(IssueFieldConstants.CUSTOM_FIELD_TYPE_TEXT),
    customFieldManager.getDefaultSearcher(customFieldManager.getCustomFieldType(IssueFieldConstants.CUSTOM_FIELD_TYPE_TEXT)),
    new ArrayList<>()
)

def project = projectManager.getProjectByCurrentKey(projectKey)
def contexts = customFieldManager.getConfigurationSchemes().findAll { scheme ->
    scheme.getAssociatedProjectIds().contains(project.getId())
}
contexts.each { contextScheme ->
    customFieldManager.createFieldConfig(customField, contextScheme)
}
