import socket
import threading

# Server configuration
HOST = '127.0.0.1'
PORT = 12345

# List to keep track of connected clients
clients = []

def broadcast(message, client_socket):
    """Send message to all clients except the sender."""
    for client in clients:
        if client != client_socket:
            try:
                client.send(message)
            except:
                clients.remove(client)

def handle_client(client_socket):
    """Handle incoming messages from a client."""
    while True:
        try:
            message = client_socket.recv(1024)
            if message:
                print(f"Received: {message.decode('utf-8')}")
                broadcast(message, client_socket)
            else:
                clients.remove(client_socket)
                break
        except:
            clients.remove(client_socket)
            break

def main():
    """Set up the server and listen for incoming connections."""
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((HOST, PORT))
    server_socket.listen()
    print(f"Server started on {HOST}:{PORT}")

    while True:
        client_socket, client_address = server_socket.accept()
        print(f"New connection from {client_address}")
        clients.append(client_socket)

        # Start a new thread for each client
        thread = threading.Thread(target=handle_client, args=(client_socket,))
        thread.start()

if __name__ == "__main__":
    main()
