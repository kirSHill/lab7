package essentials.precommands;

public class ObjectPrecommand implements Precommand{

    private Object argument;
    private final String commandName;
    private boolean fromScript;
    private String client;

    public ObjectPrecommand(String name) {
        this.commandName = name;
    }

    @Override
    public void preprocess(Object object, boolean fromScript) {
        this.argument = object;
        this.fromScript = fromScript;
    }

    @Override
    public String getCommandName() {
        return this.commandName;
    }

    @Override
    public Object getArgument() {
        return this.argument;
    }

    @Override
    public boolean isFromScript() {
        return this.fromScript;
    }

    @Override
    public void setClient(String login) {
        this.client = login;
    }

    @Override
    public String getClient() {
        return this.client;
    }
}
