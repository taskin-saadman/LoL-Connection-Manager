import java.util.Scanner;
import java.util.LinkedHashMap;

public class Server {
    //linked hashmap storing registered gamers (order of entry preserved)
    private static LinkedHashMap<String,Gamer> registry = new LinkedHashMap<String,Gamer>();

    /**
     * Main method that runs the command loop.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       //command loop until EXIT is entered
        while(true){
            String input = sc.nextLine();
            String[] tokens = tokenizeInput(input);
            String command = tokens[0].toUpperCase();  //uppercase conversion to make main command case-insensitive

            //switch-case statements for every command-type
            //cleaner than if-else statements
            switch(command){
                case "ADD_GAMER":
                    addGamer(tokens);
                    break;
                case "CONNECT":
                    connect(tokens);
                    break;
                case "DISCONNECT":
                    disconnect(tokens);
                    break;
                case "STATUS":
                    status(tokens);
                    break;
                case "LIST_GAMERS":
                    listGamers(tokens);
                    break;
                case "CHANGE_CONNECTION":
                    changeConnection(tokens);
                    break;
                case "LIST_CONNECTIONS":
                    listConnections(tokens);
                    break;
                case "REMOVE_GAMER":
                    removeGamer(tokens);
                    break;
                case "HELP":
                    help(tokens);
                    break;
                case "EXIT":
                    exit(tokens);
                    break; //won't exit if invalid command format
                default:
                    System.out.println("Error: Invalid command.");
            }
        }   
    }

    /**
     * Tokenizes input with special handling for quoted names
     * @param input The raw input string
     * @return Array of tokens with quotes handled properly
     */
     //used chatgpt to understand how to handle the token array
    private static String[] tokenizeInput(String input){
        //check if there are quotes in the input
        if (input.contains("\"")) {
            int firstQuote = input.indexOf("\"");
            int lastQuote = input.lastIndexOf("\"");//returns the index within this string of the last occurrence of the specified character.
            
            //if we have matching quotes
            if(firstQuote != lastQuote){
                //extract the command part (before the first quote)
                String commandPart = input.substring(0, firstQuote).trim();
                //extract the quoted name (between first and last quotes)
                String quotedName = input.substring(firstQuote + 1, lastQuote);
                //extract any arguments after the quoted name
                String afterQuotes = input.substring(lastQuote + 1).trim();
                
                //create tokens array based on the parts
                if(afterQuotes.isEmpty()){//if there exists just command and quoted name
                    return new String[]{commandPart, quotedName};
                }
                else{
                    //command, quoted name, and additional arguments
                    String[] afterArgs = afterQuotes.split("\\s+");//regex for making array of strings of afterquotes part
                    String[] result = new String[afterArgs.length + 2];
                    result[0] = commandPart;
                    result[1] = quotedName;
                    System.arraycopy(afterArgs, 0, result, 2, afterArgs.length);//method to copy elements from one array to another
                    return result;
                }
            }
        }
        
        //default case: split by whitespace
        return input.split("\\s+");//regex
    }

    /**
     * Registers a new gamer.
     * Expected input: ADD_GAMER <gamer_name>
     * @param tokens The input tokens.
     */
    private static void addGamer(String[] tokens) {
        if(tokens.length != 2){//invalid command format
            System.out.println("Error: Invalid command format.");
            return; //no need to execute rest of this method anymore
        }

        String gamerName = tokens[1];

        //cheking if already exists
        if(registry.containsKey(gamerName)){
            System.out.println("Error: Gamer " + gamerName + " already exists.");
        }
        else{//otherwise adding new gamer info to the LinkedHashMap
            //allocate memory for a new Gamer object and put it in the Map, otherwise error
            registry.put(gamerName, new Gamer(gamerName));
            System.out.println("Gamer " + gamerName + " added successfully.");
        }
    }

    /**
     * Connects a gamer to a server.
     * Expected input: CONNECT <gamer_name> <server_name> <port>
     * @param tokens The input tokens.
     */
    private static void connect(String[] tokens) {
        if(tokens.length != 4){//incorrect no. of arguments
            System.out.println("Error: Invalid command format.");
            return;
        }

        //retrieving data from tokens array
        String gamerName = tokens[1];
        String serverName = tokens[2];
        int port; //creating variable to contain port value

        //ensure that parsing is possible from the String array
        try{
            port = Integer.parseInt(tokens[3]); //port is id in the question
        }
        catch(NumberFormatException e){
        System.out.println("Error: Invalid id number.");
        return;
        }

        if((port < 1024) || (port > 65535)){
            System.out.println("Error: Invalid id number.");
            return;
        }

        //Main Part for connect method
        Gamer gamer = registry.get(gamerName);
    
        if(gamer == null){//name doesnt exist in the LinkedHashMap
            System.out.println("Error: Gamer " + gamerName + " not found.");
        }
        else if(gamer.isConnected()){//was already connected before
        System.out.println("Error: Gamer " + gamerName + " is already connected.");
        }
        else{//if everything else is fine, set connection details
            gamer.setServer(serverName);
            gamer.setPort(port);
            gamer.setConnected(true);
            System.out.println("Gamer " + gamerName + " connected to " + serverName + " with id " + port + ".");
        }
    }

    /**
     * Disconnects a gamer from their current server.
     * Expected input: DISCONNECT <gamer_name>
     * @param tokens The input tokens.
     */
    private static void disconnect(String[] tokens) {
        if(tokens.length != 2){
            System.out.println("Error: Invalid command format.");
            return;
        }

        String gamerName = tokens[1];
        Gamer gamer = registry.get(gamerName);

        if(gamer == null){
            System.out.println("Error: Gamer " + gamerName + " not found.");
        }
        else if(!gamer.isConnected()){
            System.out.println("Error: Gamer " + gamerName + " is not connected.");
        }
        else{
            gamer.setConnected(false);//set the connected status to false
            System.out.println("Gamer " + gamerName + " disconnected successfully.");
        }
    }

