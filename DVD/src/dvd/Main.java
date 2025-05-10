package dvd;

import javax.swing.SwingUtilities;

public class Main
{
	public static void main(String[] args)
	{
	    SwingUtilities.invokeLater(() -> new DVDAnimator("i-hate-you.png"));
	}
}
