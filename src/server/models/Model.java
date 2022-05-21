package server.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public abstract class Model {

    protected List<FieldDB> fields;
    protected String name;

    public Model(){

    }

    public String getName() {
        return name;
    }

    public boolean createTable(Connection connection) {

        PreparedStatement statement;

        StringBuilder createSql = new StringBuilder();
        try {
            createSql.append("CREATE TABLE ").append(name).append("(\n");
            for (FieldDB el : fields) {
                createSql.append(el.getFieldString()).append(",");
            }
            createSql.deleteCharAt(createSql.length()-1);
            createSql.append(");");

            statement = connection.prepareStatement(createSql.toString());
            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }


}
