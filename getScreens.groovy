import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.layout.field.FieldLayout
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager
import com.atlassian.jira.issue.fields.screen.FieldScreen
import com.atlassian.jira.issue.fields.screen.FieldScreenManager
import com.atlassian.jira.issue.fields.screen.FieldScreenTab
import com.atlassian.jira.project.Project
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.scheme.SchemeManagerFactory
import com.atlassian.jira.security.JiraAuthenticationContext

// Get the custom field by ID
CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager()
CustomField customField = customFieldManager.getCustomFieldObject("customfield_12345")

// Get the field layout manager and all schemes
FieldLayoutManager fieldLayoutManager = ComponentAccessor.getFieldLayoutManager()
List<FieldLayout> allFieldLayouts = []

// Iterate through all schemes
SchemeManagerFactory schemeManagerFactory = ComponentAccessor.getOSGiComponentInstanceOfType(SchemeManagerFactory.class)
for (String schemeType : schemeManagerFactory.getSchemeTypes()) {
    def schemeManager = schemeManagerFactory.getSchemeManager(schemeType)
    for (Object scheme : schemeManager.getSchemes()) {
        
        // Get the field layouts for the scheme
        def fieldLayouts = fieldLayoutManager.getFieldLayouts((scheme))
        allFieldLayouts.addAll(fieldLayouts)
    }
}

// Initialize an empty list to hold screen IDs that include the custom field
List<Long> screensWithField = []

// Iterate through all field layouts
for (FieldLayout fieldLayout : allFieldLayouts) {
    
    // Check if the field layout contains the custom field
    if (fieldLayout.contains(customField)) {
        
        // Get the scheme ID for the field layout
        Long schemeId = fieldLayout.getSchemeId()
        
        // Get the scheme manager factory and the project manager
        ProjectManager projectManager = ComponentAccessor.getProjectManager()
        
        // Get the scheme manager for the field layout's scheme type
        def schemeManager = schemeManagerFactory.getSchemeManager(fieldLayout.getSchemeType())
        
        // Iterate through all projects to check if they are associated with the scheme
        for (Project project : projectManager.getProjects()) {
            if (schemeManager.getProjects(schemeId).contains(project.getId())) {
                
                // Get the field screen manager and the field screens for the project
                FieldScreenManager fieldScreenManager = ComponentAccessor.getFieldScreenManager()
                List<FieldScreen> projectScreens = fieldScreenManager.getScreensForProject(project.getId())
                
                // Iterate through all field screens for the project
                for (FieldScreen screen : projectScreens) {
                    
                    // Iterate through all tabs of the field screen
                    for (FieldScreenTab tab : screen.getTabs()) {
                        
                        // Check if the tab contains the custom field
                        if (tab.getFieldScreenLayoutItem(customField.getId()) != null) {
                            
                            // Add the screen ID to the list
                            screensWithField.add(screen.getId())
                            break
                        }
                    }
                }
            }
        }
    }
}

// Remove duplicate screen IDs
screensWithField = screensWithField.unique()

// Print the screen IDs
for (Long screenId : screensWithField) {
    log.debug("Screen ID: {}", screenId)
}
