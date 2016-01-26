package com.strv.codemanager.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.strv.codemanager.utility.UIManager;


public class HighlightAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Editor editor = anActionEvent.getData(LangDataKeys.EDITOR);
        Project project = anActionEvent.getProject();
        if (editor == null || project == null)
            return;

        if (editor.getMarkupModel().getAllHighlighters().length > 0)
            UIManager.clearAllHighlights(editor);
        else
            UIManager.highlightAllMethods(editor, project);
    }
}
