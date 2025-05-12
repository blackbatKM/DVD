package dvd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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

    public DVDAnimator(String imagePath) {
        try {
            // Load the image.  Use a default if the file isn't found.
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                image = ImageIO.read(imageFile);
            } else {
                image = ImageIO.read(new File("H:\\git\\DVD\\i-hate-you.png")); // Default image
            }

            image = resizeImage(image, imgW, imgH);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Exit if image loading fails
        }

        setBackground(new Color(0, 0, 0, 0)); // Make the background fully transparent
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
        panel.setOpaque(false); // Ensure the panel is transparent

        // Add mouse listener to the panel to close the application on click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0); // Close the application
            }
        });

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

