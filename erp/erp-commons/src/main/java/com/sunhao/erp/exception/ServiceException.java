package com.sunhao.erp.exception;

/**
 * @author sunhao
 * @CreateDate: 2018/7/26 18:58
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {

    }

    public ServiceException(String message) throws ServiceException{
        super(message);
    }
}
