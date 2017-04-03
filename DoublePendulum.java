

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class DoublePendulum extends JPanel implements Runnable, ActionListener {


 private JFrame window;
 
 private JPopupMenu menu;
 
 private JButton m1I, m1D, m2I, m2D;
 
 private Timer timer;
 
 private Boolean c = true;
 
 private double t, dt, g, r10, r20, m1, m2, k1, k2;
 
 private double[] X;

 /**
  * creates thread for gui
  */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new DoublePendulum());
    }
    
 @Override
 public void run() {
  createAndShowGUI();
        reset();
        play();
 }
 
 private void createAndShowGUI() {
  
  menu = new JPopupMenu();

  JMenuItem mi;
  
  mi = new JMenuItem("Step");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);

  mi = new JMenuItem("Pause");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);

  mi = new JMenuItem("Clear screen");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Reset");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Menu"); 
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Quit");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Mass1 Increase (I)");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Mass1 Decrease (D)");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Mass2 Increase (U)");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  mi = new JMenuItem("Mass2 Decrease (L)");
  mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
  mi.addActionListener(this);
  menu.add(mi);
  
  window = new JFrame("Pendulum");
  window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  window.add(menu);
  window.getContentPane().add(this);
  window.pack();
  window.setLocationRelativeTo(null);
  window.setVisible(true);

        timer = new Timer(10, this);
 }
    
    @Override
    public Dimension getPreferredSize() {
     return new Dimension(900, 700);
    }
    
 @Override
 public void actionPerformed(ActionEvent e) {
  if(e.getSource() == timer) {
   step();
  } else if("Reset".equals(e.getActionCommand())) {
   reset();
  } else if("Step".equals(e.getActionCommand())) {
   step();
  } else if("Menu".equals(e.getActionCommand())) {
   menu();
  } else if("Quit".equals(e.getActionCommand())) {
   quit();
  } else if("Pause".equals(e.getActionCommand())) {
   ((JMenuItem)e.getSource()).setText("Play");
   pause();
  } else if("Play".equals(e.getActionCommand())) {
   play();
   ((JMenuItem)e.getSource()).setText("Pause");
  } else if("Mass1 Increase (I)".equals(e.getActionCommand())){
   m1+=2;
   repaint();
  } else if("Mass1 Decrease (D)".equals(e.getActionCommand())){
   if (m1 >= 4)
    m1-=2;
   repaint();
  } else if("Mass2 Increase (U)".equals(e.getActionCommand())){
   m2+=2;
   repaint();
  } else if("Mass2 Decrease (L)".equals(e.getActionCommand())){
   if (m2 >= 4)
    m2-=2;
   repaint();
  }else if ("Clear Screen".equals(e.getActionCommand())){
   clear();
  }
 }
 
 private void clear() {
  // TODO Auto-generated method stub
  
 }

 private void play() {
  timer.start();
 }

 private void pause() {
  timer.stop();
 }

 private void menu() {
  if (c){
   m1I = new JButton();
   m1I.setText("Mass1 Increase (I)");
   m1I.addActionListener(this);
   add(m1I);
   
   m1D = new JButton();
   m1D.setText("Mass1 Decrease (D)");
   m1D.addActionListener(this);
   add(m1D);
   
   m2I = new JButton();
   m2I.setText("Mass2 Increase (U)");
   m2I.addActionListener(this);
   add(m2I);
   
   m2D = new JButton();
   m2D.setText("Mass2 Decrease (L)");
   m2D.addActionListener(this);
   add(m2D);
   
   m2D = new JButton();
   m2D.setText("Reset");
   m2D.addActionListener(this);
   add(m2D);
   
   m2D = new JButton();
   m2D.setText("Quit");
   m2D.addActionListener(this);
   add(m2D);   
  }
  c = false;
  repaint();
 }

 private void reset() {
  X = new double[12];
        X[0] = 0;   //t
        X[1] = 70.0;  //r1
        X[2] = 70.0;  //r2
        X[3] = Math.PI/6; //theta1
        X[4] = Math.PI/2; //theta2
        X[5] = 0.0;   //r1'
        X[6] = 0.0;   //r2'
        X[7] = 0.0;   //theta1'
        X[8] = 0.0;   //theta2'
        //X[9] = 0.0;  //theta2''
        //X[10] = 0.0;  //r1''
        //X[11] = 0.0;  //r2''
        
        r10 = X[1];
        r20 = X[2];
        m1 = 6.0;
        m2 = 6.0;
        k1 = 2.0;
        k2 = 2.0;
        
  g = 9.8;
        t = 0.0;
        dt = .01;
        repaint();
 }

 private void step() {
  RK4SYS();
  repaint();
 }
 private void RK4SYS() {
  double[] Y, F1, F2, F3, F4;
  double dt2 = 0.5*dt;
  double start = t;
  for(int i=0; i<6; i++) {
   F1 = XPSYS(X);
   
   Y = new double[X.length];
   for(int j=0; j<X.length; j++) {
    Y[j] = X[j] + dt2*F1[j];
   }
   F2 = XPSYS(Y);
   
   Y = new double[X.length];
   for(int j=0; j<X.length; j++) {
    Y[j] = X[j] + dt2*F2[j];
   }
   F3 = XPSYS(Y); 
   
   Y = new double[X.length];
   for(int j=0; j<X.length; j++) {
    Y[j] = X[j] + dt2*F3[j];
   }
   F4 = XPSYS(Y); 
   
   for(int j=0; j<X.length; j++) {
    X[j] = X[j] + dt*(F1[j] + 2.0*(F2[j] + F3[j]) + F4[j])/6.0;
   }
   t = start + i*dt;
  }
 }


 private double[] XPSYS(double[] x) {
  double[] F = new double[x.length];
  
  F[0] = 2;  //r1
  F[1] = x[5]; //r1
  F[2] = x[6]; //alpha
  F[3] = x[7]; //beta
  F[4] = x[8];
  //r1'' -> Maxima
  F[5] = (Math.sin(x[3])*Math.sin(x[4])*(x[2]*k2-k2*r10)+Math.cos(x[3])*Math.cos(x[4])*(x[2]*k2-k2*r10)+k1*r10+Math.cos(x[3])*g*m1+x[1]*Math.pow(x[7],2)*m1-x[1]*k1)/m1;

  //r2'' -> Maxima
  F[6] = (Math.sin(x[3])*Math.sin(x[4])*(x[1]*k1*m2-k1*m2*r10)+Math.cos(x[3])*Math.cos(x[4])*(x[1]*k1*m2-k1*m2*r10)+(k2*m2+k2*m1)*r10+x[2]*(-k2*m2-k2*m1)+x[2]*Math.pow(x[8],2)*m1*m2)/(m1*m2);
  
  //theta1'' -> Maxima
  F[7] = (Math.sin(x[3])*Math.cos(x[4])*(k2*r10-x[2]*k2)+Math.cos(x[3])*Math.sin(x[4])*(x[2]*k2-k2*r10)-Math.sin(x[3])*g*m1-2*x[5]*x[7]*m1)/(x[1]*m1);
  
  //theta2'' -> Maxima
  F[8] = -(Math.sin(x[3])*Math.cos(x[4])*(k1*r10-x[1]*k1)+Math.cos(x[3])*Math.sin(x[4])*(x[1]*k1-k1*r10)+2*x[6]*x[8]*m1)/(x[2]*m1);
  
  return F;
 }

 private void quit() {
  System.exit(0);
 }
 
 @Override
 public void paintComponent(Graphics g) {
  super.paintComponent(g);
  
  double r1 = X[1];
  double r2 = X[2];
  double theta1 = X[3];
  double theta2 = X[4];
  
  int hingeX = getWidth()/2;
  int hingeY = getHeight()/4;
  int hingeR = 10;
  int pendX1 = (int)(hingeX + r1*Math.sin(theta1));
  int pendY1 = (int)(hingeY + r1*Math.cos(theta1));
  int pendX2 = (int)(pendX1 + r2*Math.sin(theta2));
  int pendY2 = (int)(pendY1 + r2*Math.cos(theta2));
  int pendR1 = (int)Math.sqrt(m1*100);
  int pendR2 = (int)Math.sqrt(m2*100);
  
  g.setColor(getForeground());
  g.drawLine(hingeX, hingeY, pendX1, pendY1);
  g.drawLine(pendX1, pendY1, pendX2, pendY2);
  g.fillRect(hingeX-hingeR/2, hingeY-hingeR/2, hingeR, hingeR);
  g.setColor(Color.GREEN);
  g.fillOval(pendX1-pendR1/2, pendY1-pendR1/2, pendR1, pendR1);
  g.setColor(Color.BLUE);
  g.fillOval(pendX2-pendR2/2, pendY2-pendR2/2, pendR2, pendR2);
  g.setColor(getForeground());
  String s = " Mass 1: " + m1;
  g.drawString(s, 10, hingeY);
  s = " Mass 2: " + m2;
  g.drawString(s, 10, hingeY+12);
 }

 @Override
 public void processMouseEvent(MouseEvent e) {
  super.processMouseEvent(e);
  if (e.isPopupTrigger()) { 
      menu.show(window, e.getX(), e.getY());
  }
 }
}