import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.layout.field.FieldLayout
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager

def fieldManager = ComponentAccessor.getFieldManager()
def customField = fieldManager.getCustomFieldObject("customfield_12345") // Replace with your custom field ID
def fieldLayoutManager = ComponentAccessor.getFieldLayoutManager()
def screens = fieldLayoutManager.getEditableScreens()

screens.each { screen ->
    if (screen.id == "10001") { // Replace with your screen ID
        def fieldLayout = fieldLayoutManager.getFieldLayout(screen.id, customField)
        if (!fieldLayout) {
            fieldLayout = fieldLayoutManager.addCustomFieldToScreen(customField, screen.id)
            log.debug("Enabled custom field ${customField.name} in screen ${screen.name}")
        }
    }
}
