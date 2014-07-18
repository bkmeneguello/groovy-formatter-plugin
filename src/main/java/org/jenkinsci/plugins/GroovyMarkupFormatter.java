package org.jenkinsci.plugins;

import groovy.lang.GroovyShell;
import hudson.Extension;
import hudson.markup.MarkupFormatterDescriptor;
import hudson.markup.EscapedMarkupFormatter;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Bruno Kühnen Meneguello
 */
public class GroovyMarkupFormatter extends EscapedMarkupFormatter {

    @DataBoundConstructor
    public GroovyMarkupFormatter() {
    }

    @Override
    public void translate(String markup, Writer output) throws IOException {
    	try {
    		output.write(new GroovyShell().evaluate(markup).toString());
    	} catch (Exception e) {
    		LOGGER.log(Level.WARNING, "Script execution failure", e);
    		super.translate(markup, output);
    	}
    }

    @Extension
    public static class DescriptorImpl extends MarkupFormatterDescriptor {
        @Override
        public String getDisplayName() {
            return "Parse the markup as Groovy Script and return the result (UNSAFE)";
        }
    }

    private static final Logger LOGGER = Logger.getLogger(GroovyMarkupFormatter.class.getName());
}
