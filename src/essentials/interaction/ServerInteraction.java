package essentials.interaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerInteraction implements UserInteraction {
    private ObjectOutputStream dataOutputStream;
    private ObjectInputStream dataInputStream;

    public ServerInteraction(ObjectInputStream dataInputStream, ObjectOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }


    @Override
    public void print(boolean newLine, String message) {
        try {
            if(newLine) {
                dataOutputStream.writeBytes(message+"\n");
            } else {
                dataOutputStream.writeBytes(message);
            }
        } catch (Exception e) {}
    }

    @Override
    public String getData() {
        try {
            return dataInputStream.readUTF();
        } catch (Exception e) {
            return "";
        }
    }

    public void sendData(Object o) throws IOException {
        dataOutputStream.writeObject(o);
    }

    public Object readData() throws  IOException, ClassNotFoundException {
        return dataInputStream.readObject();
    }

    @Override
    public String getSecureData() {
        return null;
    }
}
