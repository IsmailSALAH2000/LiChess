# LiChess
analysis and processing of data from large files using the Thread Pool Design Pattern with a multi-user architecture via a TCP network.

# To execute the project:
It will be necessary to put the data file in the "data" folder at the same level as pgnHandler and execute the Main class (java data/Main data/myFile.pgn) and then launch the server and the client and follow the "test game" part described in the report.

# The idea of the project:
The principle is to retrieve the data.pgn file that is browsed while creating in passing the objectscorresponding to the retrieved data (Player, Game) that are stored in tables that are serialized. The server then retrieves the ConcurrentHashMap with the desired information. When a client connects to the server, it can send a request. The server retrieves it, analyzes it, makes the necessary calculations and returns the desired information to the client. There can be at most four clients connected to the server, and the clients must be able to get their responses simultaneously.

![image](https://user-images.githubusercontent.com/116093616/225275643-e421a0dc-35ea-471c-b1cf-2ba2d042ef12.png)

#Credit
the network part was made by ROBINET Perrine
