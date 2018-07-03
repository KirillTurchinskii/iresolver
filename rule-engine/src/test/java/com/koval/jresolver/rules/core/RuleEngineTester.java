package com.koval.jresolver.rules.core;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;

public class RuleEngineTester {

    @Test
    public void testCreating() throws Exception {
        DroolsRuleEngine test = new DroolsRuleEngine();
        test.close();
    }

    @Test
    public void testExecuting() throws Exception {
        JiraProperties jiraProperties = new JiraProperties("connector.properties");
        JiraConnector jiraConnector = new JiraConnector(jiraProperties);
        List<JiraIssue> issues = jiraConnector.getActualIssues();

        DroolsRuleEngine test = new DroolsRuleEngine();

        test.execute(issues.get(0));

        test.close();
    }

    @Test
    public void testSettingDebugMode() throws Exception {
        DroolsRuleEngine test = new DroolsRuleEngine();
        test.setDebugMode();
        test.close();
    }
}