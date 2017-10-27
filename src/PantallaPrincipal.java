import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author francisco.perdomo
 */
public class PantallaPrincipal extends javax.swing.JFrame {
    SmartcardTests sc = new SmartcardTests();
    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        logoAgesic = new javax.swing.JLabel();
        img2 = new javax.swing.JLabel();
        tFechaVenc = new javax.swing.JTextField();
        tFechaEm = new javax.swing.JTextField();
        lFechaEm = new javax.swing.JLabel();
        tCedula = new javax.swing.JTextField();
        lFechaVenc = new javax.swing.JLabel();
        tNombre = new javax.swing.JTextField();
        lUnidadCert = new javax.swing.JLabel();
        ObtenerDatos = new javax.swing.JButton();
        tNumSer = new javax.swing.JTextField();
        lCedula = new javax.swing.JLabel();
        lNumSerial = new javax.swing.JLabel();
        lNombre = new javax.swing.JLabel();
        tUnidadCert = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(null);

        logoAgesic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agesic1.jpg"))); // NOI18N
        jPanel1.add(logoAgesic);
        logoAgesic.setBounds(10, 350, 140, 32);
        jPanel1.add(img2);
        img2.setBounds(540, 0, 240, 320);

        tFechaVenc.setFont(new java.awt.Font("Gotham", 0, 11)); // NOI18N
        jPanel1.add(tFechaVenc);
        tFechaVenc.setBounds(260, 240, 250, 30);

        tFechaEm.setFont(new java.awt.Font("Gotham", 0, 11)); // NOI18N
        jPanel1.add(tFechaEm);
        tFechaEm.setBounds(10, 240, 230, 30);

        lFechaEm.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        lFechaEm.setText("Fecha de Emisión");
        jPanel1.add(lFechaEm);
        lFechaEm.setBounds(10, 220, 120, 14);

        tCedula.setFont(new java.awt.Font("Gotham", 0, 11)); // NOI18N
        jPanel1.add(tCedula);
        tCedula.setBounds(10, 120, 500, 30);

        lFechaVenc.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        lFechaVenc.setText("Fecha de Vencimiento");
        jPanel1.add(lFechaVenc);
        lFechaVenc.setBounds(260, 220, 152, 14);

        tNombre.setFont(new java.awt.Font("Gotham", 0, 11)); // NOI18N
        jPanel1.add(tNombre);
        tNombre.setBounds(10, 60, 500, 30);

        lUnidadCert.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        lUnidadCert.setText("Unidad Certificadora");
        jPanel1.add(lUnidadCert);
        lUnidadCert.setBounds(10, 160, 139, 14);

        ObtenerDatos.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        ObtenerDatos.setText("Obtener Datos");
        ObtenerDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObtenerDatosActionPerformed(evt);
            }
        });
        jPanel1.add(ObtenerDatos);
        ObtenerDatos.setBounds(590, 350, 141, 35);

        tNumSer.setFont(new java.awt.Font("Gotham", 0, 11)); // NOI18N
        jPanel1.add(tNumSer);
        tNumSer.setBounds(10, 300, 500, 30);

        lCedula.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        lCedula.setText("Cédula");
        jPanel1.add(lCedula);
        lCedula.setBounds(10, 100, 47, 14);

        lNumSerial.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        lNumSerial.setText("Número Serial");
        jPanel1.add(lNumSerial);
        lNumSerial.setBounds(10, 280, 95, 14);

        lNombre.setFont(new java.awt.Font("Gotham", 0, 14)); // NOI18N
        lNombre.setText("Nombre Completo");
        jPanel1.add(lNombre);
        lNombre.setBounds(10, 40, 125, 14);

        tUnidadCert.setFont(new java.awt.Font("Gotham", 0, 11)); // NOI18N
        jPanel1.add(tUnidadCert);
        tUnidadCert.setBounds(10, 180, 500, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/esquina_1.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(-10, -10, 850, 440);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void ObtenerDatosActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String[] r = sc.leer();
        String[] n = r[4].split(",");
        tNombre.setText(n[0].substring(3));
        tCedula.setText(n[1].substring(17));
        tFechaEm.setText(r[2]);
        tFechaVenc.setText(r[3]);
        tUnidadCert.setText(r[1]);
        tNumSer.setText(r[0]);
        String result2 = r[5].substring(10);
 
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream("src/a.jpg"));
            os.write(Utils.hexStringToByteArray(result2));
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }/*
        ImageIcon newIcon = new ImageIcon (getClass().getResource("src/a.jpg"));
        newIcon.getImage().flush();*/
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/a.jpg"));
        } catch (IOException e) {System.out.println("No se encontro la imagen");}
        img2.setIcon(new ImageIcon( img ));
        
    }                                            

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton ObtenerDatos;
    private javax.swing.JLabel img2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lCedula;
    private javax.swing.JLabel lFechaEm;
    private javax.swing.JLabel lFechaVenc;
    private javax.swing.JLabel lNombre;
    private javax.swing.JLabel lNumSerial;
    private javax.swing.JLabel lUnidadCert;
    private javax.swing.JLabel logoAgesic;
    private javax.swing.JTextField tCedula;
    private javax.swing.JTextField tFechaEm;
    private javax.swing.JTextField tFechaVenc;
    private javax.swing.JTextField tNombre;
    private javax.swing.JTextField tNumSer;
    private javax.swing.JTextField tUnidadCert;
    // End of variables declaration                   
}
