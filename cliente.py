import socket
import threading

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
port = 3300


ip = input('Digite o IP do servidor: ')

s.connect((ip, port))
nickname = input("Digite seu nome: ")
s.send(nickname.encode('ascii'))

clientOnline = True

def receiveMsg(sckt):
    while clientOnline:
            msg = sckt.recv(1024).decode('ascii')
            print(msg)


threading.Thread(target = receiveMsg, args = (s,)).start()

while clientOnline:
    aux = input()
    msg = nickname + ':' + aux
    if '-q' in msg:
        clientOnline = False
        s.send('-q'.encode('ascii'))
    else:
        s.send(msg.encode('ascii'))