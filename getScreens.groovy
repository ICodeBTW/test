import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def sourceField = customFieldManager.getCustomFieldObjectByName("Source Field")
def targetField = customFieldManager.getCustomFieldObjectByName("Target Field")

def issueService = ComponentAccessor.getIssueService()

def query = issueService.newQueryBuilder().where().project("PROJECT_KEY").build()
def results = issueService.query(currentUser, query, PagerFilter.getUnlimitedFilter())

results.each { issue ->
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
