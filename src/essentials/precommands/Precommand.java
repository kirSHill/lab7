package essentials.precommands;

import java.io.Serializable;

public interface Precommand extends Serializable {

    void preprocess(Object object, boolean fromScript);

    String getCommandName();

    Object getArgument();

    boolean isFromScript();

    void setClient(String login);

    String getClient();
}
