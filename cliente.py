import socket

socketCliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
porta = 3030

ip = input('Digite o IP do servidor: ')
socketCliente.connect((ip, porta))

msg = socketCliente.recv(1024)
print(msg.decode('ascii'))
socketCliente.close()