    /**
     * Displays the connection status of a gamer.
     * Expected input: STATUS <gamer_name>
     * @param tokens The input tokens.
     */
    private static void status(String[] tokens) {
        if(tokens.length != 2){
            System.out.println("Error: Invalid command format.");
            return;
        }

        String gamerName = tokens[1];
        Gamer gamer = registry.get(gamerName);

        if(gamer == null){
            System.out.println("Error: Gamer " + gamerName + " not found.");
        }
        else if(!gamer.isConnected()){
            System.out.println("Gamer " + gamerName + " is not connected.");
        }
        else{
            System.out.println("Gamer: " + gamerName);
            System.out.println("Server: " + gamer.getServer());
            System.out.println("ID: " + gamer.getPort());
        }
    }

    /**
     * Lists all registered gamers.
     * Expected input: LIST_GAMERS
     * @param tokens The input tokens.
     */
    private static void listGamers(String[] tokens) {
        if (tokens.length != 1){
            System.out.println("Error: Invalid command format.");
            return;
        }

        if(registry.isEmpty()){
            System.out.println("No gamers registered.");
        }
        else{
            System.out.println("Registered Gamers:");
            for(String gamerName : registry.keySet()){//iterating through each key in the LinkedHashMap
                System.out.println("Gamer: " + gamerName);
            }
        }
    }

    /**
     * Updates the connection details for a gamer.
     * Expected input: CHANGE_CONNECTION <gamer_name> <new_server> <new_port>
     * @param tokens The input tokens.
     */
    private static void changeConnection(String[] tokens) {
        if(tokens.length != 4){
            System.out.println("Error: Invalid command format.");
            return;
        }

        String gamerName = tokens[1];
        String newServer = tokens[2];
        int newPort;//will store new port number

        //ensuring that parsing is possible from the String array token
        try{
            newPort = Integer.parseInt(tokens[3]);
        }
        catch (NumberFormatException e) {//could not be parsed
            System.out.println("Error: Invalid id number.");
            return;
        }

        if((newPort < 1024) || (newPort > 65535)) {
            System.out.println("Error: Invalid id number.");
            return;
        }

        Gamer gamer = registry.get(gamerName);
        if(gamer == null){
            System.out.println("Error: Gamer " + gamerName + " not found.");
        }
        else if(!gamer.isConnected()){
            System.out.println("Error: Gamer " + gamerName + " is not connected.");
        }
        else{
            gamer.setServer(newServer);
            gamer.setPort(newPort);
            System.out.println("Gamer " + gamerName + " changed connection to " + newServer + " with id " + newPort + ".");
        }
    }

    /**
     * Lists all active connections.
     * Expected input: LIST_CONNECTIONS
     * @param tokens The input tokens.
     */
    private static void listConnections(String[] tokens) {
        if(tokens.length != 1){
            System.out.println("Error: Invalid command format.");
            return;
        }

        //initializing flag to check if there are any active players or not
        boolean foundConnected = false;

        //checking if there is at least one connected gamer
        for(Gamer gamer : registry.values()){ //iterating through colletion of values (Gamer objects)
            if(gamer.isConnected()){
                System.out.println("Gamer: " + gamer.getName() + " -> Server: " + gamer.getServer() + ", ID: " + gamer.getPort());
                foundConnected = true;//updating the flag
            }
        }

        if(!foundConnected){//no active players
            System.out.println("No gamers are currently connected.");
        }
    }

    /**
     * Removes a registered gamer.
     * Expected input: REMOVE_GAMER <gamer_name>
     * @param tokens The input tokens.
     */
    private static void removeGamer(String[] tokens) {
        if(tokens.length != 2){
            System.out.println("Error: Invalid command format.");
            return;
        }

        String gamerName = tokens[1];
        Gamer gamer = registry.get(gamerName);

        if(gamer == null){
            System.out.println("Error: Gamer " + gamerName + " not found.");
        }
        else if(gamer.isConnected()){
            System.out.println("Error: Cannot remove gamer " + gamerName + " while connected.");
        }
        else{
            registry.remove(gamerName);//remove the key-value pair from our LinkedHashMap
            System.out.println("Gamer " + gamerName + " removed successfully.");
        }
    }

    /**
     * Displays help information.
     * Expected input: HELP
     * @param tokens The input tokens.
     */
    private static void help(String[] tokens) {
        if(tokens.length != 1){
            System.out.println("Error: Invalid command format.");
            return;
        }

        System.out.println("Available Commands:");
        System.out.println("1. ADD_GAMER <gamer_name>");
        System.out.println("2. CONNECT <gamer_name> <server_name> <id>");
        System.out.println("3. DISCONNECT <gamer_name>");
        System.out.println("4. STATUS <gamer_name>");
        System.out.println("5. LIST_GAMERS");
        System.out.println("6. CHANGE_CONNECTION <gamer_name> <new_server> <new_id>");
        System.out.println("7. LIST_CONNECTIONS");
        System.out.println("8. REMOVE_GAMER <gamer_name>");
        System.out.println("9. HELP");
        System.out.println("10. EXIT");
    }

    /**
     * Exits the application.
     * Expected input: EXIT
     * @param tokens The input tokens.
     */
    private static void exit(String[] tokens) {
        if (tokens.length != 1) {
            System.out.println("Error: Invalid command format.");
            return; //exit command was entered improperly
        }

        System.out.println("Exiting League of Legends Connection Manager. Goodbye!");

        //close scanner before exitting
        sc.close();

        //exitting the program right here
        System.exit(0);
    }
}
