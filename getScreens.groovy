import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.project.Project

// Replace "PROJECT_KEY" with the key of the project you want to get custom fields for
def projectKey = "PROJECT_KEY"

// Get the project object
Project project = ComponentAccessor.getProjectManager().getProjectObjByKey(projectKey)

// Get the custom fields associated with the project
def customFieldManager = ComponentAccessor.getCustomFieldManager()
List<CustomField> allCustomFields = customFieldManager.getCustomFieldObjects()

List<CustomField> projectCustomFields = allCustomFields.findAll { customField ->
    customField.getConfigurationSchemes().any { scheme ->
        scheme.getContexts().any { context ->
            context.getProjectId() == project.id
        }
    }
}

// Print out the custom fields associated with the project
log.warn("Custom fields associated with project ${projectKey}: ${projectCustomFields*.name.join(', ')}")
