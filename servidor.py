import socket
import threading
import time

MAX_SIZE = 1024
ENCODE_TYPE = 'ascii'

s = socket.socket (socket.AF_INET, socket.SOCK_STREAM)
ip = str (socket.gethostbyname (socket.gethostname ()))
port = 3300

s.bind ((ip, port))
s.listen ()

clients = {}
serverOnline = True
print ('IP do servidor: %s' % ip)

def broadcast(data):
    for k, v in clients.items ():
        v.send (data.encode (ENCODE_TYPE))

def handleClient(client, nickname):
    clientConnected = True
    keys = clients.keys ()
    help = ('-w para lista de usuarios online; -q para se desconectar')
    quitMsg = ('%s se desconectou do chat' %str(nickname))
    numUsers = 'Usuarios conectados: \n'

    while clientConnected:
            msg = client.recv (MAX_SIZE).decode (ENCODE_TYPE)
            print (time.ctime (time.time ()) + str (address) + ' ' + str (msg))
            if '-w' in msg:
                clientNo = 0
                for name in keys:
                    numUsers = numUsers + '-' + name + '\n'
                client.send (numUsers.encode (ENCODE_TYPE))
            elif '-h' in msg:
                client.send (help.encode (ENCODE_TYPE))
            elif '-q' in msg:
                response = 'Desconectado'
                client.send (response.encode (ENCODE_TYPE))
                clients.pop (nickname)
                print (nickname + ' se desconectou')
                broadcast(quitMsg)
                clientConnected = False
            else:
                broadcast(msg)

while serverOnline:
    client, address = s.accept ()
    nickname = client.recv (MAX_SIZE).decode (ENCODE_TYPE)
    print ('%s se conectou ao servidor' % str (nickname))
    client.send ('Bem vindo ao chat! '.encode (ENCODE_TYPE))
    welcomeMsg = ('%s se conectou ao chat' % str(nickname))
    broadcast (welcomeMsg)

    if (client not in clients):
        clients[nickname] = client
        threading.Thread (target=handleClient, args=(client, nickname,)).start ()

