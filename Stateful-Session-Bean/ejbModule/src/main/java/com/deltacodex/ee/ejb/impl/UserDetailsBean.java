package com.deltacodex.ee.ejb.impl;

import com.deltacodex.ee.ejb.remote.AppSettings;
import com.deltacodex.ee.ejb.remote.UserDetails;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.*;
//import jakarta.ejb.Stateless;

/**
 * <h3>Where Can we use @Stateful --> Practical Use Cases</h3>
 * 1. In an E commerce Application we can Use it for cart --> In Multi Platform Selling  <br>
 * 2. In a Chat Application we can connect main app to other recommended applications using @Stateful <br>
 * 3. Where u need to manage user state
 */
@Stateful
public class UserDetailsBean implements UserDetails {

    @EJB
    private AppSettings settings;

    private int counter;

    @PostConstruct
    public void init() {
        System.out.println("UserDetailsBean init" + this);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("UserDetailsBean::destroy");
    }

/**
    Now multiple threads can't use this simultaneously. <br>
    Now it need to do it according to the LockType
    @Lock (LockType.WRITE) --> This way multiple threads can't access this simultaneously
    @Lock (LockType.READ) --> This way multiple threads can access this simultaneously
 */
@Override
@Lock (LockType.WRITE)
    public String getName() {
        counter++;
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
           throw new RuntimeException(e);
        }
        return settings.getAppName() + " " + settings.getAppVersion() + "-" + counter + "_" + settings.getAppDescription();
    }

    /**
     With this annotation we can remove the existing session --> Check Server log to see Result
     */
    @Remove
    public void remove (){
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
