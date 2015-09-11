package br.com.gsn.sysbusapp.abstraction;

/**
 * Created by Geison on 06/09/2015.
 */
public interface BusinessDelegate<T> {
    abstract T getBusinessDelegate();
    abstract void setBusinessDelegate();
}
