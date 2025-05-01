package com.deltacodex.ee.ejb.remote;

import jakarta.ejb.Remote;

@Remote
public interface UserDetails {
    String getName();
    String getEmail();
    String getContact();
    void remove();
}
