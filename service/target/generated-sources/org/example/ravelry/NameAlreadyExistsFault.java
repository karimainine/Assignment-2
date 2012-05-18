
package org.example.ravelry;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.5.1
 * 2012-05-19T04:13:08.708+10:00
 * Generated source version: 2.5.1
 */

@WebFault(name = "NameAlreadyExists", targetNamespace = "http://www.example.org/Ravelry")
public class NameAlreadyExistsFault extends Exception {
    
    private java.lang.String nameAlreadyExists;

    public NameAlreadyExistsFault() {
        super();
    }
    
    public NameAlreadyExistsFault(String message) {
        super(message);
    }
    
    public NameAlreadyExistsFault(String message, Throwable cause) {
        super(message, cause);
    }

    public NameAlreadyExistsFault(String message, java.lang.String nameAlreadyExists) {
        super(message);
        this.nameAlreadyExists = nameAlreadyExists;
    }

    public NameAlreadyExistsFault(String message, java.lang.String nameAlreadyExists, Throwable cause) {
        super(message, cause);
        this.nameAlreadyExists = nameAlreadyExists;
    }

    public java.lang.String getFaultInfo() {
        return this.nameAlreadyExists;
    }
}
