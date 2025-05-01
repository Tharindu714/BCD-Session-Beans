package com.deltacodex.ee.ejb.impl;

import com.deltacodex.ee.ejb.remote.AppSettings;
import jakarta.ejb.Stateless;

@Stateless
public class AppSettingsBean implements AppSettings {
    @Override
    public String getAppName() {
        System.out.println(this);
        return "EJB Module";
    }

    @Override
    public String getAppVersion() {
        return "1.0.0";
    }

    @Override
    public String getAppDescription() {
        return "This is the EJB Module";
    }
}
