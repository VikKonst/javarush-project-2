package com.javarush.island.species.animals.abstractItems;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class BasicEntity implements Serializable {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity that = (BasicEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
