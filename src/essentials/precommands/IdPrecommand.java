package essentials.precommands;

public class IdPrecommand implements Precommand{

    private Object argument;
    private final String commandName;
    private final String id;
    private boolean fromScript;
    private String client;

    public IdPrecommand(String name, String id) {
        this.commandName = name;
        this.id = id;
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

    public String getId() {
        return id;
    }
}
