import socket
import threading

MAX_SIZE = 1024
ENCODE_TYPE = 'ascii'

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
port = 3300
ip = input('Digite o IP do servidor: ')

s.connect((ip, port))
nickname = input("Digite seu nome: ")
s.send(nickname.encode(ENCODE_TYPE))

clientOnline = True

def receiveMsg(sckt):
    while clientOnline:
            msg = sckt.recv(MAX_SIZE).decode(ENCODE_TYPE)
            print(msg)

threading.Thread(target = receiveMsg, args = (s,)).start()

while clientOnline:
    aux = input()
    msg = '[' + nickname +  ']' + ': ' + aux
    if '-q' in msg:
        clientOnline = False
        s.send('-q'.encode(ENCODE_TYPE))
    else:
        s.send(msg.encode(ENCODE_TYPE))