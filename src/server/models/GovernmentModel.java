package server.models;

import java.util.Arrays;

public class GovernmentModel extends Model {

    public GovernmentModel() {
        this.name = "Governments";
        this.fields = Arrays.asList(new FieldDB("id","SERIAL PRIMARY KEY"),
                new FieldDB("name","varchar(256)"));
    }
}
