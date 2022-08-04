package com.serathiuk.erp.financeiro.pagarreceber.events;

import java.util.UUID;

import com.serathiuk.erp.core.messages.core.CoreMessage;

public class RespostaCriacaoLancamentoFluxoCaixaMessage extends CoreMessage {
    private static final long serialVersionUID = 2112071483465370205L;

    private boolean sucesso;
    private String message;
    private String idOrigem;

    public RespostaCriacaoLancamentoFluxoCaixaMessage(UUID transactionId, boolean sucesso, String idOrigem, String message) {
        super(transactionId);
        this.sucesso = sucesso;
        this.message = message;
        this.idOrigem = idOrigem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMessage() {
        return message;
    }

    public String getIdOrigem() {
        return idOrigem;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"{" +
                "messageId=" + messageId +
                ", transactionId=" + transactionId +
                ", sucesso=" + sucesso +
                ", message='" + message + '\'' +
                ", idOrigem='" + idOrigem + '\'' +
                '}';
    }
}
