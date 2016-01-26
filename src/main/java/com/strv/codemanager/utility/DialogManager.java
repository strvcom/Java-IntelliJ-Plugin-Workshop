package com.strv.codemanager.utility;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;

public class DialogManager {

    public static String showInputEntityNameDialog(Project project) {
        return Messages.showInputDialog(project, "Please fill entity name", "Input Entity Name", Messages.getQuestionIcon());
    }

    public static void showMessageDialog(Project project, String text) {
        Messages.showMessageDialog(project, text, "Information", Messages.getInformationIcon());
    }

    public static File pickDirectory(@NotNull Project project) {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        if (project.getBasePath() != null)
            fc.setCurrentDirectory(new File(project.getBasePath()));
        final int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        else
            return null;
    }
}
