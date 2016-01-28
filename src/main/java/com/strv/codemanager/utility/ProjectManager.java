package com.strv.codemanager.utility;


import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.strv.codemanager.entity.ClassEntity;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {

    public static List<ClassEntity> getClassEntityList(Project project) {
        List<ClassEntity> classList = new ArrayList<>();

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null)
            return classList;

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
        if (psiFile instanceof PsiJavaFile) {
            PsiClass[] psiClasses = ((PsiJavaFile) psiFile).getClasses();
            analyzeClasses(classList, psiClasses, 1);
        }
        return classList;
    }

    public static boolean createSampleEntity(final Project project, @NotNull final File directory, @NotNull final String entityName) {
        if (project == null)
            return false;

        // Get virtual file
        VirtualFile vFile = LocalFileSystem.getInstance().findFileByIoFile(directory);
        if (vFile == null)
            return false;

        // Get PsiDirectory
        final PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(vFile);
        if (psiDirectory == null)
            return false;

        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                try {
                    // Create new PsiFile
                    PsiFile psiFile = PsiFileFactory.getInstance(project).createFileFromText(entityName + ".java", JavaFileType.INSTANCE, generateFileContent(entityName));

                    // Assign created PsiFile to selected directory
                    psiDirectory.add(psiFile);
                } catch (NullPointerException ignored) {
                }
            }
        });
        return true;
    }


    private static void analyzeClasses(List<ClassEntity> classList, PsiClass[] psiClasses, int counter) {
        for (PsiClass psiClas : psiClasses) {
            ClassEntity entity = new ClassEntity(psiClas.getName());
            for (PsiMethod method : psiClas.getMethods()) {
                entity.addMethod(method.getName());
            }
            classList.add(entity);

            if (psiClas.getAllInnerClasses().length != 0 && counter > 0)
                analyzeClasses(classList, psiClas.getAllInnerClasses(), --counter);
        }
    }


    private static String generateFileContent(String entityName) {
        String content = "";

        content += "import java.util.ArrayList;\n";
        content += "import java.util.List;\n\n";

        content += "public class " + entityName + " {\n";
        content += "\tprivate String mName;\n";
        content += "\tprivate List<String> mMethodList = new ArrayList<>();\n\n";
        content += "\tpublic " + entityName + "(String name) {\n";
        content += "\t\tmName = name;\n";
        content += "\t}\n\n";
        content += "\tpublic String getName() {\n";
        content += "\t\treturn mName;\n";
        content += "\t}\n\n";
        content += "\tpublic void setName(String name) {\n";
        content += "\t\tmName = name;\n";
        content += "\t}\n\n";
        content += "\tpublic void addMethod(String methodName) {\n";
        content += "\t\tmMethodList.add(methodName);\n";
        content += "\t}\n\n";
        content += "\tpublic List<String> getMethodList() {\n";
        content += "\t\treturn mMethodList;\n";
        content += "\t}\n}";


        return content;
    }
}
