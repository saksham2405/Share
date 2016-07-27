/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileshare;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author aditya
 */
public class Send {
    
    static String filename;
    static ServerSocket server;
    static Socket sock;
    static BufferedOutputStream bos;
    static DataInputStream dis;
    static DataOutputStream dos;
    public Send() {
      filename=null;
      server=null;
      sock=null;
      bos=null;
      dis=null;
      dos=null;
    }
    public void sender(){
        File myfile = null ;
       JFileChooser chosen=new JFileChooser();
        chosen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result=chosen.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION)
        {
          myfile=chosen.getSelectedFile();
          filename=chosen.getSelectedFile().getName();
        }
        System.out.println("filename mil gaya");
       try {
           try {
               server = new ServerSocket(3128);
               System.out.println("server listening ");
           } catch (IOException ex) {
               Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           
       sock = server.accept();
       System.out.println("request accecpted ");
       } catch (IOException ex) {
           Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
       }
   
       
       
      try {
            dis=new DataInputStream(sock.getInputStream());
            System.out.println("dis success ");
            } catch (IOException ex) {
                Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bos=new BufferedOutputStream(sock.getOutputStream());
                dos=new DataOutputStream(bos);
                 System.out.println("bos success ");
            } catch (IOException ex) {
                Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if(!myfile.isFile()){
                    dos.writeBoolean(true);
                       dos.writeUTF(filename);
                       System.out.println("Calling function\n");
                    allfiles(myfile.listFiles());
                }
                else{
                   byte[] data=new byte[1025];
                   int len;
                   FileInputStream fis=new FileInputStream(myfile);
                   BufferedInputStream bis=new BufferedInputStream(fis);
                   dos.writeBoolean(false);
                   dos.writeLong(myfile.length());
                   dos.writeUTF(myfile.getName());
                   while((len=bis.read(data))>0)
                   {
                       dos.write(data,0,len);
                   }
                   fis.close();
                   bis.close();
                }
                    
                System.out.println("allfiles function successfully called");
            } catch (IOException ex) {
                Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
            dos.close();
            dis.close();
            sock.close();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   //A Recursive function to send all files 
   public static void allfiles(File [] files) throws IOException
    {
        System.out.println("In the function\n");
        dos.writeInt(files.length);
        for(int i=0;i<files.length;i++)
        {
            if(files[i].isFile())
            {
                int len=0;
                FileInputStream fis=new FileInputStream(files[i]);
                BufferedInputStream bis=new BufferedInputStream(fis);
                byte[] filedata=new byte[4096];
                dos.writeBoolean(false);
                dos.writeLong(files[i].length());
                dos.writeUTF(files[i].getName());
                System.out.println("Sending file "+files[i].getName());
                while((len=bis.read(filedata))>0)
                {
                    dos.write(filedata,0,len);
                }
                System.out.println("File "+files[i].getName());
                fis.close();
                bis.close();
            }
            else
            {
                dos.writeBoolean(true);
                dos.writeUTF(files[i].getName());
                System.out.println("Sending subd "+files[i].getName());
                allfiles(files[i].listFiles());
                System.out.println("Sending finished "+files[i].getName());
            }
        }
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   

}
   
   

