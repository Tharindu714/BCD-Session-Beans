package com.deltacodex.ee.ejb.remote;

import jakarta.ejb.Local;

@Local
public interface AppSettings {
    String getAppName();
    String getAppVersion();
    String getAppDescription();
}
