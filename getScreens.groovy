import com.onresolve.scriptrunner.runner.ScriptRunnerImpl

def scriptRunner = ScriptRunnerImpl.getPlugin()
def consoleAppender = scriptRunner.console.getAppender("Console")

// store the original value of MaxFileSize
def originalMaxFileSize = consoleAppender.getMaxFileSize()

// temporarily set MaxFileSize to a larger value
consoleAppender.setMaxFileSize("10MB")

// your script code here

// reset MaxFileSize to its original value
consoleAppender.setMaxFileSize(originalMaxFileSize)
