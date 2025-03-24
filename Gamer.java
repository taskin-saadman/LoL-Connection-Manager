public class Gamer {
    private String name;
    private boolean connected;
    private String server;
    private int port;

    /**
     * Constructs a new Gamer with the specified name.
     * @param name The gamer's name.
     */
    public Gamer(String name) {
        this.name = name;
    }

    /**
     * Returns the gamer's name.
     * @return The name of the gamer.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the gamer is connected.
     * @return true if connected, false otherwise.
     */
    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Sets the gamer's connection status.
     * @param connected true if connected, false otherwise.
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Returns the server the gamer is connected to.
     * @return The server name.
     */
    public String getServer() {
        return this.server;
    }

    /**
     * Sets the server the gamer is connected to.
     * @param server The server name.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Returns the port number the gamer is connected on.
     * @return The port number.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Sets the port number for the gamer's connection.
     * @param port The port number.
     */
    public void setPort(int port) {
        this.port = port;
    }
}
