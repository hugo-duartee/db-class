/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author he-ds
 */
public class Menu extends javax.swing.JFrame
{
    Vista_ClientesDlg clientes;
    Vista_ProductosDlg productos;
    Vista_RegistroVentasDlg registroV;
    Vista_BusquedaVentasDlg busquedaV;

    /**
     * Creates new form Menu
     */
    public Menu()
    {
        initComponents();
        clientes = new Vista_ClientesDlg(this, true);
        productos = new Vista_ProductosDlg(this, true);
        registroV = new Vista_RegistroVentasDlg(this, true);
        busquedaV = new Vista_BusquedaVentasDlg(this, true);
        
        ventasBtn.setVisible(false);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        ventasBtn = new javax.swing.JButton();
        clientesBtn = new javax.swing.JButton();
        productosBtn = new javax.swing.JButton();
        registroventasBtn = new javax.swing.JButton();
        busquedaVentasBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ventasBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ventasBtn.setText("Ventas");
        ventasBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ventasBtnActionPerformed(evt);
            }
        });

        clientesBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        clientesBtn.setText("Clientes");
        clientesBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                clientesBtnActionPerformed(evt);
            }
        });

        productosBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        productosBtn.setText("Productos");
        productosBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                productosBtnActionPerformed(evt);
            }
        });

        registroventasBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        registroventasBtn.setText("Registro Ventas");
        registroventasBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                registroventasBtnActionPerformed(evt);
            }
        });

        busquedaVentasBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        busquedaVentasBtn.setText("Busqueda Ventas");
        busquedaVentasBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                busquedaVentasBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ventasBtn)
                    .addComponent(clientesBtn)
                    .addComponent(productosBtn)
                    .addComponent(busquedaVentasBtn)
                    .addComponent(registroventasBtn))
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(ventasBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientesBtn)
                .addGap(18, 18, 18)
                .addComponent(productosBtn)
                .addGap(18, 18, 18)
                .addComponent(registroventasBtn)
                .addGap(18, 18, 18)
                .addComponent(busquedaVentasBtn)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void registroventasBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_registroventasBtnActionPerformed
    {//GEN-HEADEREND:event_registroventasBtnActionPerformed
        // TODO add your handling code here:
        registroV.setVisible(true);
    }//GEN-LAST:event_registroventasBtnActionPerformed

    private void ventasBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ventasBtnActionPerformed
    {//GEN-HEADEREND:event_ventasBtnActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_ventasBtnActionPerformed

    private void clientesBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clientesBtnActionPerformed
    {//GEN-HEADEREND:event_clientesBtnActionPerformed
        // TODO add your handling code here:
        
        //this.dispose();
        //this.setVisible(false);
        clientes.setVisible(true);
        //this.setVisible(true);
        
    }//GEN-LAST:event_clientesBtnActionPerformed

    private void productosBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_productosBtnActionPerformed
    {//GEN-HEADEREND:event_productosBtnActionPerformed
        // TODO add your handling code here:
        productos.setVisible(true);
    }//GEN-LAST:event_productosBtnActionPerformed

    private void busquedaVentasBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_busquedaVentasBtnActionPerformed
    {//GEN-HEADEREND:event_busquedaVentasBtnActionPerformed
        // TODO add your handling code here:
        busquedaV.setVisible(true);
    }//GEN-LAST:event_busquedaVentasBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton busquedaVentasBtn;
    private javax.swing.JButton clientesBtn;
    private javax.swing.JButton productosBtn;
    private javax.swing.JButton registroventasBtn;
    private javax.swing.JButton ventasBtn;
    // End of variables declaration//GEN-END:variables
}
