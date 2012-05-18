
package org.example.ravelry;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.5.1
 * 2012-05-19T04:13:08.547+10:00
 * Generated source version: 2.5.1
 */

@WebFault(name = "AccessDenied", targetNamespace = "http://www.example.org/Ravelry")
public class AccessDeniedFault extends Exception {
    
    private java.lang.String accessDenied;

    public AccessDeniedFault() {
        super();
    }
    
    public AccessDeniedFault(String message) {
        super(message);
    }
    
    public AccessDeniedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedFault(String message, java.lang.String accessDenied) {
        super(message);
        this.accessDenied = accessDenied;
    }

    public AccessDeniedFault(String message, java.lang.String accessDenied, Throwable cause) {
        super(message, cause);
        this.accessDenied = accessDenied;
    }

    public java.lang.String getFaultInfo() {
        return this.accessDenied;
    }
}