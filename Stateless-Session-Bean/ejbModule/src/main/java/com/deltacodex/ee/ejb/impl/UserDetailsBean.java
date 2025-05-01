package com.deltacodex.ee.ejb.impl;

import com.deltacodex.ee.ejb.remote.AppSettings;
import com.deltacodex.ee.ejb.remote.UserDetails;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import javax.naming.InitialContext;
import javax.naming.NamingException;

//@Stateless (name = "UserInfo", mappedName = "User_mappedName")
@Stateless
public class UserDetailsBean implements UserDetails {

    //    int count; --> Instance variables are not recommended... if Use them reset it
    @EJB private AppSettings settings;
   // @EJB private AppSettings appSettings;

    @PostConstruct
    public void init() {
        System.out.println("UserDetailsBean init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("UserDetailsBean::destroy");
    }

    @Override
    public String getName() {
//        try{
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        count++;
//        return "Max Payne: " + count; --> This method is not recommended

//        try{
//            InitialContext ctx = new InitialContext();
//           AppSettings app_settings = (AppSettings) ctx.lookup("java:global/EJBModule/AppSettingsBean");
//           return app_settings.getAppName();
//        } catch (NamingException e) {
//            throw new RuntimeException(e);
//        } //--> Before Implement @EJB Annotation

//        return "Max Payne";
        //System.out.println(settings);  //--> com.deltacodex.ee.ejb.remote.AppSettings_1674898579 (Proxy Object)
        //System.out.println(appSettings);  //--> com.deltacodex.ee.ejb.remote.AppSettings_1674898579 (Proxy Object)
       return settings.getAppName() + " " + settings.getAppVersion() + "-" + settings.getAppDescription();
       //After implementing @EJB Annotation
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
