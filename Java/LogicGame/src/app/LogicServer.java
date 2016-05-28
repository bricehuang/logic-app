package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import game.GameBoard;

public class LogicServer {

    private static final int DEFAULT_PORT = 1337;
    
    private final GameBoard gameBoard = new GameBoard();
    
    private final ServerSocket serverSocket;
    
    private int numPlayers = 0;
    
    private final Thread[] clientThreads = new Thread[4];
    
    // TODO this is jank
    // basically player x's handler thread calls threadControls[x].wait when he has to wait, and 
    // the master server thread calls threadControls[x].notify when player x gets to go again
    private final Object[] threadControls = {new Object(), new Object(), new Object(), new Object()}; 
    
    /**
     * Make a LogicServer that listens for connections on port.   
     * 
     * @param port port number, requires 0 <= port <= 65535
     * @throws IOException if an error occurs opening the server socket
     */
    public LogicServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    
    /**
     * Run the server, listening for client connections and handling them.
     * Never returns unless an exception is thrown.
     * 
     * @throws IOException if the main server socket is broken
     *                     (IOExceptions from individual clients do *not* terminate serve())
     */
    public void serve() throws IOException {
        //TODO bug: numPlayers is very very not threadsafe
        
        // phase while clients are connecting
        while (numPlayers<4) {
            // wait for a client to connect
            Socket socket = serverSocket.accept();
            
            // handle a connection
            // Basically this starts a new thread that handles the connection with
            // a connecting client.  Don't worry about this, this is boilerplate 
            // code.
            Thread thread = new Thread(new Runnable(){
                public void run(){
                    try {
                        handleConnection(socket, numPlayers-1);  
                    } catch (IOException ioe) {
                        ioe.printStackTrace(); // but don't terminate serve()
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }                                    
                }
            });

            // Client 
            
            clientThreads[numPlayers] = thread;
            numPlayers++; //TODO synchronization issues here, this is jank
            
            thread.start();
        }
        
        // Client threads are asleep until four clients come online.
        // This code wakes up the clients.  
        for (Object obj: threadControls){
            synchronized(obj){
                obj.notify();                
            }
        }
        //TODO bug: last player to enter gets his threadControl object notified before it's put on wait,
        //so he gets put on wait again and gets stuck

        //TODO main phase of game
        
    }
    
    /**
     * Handle a single client connection. Returns when client disconnects.
     * 
     * @param socket socket where the client is connected
     * @param playerID 0-3 ID of player
     * @throws IOException if the connection encounters an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket, int playerID) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        
        try {
            out.println("Welcome to Logic! You are player #" + playerID + ".");
            out.println("Please wait for four players to arrive.");

            // phase while clients are connecting
            
            // while fewer than 4 players, wait
            synchronized(threadControls[playerID]){
                try{
                    threadControls[playerID].wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            
            out.println("Game has begun! Please set up your cards.");
            out.println(gameBoard.showPlayerOwnCards(playerID));
            
            // setup phase: clients can swap same-value cards
            // this loop continues until client enters "done" 
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String output = handleRequestSetupPhase(line, playerID);
                if (output.equals("done")){
                    out.println("Yay! Wait for other players to finish setup...");
                    break;
                }
                if (output != null){
                    out.println(output);
                }
            }
            
            // TODO main phase of game

        } finally {
            out.close();
            in.close();
        }
    }

    /**
     * Handler for client input in the setup phase
     * @param in client message.  Should be of form "swap [position]", "view", "help", or "done".  
     * @return message to client, or null
     */
    private String handleRequestSetupPhase(String in, int playerID){
        String regex = "(view)|(help)|(done)|(swap [0-5])";
        String helpMessage = "same";//TODO
        if (!in.matches(regex)){
            // invalid input
            return helpMessage;
        }
        else if(in.equals("view")){
            return gameBoard.showPlayerOwnCards(playerID);
        }
        else if(in.equals("help")){
            return helpMessage;
        }
        else if(in.equals("done")){
            return "done";
        }
        else if(in.matches("swap [0-5]")){
            Integer position = Character.getNumericValue(in.charAt(5));
            gameBoard.swapTwoEqualCards(playerID, position);
            return gameBoard.showPlayerOwnCards(playerID);
        }
        // should not get here
        throw new UnsupportedOperationException("Should not get here");
    }
    
    /**
     * Handler for client input in the main game phase
     * @param in client message.  Should be of form "swap [position]", "view", "help", or "done".  
     * @return message to client, or null
     */
    private String handleRequestMainPhase(String in, int playerID){
        //TODO
         throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Main
     * @param args bleh
     */
    public static void main(String[] args){
        // TODO provide command line interface??
        try{
            runLogicServer(DEFAULT_PORT);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Starts a Logic Server 
     * @param port the port at which this LogicServer listens
     * @throws IOException
     */
    private static void runLogicServer(int port) throws IOException{
        LogicServer server = new LogicServer(port);
        server.serve();
    }

}
