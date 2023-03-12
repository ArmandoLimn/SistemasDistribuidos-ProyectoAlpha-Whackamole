package proyectoalpha;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public abstract class GUI extends javax.swing.JFrame {

    protected static javax.swing.JLabel[][] matrix;
    private int moleRow = -1; 
    private int moleColumn = -1;
    
    public GUI() {
        initComponents();
        initMatrix();
    }
    
    // putMole draw inside the form a new mole.
    protected void putMole(int row, int column)
    {
        if (moleRow != -1 && moleColumn != -1)
        {
            matrix[moleRow][moleColumn].setToolTipText("burrow");
            matrix[moleRow][moleColumn].setIcon((new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))));
        }

        matrix[row][column].setToolTipText("mole");
        matrix[row][column].setIcon((new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/mole.png"))));

        moleRow = row;
        moleColumn = column;
    }

    // refresh function update the windows when the mole changes his position.
    // We have to update each label of the matrix.
    protected void refresh()
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j].revalidate();
                matrix[i][j].repaint();
            }
        }
    }
    
    protected abstract void onMonsterClick();

    private void initMatrix() {
        matrix = new javax.swing.JLabel[][]{
            { m00, m01, m02, m03 },
            { m10, m11, m12, m13 },
            { m20, m21, m22, m23 },
            { m30, m31, m32, m33 }
        };

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent me) {
                        JLabel clicked = (JLabel) me.getSource();
                        if (clicked.getToolTipText().equals("mole")) {
                            clicked.setIcon((new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hammer.jpg"))));
                            onMonsterClick();
                        }
                    }
                });
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m00 = new javax.swing.JLabel();
        m01 = new javax.swing.JLabel();
        m03 = new javax.swing.JLabel();
        m02 = new javax.swing.JLabel();
        m13 = new javax.swing.JLabel();
        m11 = new javax.swing.JLabel();
        m10 = new javax.swing.JLabel();
        m12 = new javax.swing.JLabel();
        m31 = new javax.swing.JLabel();
        m23 = new javax.swing.JLabel();
        m21 = new javax.swing.JLabel();
        m20 = new javax.swing.JLabel();
        m33 = new javax.swing.JLabel();
        m22 = new javax.swing.JLabel();
        m30 = new javax.swing.JLabel();
        m32 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        m00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m00.setToolTipText("burrow");
        m00.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        m01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m01.setToolTipText("burrow");

        m03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m03.setToolTipText("burrow");

        m02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m02.setToolTipText("burrow");

        m13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m13.setToolTipText("burrow");

        m11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m11.setToolTipText("burrow");

        m10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m10.setToolTipText("burrow");

        m12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m12.setToolTipText("burrow");

        m31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m31.setToolTipText("burrow");

        m23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m23.setToolTipText("burrow");

        m21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m21.setToolTipText("burrow");

        m20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m20.setToolTipText("burrow");

        m33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m33.setToolTipText("burrow");

        m22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m22.setToolTipText("burrow");

        m30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m30.setToolTipText("burrow");

        m32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectoalpha/hole.jpg"))); // NOI18N
        m32.setToolTipText("burrow");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m30)
                                .addGap(18, 18, 18)
                                .addComponent(m31)
                                .addGap(18, 18, 18)
                                .addComponent(m32)
                                .addGap(18, 18, 18)
                                .addComponent(m33))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m20)
                                .addGap(18, 18, 18)
                                .addComponent(m21)
                                .addGap(18, 18, 18)
                                .addComponent(m22)
                                .addGap(18, 18, 18)
                                .addComponent(m23)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m10)
                                .addGap(18, 18, 18)
                                .addComponent(m11)
                                .addGap(18, 18, 18)
                                .addComponent(m12)
                                .addGap(18, 18, 18)
                                .addComponent(m13))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m00)
                                .addGap(18, 18, 18)
                                .addComponent(m01)
                                .addGap(18, 18, 18)
                                .addComponent(m02)
                                .addGap(18, 18, 18)
                                .addComponent(m03)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(m03)
                    .addComponent(m01)
                    .addComponent(m00)
                    .addComponent(m02))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(m13)
                    .addComponent(m11)
                    .addComponent(m10)
                    .addComponent(m12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(m23)
                    .addComponent(m21)
                    .addComponent(m20)
                    .addComponent(m22))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(m33)
                    .addComponent(m31)
                    .addComponent(m30)
                    .addComponent(m32))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected static javax.swing.JLabel m00;
    protected static javax.swing.JLabel m01;
    protected static javax.swing.JLabel m02;
    protected static javax.swing.JLabel m03;
    protected static javax.swing.JLabel m10;
    protected static javax.swing.JLabel m11;
    protected static javax.swing.JLabel m12;
    protected static javax.swing.JLabel m13;
    protected static javax.swing.JLabel m20;
    protected static javax.swing.JLabel m21;
    protected static javax.swing.JLabel m22;
    protected static javax.swing.JLabel m23;
    protected static javax.swing.JLabel m30;
    protected static javax.swing.JLabel m31;
    protected static javax.swing.JLabel m32;
    protected static javax.swing.JLabel m33;
    protected static javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
