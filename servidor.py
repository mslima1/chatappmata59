import socket
import threading

s = socket.socket (socket.AF_INET, socket.SOCK_STREAM)
serverOnline = True
ip = str (socket.gethostbyname (socket.gethostname ()))
port = 3300

clients = {}

s.bind ((ip, port))
s.listen ()
print ('IP do servidor: %s' % ip)


def handleClient(client, nickname):
    clientConnected = True
    keys = clients.keys ()
    help = ('-w para usuarios lista de online; -q para se desconectar')

    while clientConnected:
            msg = client.recv (1024).decode ('ascii')
            response = 'Usuarios conectados: \n'
            found = False
            if '-w' in msg:
                clientNo = 0
                for name in keys:
                    response = response + '-' + name + '\n'
                client.send (response.encode ('ascii'))
            elif '-h' in msg:
                client.send (help.encode ('ascii'))
            elif '-q' in msg:
                response = 'Desconectado'
                client.send (response.encode ('ascii'))
                clients.pop (nickname)
                print (nickname + ' se desconectou')
                clientConnected = False
            else:
                for k, v in clients.items():
                    v.send(msg.encode('ascii'))


while serverOnline:
    client, address = s.accept ()
    nickname = client.recv (1024).decode ('ascii')
    print ('%s se conectou ao servidor' % str (nickname))
    client.send ('Bem vindo ao chat! '.encode ('ascii'))

    if (client not in clients):
        clients[nickname] = client
        threading.Thread (target=handleClient, args=(client, nickname,)).start ()

