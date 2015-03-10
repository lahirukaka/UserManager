/*
 * Copyright (c) 2015 Lahiru Udana <lahirukaka@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Lahiru Udana <lahirukaka@gmail.com> - initial API and implementation and/or initial documentation
 */

package me.colhh.usermanager.rmi.first.Adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class Verification {

    private static Verification instance;
    
    private Pattern ptrn;
    private Matcher mtch;
    
    private Verification(){}
    
    /**
     * Get the only instance of Verification Class
     * @return Verification
     */
    public static synchronized Verification getInstance()
    {
        if(instance == null) instance = new Verification();
        return instance;
    }
    
    /**
     * Returns if there is no special character in the text
     * @param text String to check
     * @return true only if there is no special chars
     */
    public boolean noSpecialChars(String text)
    {
        ptrn = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        mtch = ptrn.matcher(text);
        boolean nospecial = !mtch.find();
        ptrn = null;    mtch = null;
        return nospecial;
    }
    
    /**
     * Returns if the first char is an English letter
     * @param text String to check
     * @return true , if the first char is an English letter
     */
    public boolean firstIsChar(String text)
    {
        ptrn = Pattern.compile("[^a-z]",Pattern.CASE_INSENSITIVE);
        mtch = ptrn.matcher(text.substring(0, 1));
        boolean ischar = !mtch.find();
        ptrn = null;    mtch = null;
        return ischar;
    }
    
    /**
     * Returns whether the text length is conformed to the min and max lengths
     * @param min minimum length
     * @param max maximum length
     * @param text String to check
     * @return true, if the text is within the length range
     */
    public boolean stringInCorrectLength(int min, int max, String text)
    {
        int length = text.length();
        return (length >= min && length <= max);
    }
}
