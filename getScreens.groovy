import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.context.GlobalIssueContext
import com.atlassian.jira.issue.context.ProjectIssueContext
import com.atlassian.jira.issue.context.IssueContext
import com.atlassian.jira.issue.context.IssueContextImpl
import com.atlassian.jira.issue.fields.config.FieldConfig
import com.atlassian.jira.issue.fields.config.FieldConfigScheme
import com.atlassian.jira.issue.fields.config.manager.FieldConfigSchemeManager
import com.atlassian.jira.issue.fields.config.manager.FieldConfigSchemeManagerImpl

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def fieldConfigSchemeManager = ComponentAccessor.getComponent(FieldConfigSchemeManager)

// Get the source and target custom fields
def sourceField = customFieldManager.getCustomFieldObjectByName("Source Custom Field")
def targetField = customFieldManager.getCustomFieldObjectByName("Target Custom Field")

// Get the list of issue contexts for the source custom field
def sourceContexts = sourceField.getConfigurationSchemes().collect { FieldConfigScheme fieldConfigScheme ->
    fieldConfigScheme.getAllConfigs().collect { FieldConfig fieldConfig ->
        fieldConfig.getContexts().collect { IssueContext issueContext ->
            new IssueContextImpl(issueContext.getProjectObject(), issueContext.getIssueTypeObject())
        }
    }.flatten()
}.flatten().unique()

// Copy the issue contexts to the target custom field
sourceContexts.each { IssueContext issueContext ->
    def existingConfigs = fieldConfigSchemeManager.getConfigsForField(targetField, issueContext)
    def newConfig = existingConfigs.isEmpty() ? targetField.getRelevantConfig(issueContext) : existingConfigs.first()
    fieldConfigSchemeManager.removeConfigScheme(issueContext, targetField)
    fieldConfigSchemeManager.addConfigToScheme(newConfig, issueContext, targetField)
}
