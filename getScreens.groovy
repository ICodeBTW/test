import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.layout.field.FieldLayout
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem
import com.atlassian.jira.issue.fields.screen.FieldScreen
import com.atlassian.jira.issue.fields.screen.FieldScreenScheme
import com.atlassian.jira.issue.fields.screen.FieldScreenSchemeItem

// Get the FieldLayoutManager and FieldScreenManager
def fieldLayoutManager = ComponentAccessor.getFieldLayoutManager()
def fieldScreenManager = ComponentAccessor.getFieldScreenManager()

// Get the custom field by ID
def customFieldId = "CUSTOM_FIELD_ID"
def customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(customFieldId)

// Get the screens by IDs
def screenIds = [123, 456, 789] // Replace with your screen IDs
def fieldScreens = screenIds.collect { fieldScreenManager.getFieldScreen(it as Long) }

// Loop through the screens and add the custom field to them
fieldScreens.each { screen ->
    def fieldScreenScheme = fieldScreenManager.getFieldScreenScheme(screen)

    // Find the field screen scheme item that corresponds to the screen
    def schemeItem = fieldScreenScheme.getFieldScreenSchemeItems().find { it.getFieldScreen().id == screen.id }

    // Find the field layout that corresponds to the issue type and field screen scheme item
    def fieldLayout = fieldLayoutManager.getFieldLayout(schemeItem.getIssueTypeId(), schemeItem.getFieldScreenSchemeId())

    // Add the custom field to the field layout
    def fieldLayoutItem = fieldLayoutManager.getFieldLayoutItem(fieldLayout.getId(customField))
    if (!fieldLayout.getFieldLayoutItems().contains(fieldLayoutItem)) {
        fieldLayout.add(fieldLayoutItem)
        fieldLayoutManager.updateFieldLayout(fieldLayout)
    }

    // Add the custom field to the screen
    def fieldScreenLayoutItem = fieldScreenManager.createFieldScreenLayoutItem().with({
        setFieldId(customFieldId)
        setFieldScreen(screen)
    })
    screen.addFieldScreenLayoutItem(fieldScreenLayoutItem)

    // Update the field screen scheme item
    def fieldScreenSchemeItem = new FieldScreenSchemeItem(
        schemeItem.getIssueTypeId(),
        schemeItem.getFieldScreen(),
        fieldScreenLayoutItem.getId() as Long
    )
    fieldScreenScheme.updateFieldScreenSchemeItem(fieldScreenSchemeItem)
}

// Print the result
log.debug("Custom field with ID {} enabled in screens with IDs {}", customFieldId, screenIds)
