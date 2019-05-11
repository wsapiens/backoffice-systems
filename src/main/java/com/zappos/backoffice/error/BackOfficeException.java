package com.zappos.backoffice.error;

/**
 * BackOffice Exception
 * 
 * @author spark
 *
 */
public class BackOfficeException extends RuntimeException {

    private static final long serialVersionUID = -5569320662417184823L;

    public BackOfficeException() { super(); }
    public BackOfficeException(String s) { super(s); }
    public BackOfficeException(String s, Throwable throwable) { super(s, throwable); }
    public BackOfficeException(Throwable throwable) { super(throwable); }
}
