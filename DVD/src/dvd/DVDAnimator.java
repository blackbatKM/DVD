package dvd;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class DVDAnimator extends JWindow {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
    private int x = 300, y = 300;
    private int dx = 4, dy = 4;
    private int imgW = 120, imgH = 120;
    private Timer timer;

    @SuppressWarnings("unused")
	public DVDAnimator(String imagePath) {
        try {
            BufferedImage original = ImageIO.read(new File("i-hate-you.png"));
            image = resizeImage(original, imgW, imgH);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        setBackground(new Color(0, 0, 0, 0)); // voll transparent
        setAlwaysOnTop(true);
        setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        JPanel panel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, x, y, null);
                }
            }
        };
        panel.setOpaque(false);
        setContentPane(panel);

        timer = new Timer(30, e -> animate());
        timer.start();
        setVisible(true);
    }

    private void animate() {
        int screenW = getWidth();
        int screenH = getHeight();

        if (x + imgW >= screenW || x <= 0) dx = -dx;
        if (y + imgH >= screenH || y <= 0) dy = -dy;

        x += dx;
        y += dy;

        repaint();
    }

    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        Image tmp = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();
        g2.drawImage(tmp, 0, 0, null);
        g2.dispose();
        return resized;
    }

}
