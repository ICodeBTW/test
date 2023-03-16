import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.search.SearchProviderFactory

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def sourceField = customFieldManager.getCustomFieldObjectByName("Source Field")
def targetField = customFieldManager.getCustomFieldObjectByName("Target Field")

def issueService = ComponentAccessor.getIssueService()
def jqlSearchProvider = ComponentAccessor.getComponent(SearchProviderFactory).createSearchProvider(null)

def query = jqlSearchProvider.getSearchQueryBuilder().project("PROJECT_KEY").buildQuery()
def results = jqlSearchProvider.search(query, currentUser, new PagerFilter())

results.issues.each { issue ->
    def sourceFieldValue = issue.getCustomFieldValue(sourceField)
    if (sourceFieldValue) {
        issue.setCustomFieldValue(targetField, sourceFieldValue)
        def updateValidationResult = issueService.validateUpdate(currentUser, issue.id, issue)
        if (updateValidationResult.isValid()) {
            issueService.update(currentUser, updateValidationResult)
        } else {
            log.warn("Could not update issue ${issue.key}: ${updateValidationResult.errorCollection}")
        }
    }
}
