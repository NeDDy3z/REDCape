package org.redcape.exceptions;


/**
 * Exception thrown when an object is not initialized.
 * <p>
 * This exception is used to indicate that an object has not been properly initialized before use.
 * </p>
 */
public final class ObjectNotInitialized extends Exception {

    /**
     * Default constructor for ObjectNotInitialized exception
     *
     * @param message for the exception
     */
    public ObjectNotInitialized(String message) {
        super(message);
    }

    /**
     * Constructor for ObjectNotInitialized exception
     *
     * @param message for the exception
     * @param cause   for the exception
     */
    public ObjectNotInitialized(String message, Throwable cause) {
        super(message, cause);
    }
}