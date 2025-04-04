# LoL-Connection-Manager

A console-based Java application that simulates a connection manager for League of Legends gamers. This program allows gamers to register, connect to a server using a specific ID, disconnect, update their connection, check their status, and remove their registration.

## Features
- Add gamers to the system
- Connect gamers to a server with a specific ID
- Disconnect gamers from a server
- Check gamer connection status
- List all registered gamers
- Change server connection details
- List all active connections
- Remove gamers from the system
- Help command to display available commands
- Permanently ban a player from a server
- Exit the application

## Installation
1. Ensure you have **Java 8 or later** installed on your system.
2. Clone this repository or download the files manually:
   ```sh
   git clone https://github.com/your-username/LoL-Connection-Manager.git
   ```
3. Navigate to the project directory:
   ```sh
   cd LoL-Connection-Manager
   ```
4. Compile the Java files:
   ```sh
   javac Gamer.java Server.java
   ```
5. Run the application:
   ```sh
   java Server
   ```

## Usage
Once the application is running, enter any of the following commands in the console:

### Commands:
- **ADD_GAMER gamer_name**
  - Registers a new gamer.
  - Example: `ADD_GAMER JohnDoe` or `ADD_GAMER "Faker Pro"`
  - Error: Gamer already exists or invalid command format.

- **CONNECT gamer_name server_name id**
  - Connects a gamer to a server with a specific ID (1024-65535).
  - Example: `CONNECT JohnDoe NA 4500`
  - Error: Gamer not found, already connected, invalid ID, or invalid command format.

- **DISCONNECT gamer_name**
  - Disconnects a gamer from their current server.
  - Example: `DISCONNECT JohnDoe`
  - Error: Gamer not found, not connected, or invalid command format.

- **STATUS gamer_name**
  - Displays the connection status of a gamer.
  - Example: `STATUS JohnDoe`
  - Error: Gamer not found, or invalid command format.

- **LIST_GAMERS**
  - Lists all registered gamers.
  - Output: "Registered Gamers:" followed by the list of gamers.
  - Error: Invalid command format.

- **CHANGE_CONNECTION gamer_name new_server new_id**
  - Updates the connection details for a gamer.
  - Example: `CHANGE_CONNECTION JohnDoe EU 5500`
  - Error: Gamer not found, not connected, invalid ID, or invalid command format.

- **LIST_CONNECTIONS**
  - Lists all active connections.
  - Output: "Gamer: <gamer_name> -> Server: <server_name>, ID: <id>"
  - Error: Invalid command format.

- **REMOVE_GAMER gamer_name**
  - Removes a gamer from the registry (only if disconnected).
  - Example: `REMOVE_GAMER JohnDoe`
  - Error: Gamer not found, currently connected, or invalid command format.

- **HELP**
  - Displays a list of available commands in the required order.
  - Error: Invalid command format.

- **EXIT**
  - Exits the application.
  - Output: "Exiting League of Legends Connection Manager. Goodbye!"
  - Error: Invalid command format.

## Example Usage
```
> ADD_GAMER JohnDoe
Gamer JohnDoe added successfully.

> ADD_GAMER "Faker Pro"
Gamer Faker Pro added successfully.

> CONNECT JohnDoe NA 4500
Gamer JohnDoe connected to NA with id 4500.

> STATUS JohnDoe
Gamer: JohnDoe
Server: NA
ID: 4500

> LIST_CONNECTIONS
Gamer: JohnDoe -> Server: NA, ID: 4500

> DISCONNECT JohnDoe
Gamer JohnDoe disconnected successfully.

> REMOVE_GAMER JohnDoe
Gamer JohnDoe removed successfully.
```

## Contributing
Contributions are welcome! If you have suggestions, feel free to open an issue or submit a pull request.

## Author
Taskin Saadman (GitHub: @taskin-saadman)

