package org.jenkinsci.plugins.valgrind.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ValgrindSourceResolverTest {
	String singleSubstitutionPathList = "/home/jenkins:/home/makeuser";
	String substitutionPathList = "/home/jenkins:/home/makeuser,/home/jenkins/workspace:/home/root/workspace";
    @Test
    public void testValgrindSourceResolverSortsSubstitutionPathList(){
    	ValgrindSourceResolver sourceResolver = new ValgrindSourceResolver(substitutionPathList);
    	List<SimpleEntry<String,String>> expectedSubstitutionPathList = new ArrayList<SimpleEntry<String, String>>(2);
    	expectedSubstitutionPathList.add(new SimpleEntry<String,String>("/home/jenkins/workspace","/home/root/workspace"));
    	expectedSubstitutionPathList.add(new SimpleEntry<String,String>("/home/jenkins","/home/makeuser"));
    	Assert.assertEquals("Expected substitution path list does not match actual",expectedSubstitutionPathList, sourceResolver.substitutionPathList);
    }
    @Test
    public void testValgrindSourceResolverParsesSingleSubstitutionPath(){
    	ValgrindSourceResolver sourceResolver = new ValgrindSourceResolver(singleSubstitutionPathList);
    	List<SimpleEntry<String,String>> expectedSubstitutionPathList = new ArrayList<SimpleEntry<String, String>>();
    	expectedSubstitutionPathList.add(new SimpleEntry<String,String>("/home/jenkins","/home/makeuser"));
    	Assert.assertEquals("Expected substitution path list does not match actual",expectedSubstitutionPathList, sourceResolver.substitutionPathList);
    }
    @Test
    public void testValgrindSourceResolverResolvesFileFromLongerSubstitutionPath(){
    	ValgrindSourceResolver sourceResolver = new ValgrindSourceResolver(substitutionPathList);
    	String expectedResolvedFilePath = "/home/root/workspace/file.cpp";
    	String resolvedSourceFilePath = sourceResolver.resolveFilePath("/home/jenkins/workspace/file.cpp");
    	Assert.assertEquals("Resolved file does not match expected",expectedResolvedFilePath, resolvedSourceFilePath);
    }
    @Test
    public void testValgrindSourceResolverResolvesFileFromSubstitutionPath(){
    	ValgrindSourceResolver sourceResolver = new ValgrindSourceResolver(substitutionPathList);
    	String expectedResolvedFilePath = "/home/makeuser/file.cpp";
    	String resolvedSourceFilePath = sourceResolver.resolveFilePath("/home/jenkins/file.cpp");
    	Assert.assertEquals("Resolved file does not match expected",expectedResolvedFilePath, resolvedSourceFilePath);
    }
    @Test
    public void testValgrindSourceResolverResolvesFileReturningOriginalFileWhenNoSubsitionPathMatches(){
    	ValgrindSourceResolver sourceResolver = new ValgrindSourceResolver(substitutionPathList);
    	String expectedResolvedFilePath = "/home/root/file.cpp";
    	String resolvedSourceFilePath = sourceResolver.resolveFilePath(expectedResolvedFilePath);
    	Assert.assertEquals("Resolved file does not match expected",expectedResolvedFilePath, resolvedSourceFilePath);
    }
}
