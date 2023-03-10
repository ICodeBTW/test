screenIds.each { screenId ->
    def fieldScreen = fieldScreenManager.getFieldScreen(screenId)
    if (fieldScreen) {
        fieldScreen.getTabs().each { tab ->
            def fieldScreenLayoutItem = tab.getFieldScreenLayoutItem(fieldId)
            if (fieldScreenLayoutItem == null) {
                def fieldLayoutItem = fieldManager.getFieldLayout(screenId).getFieldLayoutItem(fieldId)
                if (fieldLayoutItem != null) {
                    tab.addLayoutItem(fieldLayoutItem)
                }
            }
        }
    }
}
