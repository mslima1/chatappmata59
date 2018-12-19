
package cliente;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import static javax.swing.JOptionPane.*;

public class Chat extends javax.swing.JFrame {
    
    private Socket s;
    private DatagramSocket ds;
    private String nome;
    private BufferedReader br;
    private InputStreamReader isr;
    private boolean rodar;
    private InetAddress endereco;
    


    public Chat(String nome) {
        initComponents();
        this.nome = nome;
        rodar = true;
        
        try{
            s = new Socket("127.0.0.1",5000);
            ds = new DatagramSocket();
            endereco = InetAddress.getLocalHost(); //mudar pra getebyname?
//            ds = new DatagramSocket();
//            byte [] buffer;
//            buffer = new String("datagrama").getBytes();
//            InetAddress endereco = InetAddress.getByName("host");
//            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, endereco, 10000);
//            ds.send(pacote);
//            
//            byte [] buffer2 = new byte[100];
//            pacote = new DatagramPacket(buffer2, buffer2.length, endereco, 10000);
//            ds.receive(pacote);
//            System.out.println(new String(pacote.getData()));
            
            
        }catch(Exception e){
            
            showMessageDialog(null, "Não se conectou com o servidor","",ERROR_MESSAGE);
            System.exit(0);
        }
        Thread();
        
    }	
    private void Thread(){
        Thread t = new Thread(new Runnable() {
            String mensagem;
            @Override
            public void run() {
                try {
                    isr = new InputStreamReader(s.getInputStream());
                    br = new BufferedReader(isr);
                    
                    while((mensagem = br.readLine())!= null){
                        msgRec.setText(msgRec.getText()+ mensagem + "\n");
                        if(!rodar){
                            break;
                        }
                    }
                    
                } catch (IOException e) {
                    showMessageDialog(null,"Erro na conexão ao servidor","",ERROR_MESSAGE);
                }
                  
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        t.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msgRec = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        msgEnv = new javax.swing.JTextArea();
        BtEnv = new javax.swing.JButton();
        Sair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msgRec.setColumns(20);
        msgRec.setRows(5);
        jScrollPane1.setViewportView(msgRec);

        msgEnv.setColumns(20);
        msgEnv.setRows(5);
        msgEnv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msgEnvKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(msgEnv);

        BtEnv.setText("Enviar");
        BtEnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEnvActionPerformed(evt);
            }
        });

        Sair.setText("Sair");
        Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtEnv, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(Sair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtEnv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Sair)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtEnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEnvActionPerformed
        String mensagem = nome+ " Disse: ";
        
        try {
            PrintStream ps = new PrintStream(s.getOutputStream());
            mensagem += msgEnv.getText();
            byte [] buffer;
            buffer = msgEnv.getText().getBytes();
            DatagramPacket pacote = new DatagramPacket	(buffer, buffer.length, endereco, 10000);
            ds.send(pacote);
            
            
            ps.println(mensagem);
            ps.flush();
             
            msgEnv.setText("");
            
            
            
            
        } catch (Exception e) {
            showMessageDialog(null, "Não conseguiu enviar mensagem","",ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_BtEnvActionPerformed

    private void msgEnvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msgEnvKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String mensagem = nome+ " Disse: ";

            try {
               PrintStream ps = new PrintStream(s.getOutputStream());
               mensagem += msgEnv.getText();
               byte [] buffer;
               buffer = msgEnv.getText().getBytes();
               DatagramPacket pacote = new DatagramPacket	(buffer, buffer.length, endereco, 10000);
               ds.send(pacote);
               
               
               ps.println(mensagem);
               ps.flush();
                
               msgEnv.setText("");
               

            }catch (IOException e) {
               showMessageDialog(null, "Não conseguiu enviar mensagem","",ERROR_MESSAGE);
           }

            
        }
    }//GEN-LAST:event_msgEnvKeyPressed

    private void SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SairActionPerformed
        // TODO add your handling code here:
        try {
    
            s.close();
            System.exit(0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_SairActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtEnv;
    private javax.swing.JButton Sair;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea msgEnv;
    private javax.swing.JTextArea msgRec;
    // End of variables declaration//GEN-END:variables
}
