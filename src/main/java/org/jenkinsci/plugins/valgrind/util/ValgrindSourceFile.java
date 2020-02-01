package org.jenkinsci.plugins.valgrind.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * @author Johannes Ohlemacher
 */
public class ValgrindSourceFile {
    public static final String SOURCE_DIRECTORY = "valgrind-plugin/source-files";

    private static final int GENERATED_HTML_SOURCE_HEADER_SIZE = 12;
    private static final int GENERATED_HTML_SOURCE_FOOTER_SIZE = 9;
    private static final String ERROR_LINE_COLOR = "#FCAF3E";
    private static final String SOURCE_NOT_AVAIABLE_MESSAGE = "<b>Source code not available</b>";

    private Map<String, List<String>> sourceCodeBuffer = new HashMap<>();
    private Map<String, String> sourceFileLookup;
    private int linesBefore;
    private int linesAfter;

    public ValgrindSourceFile(int linesBefore, int linesAfter, Map<String, String> sourceFileLookup) {
        this.sourceFileLookup = sourceFileLookup;
        this.linesAfter = linesAfter;
        this.linesBefore = linesBefore;
    }

    public String getSnippet(String fileName, Integer lineNumber) {
        if (fileName == null || lineNumber == null)
            return SOURCE_NOT_AVAIABLE_MESSAGE;

        if (!sourceFileLookup.containsKey(fileName) || sourceFileLookup.get(fileName) == null)
            return SOURCE_NOT_AVAIABLE_MESSAGE;

        String localFileName = sourceFileLookup.get(fileName);

        if (!sourceCodeBuffer.containsKey(localFileName))
            load(localFileName);

        List<String> lines = sourceCodeBuffer.get(localFileName);
        if (lines == null || lines.isEmpty())
            return SOURCE_NOT_AVAIABLE_MESSAGE;

        StringBuilder output = new StringBuilder();
        ListIterator<String> it = lines.listIterator();
        int currentLine = 0;
        int errorLine = lineNumber + GENERATED_HTML_SOURCE_HEADER_SIZE;

        while (it.hasNext()) {
            currentLine++;
            String line = it.next();

            boolean append = false;

            //html header
            if (currentLine <= GENERATED_HTML_SOURCE_HEADER_SIZE)
                append = true;

            //lines of interest
            if (currentLine >= errorLine - linesBefore &&
                    currentLine <= errorLine + linesAfter) {
                append = true;
            }

            //html footer
            if (currentLine > lines.size() - GENERATED_HTML_SOURCE_FOOTER_SIZE)
                append = true;


            if (currentLine == errorLine) {
                output.append("</code></td></tr>\n");
                output.append("<tr><td bgcolor=\"" + ERROR_LINE_COLOR + "\">\n");
                output.append("<code>\n");
                output.append(line + "\n");
                output.append("</code></td></tr>\n");
                output.append("<tr><td>\n");
                output.append("<code>\n");
            } else if (append)
                output.append(line + "\n");
        }

        return output.toString();

    }

    private void load(String filePath) {
        sourceCodeBuffer.put(filePath, null);

        /*try {
            File dir = new File("DIR", SOURCE_DIRECTORY);

            File file = new File(dir, filePath);

            if (file.exists() && file.isFile()) {
                String sourceCode = highlightSource(IOUtils.toString(new FileInputStream(file)));

                List<String> lines = IOUtils.readLines(new StringInputStream(sourceCode));

                sourceCodeBuffer.put(filePath, lines);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }*/
    }
}
