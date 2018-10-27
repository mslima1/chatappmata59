import socket

socketServidor = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
porta = 3030
ipServidor = str(socket.gethostbyname(socket.gethostname()))

socketServidor.bind((ipServidor, porta))
socketServidor.listen()

servidorOnline= True
print('Ip do servidor: %s' %ipServidor)

while servidorOnline:
    cliente, enderecoCliente = socketServidor.accept()
    print('%s se conectou ao servidor' %str(enderecoCliente))
    cliente.send('Bem vindo ao chat'.encode('ascii'))
    cliente.close()
