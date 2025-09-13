package newpackage;
import java.sql.*;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.*;  
import java.awt.*; 
public class Menu extends javax.swing.JFrame implements Runnable, ThreadFactory {
     private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    public Menu() {
        Image icon = Toolkit.getDefaultToolkit().getImage("D:\\HTML\\Attendace-check.jpg");    
this.setIconImage(icon);  
           try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        initWebcam();
         
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        result_field = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);
        setLocation(new java.awt.Point(150, 150));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 255, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(1100, 900));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 900));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1080, 570));

        jScrollPane1.setBackground(new java.awt.Color(198, 94, 94));
        jScrollPane1.setAlignmentX(10.0F);

        result_field.setBackground(new java.awt.Color(255, 255, 51));
        result_field.setColumns(20);
        result_field.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        result_field.setLineWrap(true);
        result_field.setRows(5);
        result_field.setToolTipText("");
        result_field.setWrapStyleWord(true);
        result_field.setAlignmentX(100.0F);
        result_field.setAlignmentY(50.0F);
        result_field.setAutoscrolls(false);
        result_field.setFocusable(false);
        jScrollPane1.setViewportView(result_field);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 650, 450, 240));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("SCAN YOUR QR CODE HERE");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 350, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
//
//    public static void main(String args[]) {
//      
////        java.awt.EventQueue.invokeLater(() -> {
////            new Menu().setVisible(true);
////        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea result_field;
    // End of variables declaration//GEN-END:variables
private WebcamPanel panel = null;
    private Webcam webcam = null;
    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 570));

        executor.execute(this);
    }
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException e) {
                //No result...
            }
            if (result != null) {
                result_field.setText(result.getText());
              
               // result_field..setAlignmentX(JLabel.CENTER_ALIGNMENT);;
               // result_field.setVerticalAlignment(SwingConstants.CENTER);
                result_field.append("\nAttendance   Marked");      
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MMM");  
   LocalDateTime now = LocalDateTime.now(); 
   DateTimeFormatter dt = DateTimeFormatter.ofPattern("MMM");  
   LocalDateTime nw = LocalDateTime.now(); 
   String mn=dt.format(nw);
   if(mn.equals("Dec"))
       mn="decr";
                            try{  
Class.forName("com.mysql.cj.jdbc.Driver");  
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","root");   
Statement stmt=con.createStatement();  
Statement stm=con.createStatement();
Statement sm=con.createStatement();
String s="update "+mn+" set "+dtf.format(now)+"=1 where rollno="+result.getText();
stmt.executeUpdate(s);  
String s1="select *from "+mn+" where rollno="+result.getText();
ResultSet rs=stm.executeQuery(s1);
String s2="select count(*) from information_schema.columns where table_name='"+mn+"'";
ResultSet rs2=sm.executeQuery(s2);
int count;
rs2.next();
count=rs2.getInt(1);

float per=0f;
rs.next();
for(int i=3;i<=count;i++)
{
    
    per=per+rs.getInt(i);
    
}

count=count-2;
per=(per/count)*100;
String st;
 st="update "+mn+" set percentage="+String.valueOf(per)+" where rollno="+result.getText();
 Statement smt=con.createStatement();
smt.executeUpdate(st); 
 con.close();  
}catch(Exception e){}
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
}
