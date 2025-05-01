package com.deltacodex.ee.ejb.impl;

import com.deltacodex.ee.ejb.remote.AppSettings;
import com.deltacodex.ee.ejb.remote.UserDetails;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.*;
import java.io.Serializable;

@Stateful
public class UserDetailsBean implements UserDetails, Serializable {

    @EJB
    private transient AppSettings  settings;

    private int counter;

    @PostConstruct
    public void init() {
        System.out.println("UserDetailsBean init" + this);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("UserDetailsBean::destroy");
    }

@Override
@Lock(LockType.WRITE) //If some methods change their states. We should implement @Lock annotation
    public String getName() {
        counter++;
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
           throw new RuntimeException(e);
        }
        return settings.getAppName() + " " + settings.getAppVersion() + "-" + counter + "_" + settings.getAppDescription();
    }

    @PrePassivate
    public void passivate() {
        System.out.println("UserDetailsBean::passivate");
    }

    @PostActivate
    public void activate() {
        System.out.println("UserDetailsBean::activate");
    }

    @Remove
    public void remove() {
        System.out.println("UserDetailsBean::remove");
    }

    @Override
    public String getEmail() {
        return "max.payne.2009@gmail.com";
    }

    @Override
    public String getContact() {
        return "0751441764";
    }
}
