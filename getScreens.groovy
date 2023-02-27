import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def customField = customFieldManager.getCustomFieldObjectByName("Custom Field Name")

def fieldLayoutManager = ComponentAccessor.getFieldLayoutManager()
def fieldLayouts = fieldLayoutManager.getFieldLayoutsForCustomField(customField)

def screens = []
for (fieldLayout in fieldLayouts) {
    screens.addAll(fieldLayout.getAssociatedScreens())
}

screens = screens.unique()
log.debug("Screens containing {}:", customField.name)
for (screen in screens) {
    log.debug(" - {}", screen.name)
}
