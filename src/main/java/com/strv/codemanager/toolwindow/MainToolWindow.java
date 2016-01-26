package com.strv.codemanager.toolwindow;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.strv.codemanager.entity.ClassEntity;
import com.strv.codemanager.utility.ProjectManager;
import com.strv.codemanager.utility.UIManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public class MainToolWindow extends JFrame implements ToolWindowFactory, FileEditorManagerListener {
    private ToolWindow mToolWindow;
    private Project mProject;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        mToolWindow = toolWindow;
        mProject = project;
        mProject.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this);
        editorSelectionChanged(mProject, mToolWindow);
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {
        editorSelectionChanged(mProject, mToolWindow);
    }


    private void editorSelectionChanged(Project project, ToolWindow toolWindow) {

    }
}
