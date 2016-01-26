package com.strv.codemanager.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.strv.codemanager.utility.DialogManager;
import com.strv.codemanager.utility.ProjectManager;

import java.io.File;


public class CreateEntityAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getProject();
        if (project == null)
            return;

        File file = DialogManager.pickDirectory(project);
        if (file == null)
            return;

        String fileName = DialogManager.showInputEntityNameDialog(project);
        if (fileName == null)
            return;

        if (ProjectManager.createSampleEntity(project, file, fileName))
            DialogManager.showMessageDialog(project, "File created successfully!");
        else
            DialogManager.showMessageDialog(project, "File creation failed!");
    }
}