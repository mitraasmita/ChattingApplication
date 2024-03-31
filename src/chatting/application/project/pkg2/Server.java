package chatting.application.project.pkg2;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener //we are not extending because we have to extend statically
{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();//made static for line 166
    static JFrame f = new JFrame();//that is why we create an object of the frame to extend statically.
    static DataOutputStream dout;
    Server()
    {
        f.setLayout(null);//now whatever is in the frame you have add them using 'f.' because now it is not called statically.
        
        JPanel p = new JPanel();
        p.setBackground(new Color(9,90,70));
        p.setBounds(0,0,380,55);
        p.setLayout(null);
        f.add(p);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/back-removebg-preview.png"));
        Image i2= i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,15,25,25);
        p.add(back);
        
        back.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent ae)
            {
                    System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/profile3-removebg-preview.png"));
        Image i5= i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(25,5,50,50);
        p.add(profile);
        
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video (1).png"));
        Image i8= i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(220,5,50,50);
        p.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone (1).png"));
        Image i11= i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(270,5,50,50);
        p.add(phone);
        
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon (1).png"));
        Image i14= i13.getImage().getScaledInstance(15,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel options = new JLabel(i15);
        options.setBounds(310,5,50,50);
        p.add(options);
        
        
        JLabel name=new JLabel("Radhika");
        name.setBounds(80,18,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        p.add(name);
        
        a1 = new JPanel();
        a1.setBounds(5,60,370,400);
        f.add(a1);
        
        text= new JTextField();
        text.setBounds(5,465,270,27);
        text.setFont(new Font("SANS_SERIF",Font.PLAIN,18));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(280,465,90,27);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);
               
        f.setSize(380,500);
        f.setLocation(200,100);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
       try
       {
        String out =  text.getText();
       
       JPanel p2 = formatLabel(out);
             
      a1.setLayout(new BorderLayout());
      
      JPanel right = new JPanel(new BorderLayout());
      right.add(p2, BorderLayout.LINE_END);
      vertical.add(right);
      vertical.add(Box.createVerticalStrut(10));
      
     a1.add(vertical,BorderLayout.PAGE_START);
     
     dout.writeUTF(out);
     
     text.setText("");
     
     f.repaint();
     f.invalidate();
     f.validate();
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }
    
    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 140px\">" + out +"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN, 16));
        output.setBackground(new Color(27,190,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
  
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
                
        return panel;
    }
    
    public static void main(String [] args)
    {
        new Server();
        
        try
        {
            ServerSocket skt = new ServerSocket(6001);
            while(true)
            {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());
                
                while(true)
                {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
                
    }
    
}
