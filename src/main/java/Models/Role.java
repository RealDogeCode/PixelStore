package Models;

import java.io.Serializable;

public enum Role implements Serializable {
    USER(1),
    ADMIN(2),
    PUBLISHER(3);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role fromValue(int value) {
        for (Role role : values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException();
    }
}