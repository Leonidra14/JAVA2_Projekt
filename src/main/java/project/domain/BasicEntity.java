package project.domain;

import java.io.Serializable;

public interface BasicEntity<ID> extends Serializable {
    ID getId();
}
