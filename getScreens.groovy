screenIds.each { screenId ->
    def fieldScreen = fieldScreenManager.getFieldScreen(screenId)
    if (fieldScreen) {
        def fieldScreenTab = fieldScreen.getTab(0)
        if (fieldScreenTab) {
            def fieldScreenLayoutItem = fieldScreenTab.getFieldScreenLayoutItem(fieldId)
            if (fieldScreenLayoutItem == null) {
                def fieldLayoutItem = fieldManager.getFieldLayout(screenId).getFieldLayoutItem(fieldId)
                if (fieldLayoutItem != null) {
                    fieldScreenTab.addLayoutItem(fieldLayoutItem)
                }
            }
        }
    }
}
