import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.screen.FieldScreen

def projectKey = "TEST" // Change this to your project key

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def fieldScreenManager = ComponentAccessor.getFieldScreenManager()

// Get all custom fields for all issue types of the project
def customFieldsForProject = customFieldManager.getCustomFieldObjects().findAll { CustomField cf ->
    cf.getAssociatedProjectIds().contains(projectKey as Long)
}

// Get all fields on every screen (whether the screens are applied to the workflow transition or not)
def fieldsOnScreens = fieldScreenManager.getFieldScreens().collectEntries { FieldScreen fs ->
    [fs.name, fs.tabs.collectMany { tab -> tab.fieldLayoutItems.collect { it.orderableField.id } }]
}

// Compare custom fields with fields on screens and return only custom fields
def result = [:]
fieldsOnScreens.each { screenName, fieldIds ->
    def commonFields = fieldIds.intersect(customFieldsForProject*.id)
    if (commonFields) {
        result[screenName] = commonFields.collect { customFieldManager.getCustomFieldObject(it).name }
    }
}

return result
