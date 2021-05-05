package model;

import lombok.Getter;
import lombok.Setter;

public class Dashboard {
    private @Getter @Setter Integer total, count, promedio;
    private @Getter @Setter String name, genero, country;
}
