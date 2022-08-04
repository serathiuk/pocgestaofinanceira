package com.serathiuk.erp.core.messages.core;

import java.io.Serializable;
import java.util.UUID;

public class CoreMessage implements Serializable {
    private static final long serialVersionUID = 3572170090858789210L;
    
	protected UUID messageId = UUID.randomUUID();
    protected UUID transactionId;

    public CoreMessage(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}
