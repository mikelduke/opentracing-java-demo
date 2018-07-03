package com.mikelduke.vhs.catalog;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 55910427545988915L;
    
    public NotFoundException() {
        super();
    }

    public NotFoundException(String reason) {
        super(reason);
    }
}
