/*
Copyright Luxfacta Solucoes de TI Ltda
 */
package com.luxfacta.vernyy.api.message;

/**
 *
 * @author rcerqueira
 */
public interface IBaseMessage {
    
    public String getMessage();

    public String getId();

    public String getStatus();

    public boolean getError();

}
