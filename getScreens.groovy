import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.FieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.config.IssueTypeScreenSchemeManager

def fieldManager = ComponentAccessor.getFieldManager() as FieldManager
def customField = fieldManager.getCustomFieldObjectByName("Name of Field to Disable")
def issueTypeScreenSchemeManager = ComponentAccessor.getComponent(IssueTypeScreenSchemeManager)

issueTypeScreenSchemeManager.getIssueTypeScreenSchemes().each { issueTypeScreenScheme ->
    issueTypeScreenScheme.getIssueTypeMappings().each { issueType, screenScheme ->
        screenScheme.removeFieldScreenLayoutItem(customField.id)
        issueTypeScreenSchemeManager.updateIssueTypeScreenSchemeMappings(issueTypeScreenScheme, issueType, screenScheme)
    }
}
