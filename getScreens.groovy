import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.search.SearchResults
import com.atlassian.jira.issue.search.SearchProviderFactory
import com.atlassian.jira.web.bean.PagerFilter

def projectKey = "PROJECT_KEY" // replace with your project key

def jqlSearchProvider = ComponentAccessor.getComponent(SearchProviderFactory).createSearchProvider(null)
def searchQuery = jqlSearchProvider.getSearchQueryBuilder().project(projectKey).buildQuery()

def searchResults = jqlSearchProvider.search(searchQuery, ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(), PagerFilter.getUnlimitedFilter()) as SearchResults

def issues = searchResults.issues.collect { Issue issue -> issue }

issues.each { Issue issue ->
    log.debug("Issue: ${issue.key}")
    // Do something with the issue
}
