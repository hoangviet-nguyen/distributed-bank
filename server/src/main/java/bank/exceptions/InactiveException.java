/*
 * Copyright (c) 2025 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.exceptions;

import java.io.Serial;

/**
 * The InactiveException is thrown when a bank transaction is called on an
 * closed account.
 * 
 * @see Account
 * @see LocalBank
 * @author Dominik Gruntz
 * @version 3.0
 */
public class InactiveException extends Exception {
    @Serial
    private static final long serialVersionUID = -408686052253480736L;

    public InactiveException() {
        super();
    }

    public InactiveException(String reason) {
        super(reason);
    }

    public InactiveException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public InactiveException(Throwable cause) {
        super(cause);
    }
}
