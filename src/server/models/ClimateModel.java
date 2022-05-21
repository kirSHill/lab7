package server.models;

import java.util.Arrays;

public class ClimateModel extends Model {

    public ClimateModel() {
        this.name = "Climates";
        this.fields = Arrays.asList(new FieldDB("id","SERIAL PRIMARY KEY"),
                new FieldDB("name","varchar(256)"));
    }
}
