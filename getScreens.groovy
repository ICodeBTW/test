// Replace 'CUSTOM_FIELD_NAME' with the name of your custom field
CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName("CUSTOM_FIELD_NAME")

// Replace the list of project IDs with your own list
List<String> projectIDs = ["PROJECT_ID_1", "PROJECT_ID_2", "PROJECT_ID_3"]

projectIDs.each { projectID ->
    Project project = ComponentAccessor.getProjectManager().getProjectObjByKey(projectID)
    if (project) {
        if (!project.getCustomFieldObjects().contains(customField)) {
            ComponentAccessor.getProjectManager().updateProject(project, project.getIssueTypes(), project.getAssigneeType(),
                project.getProjectLead(), project.getUrl(), project.getDescription(), customField)
        } else {
            log.warn("Custom field {} already exists on project {}.", customField.name, project.key)
        }
    } else {
        log.warn("Project with ID {} not found.", projectID)
    }
}
