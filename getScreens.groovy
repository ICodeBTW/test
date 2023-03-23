import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField

def customFieldManager = ComponentAccessor.getCustomFieldManager()
def customField = customFieldManager.getCustomFieldObjectByName("Custom Field Name")

if (customField) {
    customField.getConfigurationSchemes().each { scheme ->
        scheme.getConfigurations().each { config ->
            config.getConfigurationItem(customField.getId())?.setDisabled(true)
        }
        ComponentAccessor.getIssueTypeScreenSchemeManager().schemes.forEach { screenScheme ->
            screenScheme.associatedSchemes.each { issueTypeScreenScheme ->
                issueTypeScreenScheme.screenSchemes.each { screenSchemeItem ->
                    screenSchemeItem.getTabs().each { tab ->
                        tab.getFieldScreenRenderLayoutItems().each { layoutItem ->
                            if (layoutItem.getOrderableField().getId() == customField.getId()) {
                                layoutItem.setHidden(true)
                                layoutItem.store()
                            }
                        }
                    }
                }
            }
        }
    }
}
