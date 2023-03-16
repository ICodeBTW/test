import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issueManager = ComponentAccessor.getIssueManager()

def sourceField = customFieldManager.getCustomFieldObjectByName("Source Field Name")
def destinationField = customFieldManager.getCustomFieldObjectByName("Destination Field Name")

def issues = issueManager.getIssueObjects(issueManager.getIssueIdsForProject(projectKey))

issues.each {
  def sourceValue = it.getCustomFieldValue(sourceField)
  destinationField.updateValue(null, it, new ModifiedValue(it.getCustomFieldValue(destinationField), sourceValue),changeHolder,true)
}
