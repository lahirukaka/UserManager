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

package me.colhh.usermanager.rmi.first.Holder;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class UserException extends Exception{
      
    private final UserExceptionCodes ue;
    
    public UserException(UserExceptionCodes code)
    {
        this.ue = code;
    }

    public int getErrorCode()
    {
        return ue.getCode();
    }
    
    @Override
    public String getMessage()
    {
        return ue.getMessage();
    }
    
    public enum UserExceptionCodes
    {
        USER_EXISTS(11,"User already exists"),
        USER_NOT_EXISTING(12,"User not existing");
        
        private final int code;
        private final String msg;
        
        UserExceptionCodes(int code, String message)
        {
            this.code = code;
            this.msg = message;
        }
        
        public int getCode()
        {
            return this.code;
        }
        
        public String getMessage()
        {
            return this.msg;
        }
    }
}
