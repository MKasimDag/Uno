package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import gui.LoginOverlay;

/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own
without any help from anyone else. The effort in the project thus belongs
completely to me. I did not search for a solution, or I did not consult any
program written by others or did not copy any program from other sources. I
read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: Muhammed Kasım DAĞ, 83679
*************************************************************************/

public class Main {

    public static void main(String[] args) {
        // Create UI on the EDT and apply a modern Look & Feel
        SwingUtilities.invokeLater(() -> {
            try {
                boolean nimbusSet = false;
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        nimbusSet = true;
                        break;
                    }
                }
                if (!nimbusSet) {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
            } catch (Exception ignore) { }

            new LoginOverlay();
        });
    }
}
