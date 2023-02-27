import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.fields.layout.field.FieldLayout
import com.atlassian.jira.issue.fields.screen.FieldScreen
import com.atlassian.jira.issue.fields.screen.FieldScreenTab
import com.atlassian.jira.project.Project
import com.atlassian.jira.project.ProjectManager

// Get the custom field by ID
CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_12345")

// Initialize an empty list to hold screen IDs that include the custom field
List<Long> screensWithField = []

// Get the project manager
ProjectManager projectManager = ComponentAccessor.getProjectManager()

// Iterate through all projects
for (Project project : projectManager.getProjects()) {
    
    // Get the field layout for the custom field in the project
    FieldLayout fieldLayout = ComponentAccessor.getFieldLayoutManager().getFieldLayout(project, customField)
    
    // Check if the field layout contains the custom field
    if (fieldLayout.contains(customField)) {
        
        // Get the field screen for the field layout
        FieldScreen fieldScreen = fieldLayout.getFieldScreen()
        
        // Iterate through all tabs of the field screen
        for (FieldScreenTab tab : fieldScreen.getTabs()) {
            
            // Check if the tab contains the custom field
            if (tab.getFieldScreenLayoutItem(customField.getId()) != null) {
                
                // Add the screen ID to the list
                screensWithField.add(fieldScreen.getId())
                break
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
