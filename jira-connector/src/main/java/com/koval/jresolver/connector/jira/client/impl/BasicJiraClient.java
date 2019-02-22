package com.koval.jresolver.connector.jira.client.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;


public class BasicJiraClient implements JiraClient {

  private final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
  private final JiraRestClient restClient;

  public BasicJiraClient(String host, Credentials credentials) throws JiraConnectorException {
    restClient = factory.createWithBasicHttpAuthentication(getURI(host), credentials.getUsername(), credentials.getPassword());
  }

  public BasicJiraClient(String host) throws JiraConnectorException {
    restClient = factory.create(getURI(host), new AnonymousAuthenticationHandler());
  }

  private URI getURI(String host) throws JiraConnectorException {
    try {
      return new URI(host);
    } catch (URISyntaxException e) {
      throw new JiraConnectorException("Could not initialize URI for host: " + host, e);
    }
  }

  @Override
  public SearchResult searchByJql(String jql, int maxResults, int startAt) {
    return restClient.getSearchClient().searchJql(jql, maxResults, startAt, new HashSet<>()).claim();
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    return restClient.getIssueClient().getIssue(issueKey).claim();
  }

  @Override
  public void close() throws IOException {
    restClient.close();
  }
}