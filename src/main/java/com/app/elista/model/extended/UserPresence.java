package com.app.elista.model.extended;

import com.app.elista.model.Presences;
import com.app.elista.model.Users;

public class UserPresence {

    private Users user;
    private Presences presence;

    public UserPresence(Users user, Presences presence) {
        this.user = user;
        this.presence = presence;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Presences getPresence() {
        return presence;
    }

    public void setPresence(Presences presence) {
        this.presence = presence;
    }
}
