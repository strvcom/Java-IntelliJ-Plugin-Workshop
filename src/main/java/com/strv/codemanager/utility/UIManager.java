package com.strv.codemanager.utility;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.*;
import com.intellij.ui.JBColor;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


public class UIManager {
    public static JPanel initUI(ToolWindow toolWindow) {
        // create UI
        final JPanel myToolWindowContent = new JPanel();
        myToolWindowContent.setLayout(new BoxLayout(myToolWindowContent, BoxLayout.Y_AXIS));
        final Content content = ContentFactory.SERVICE.getInstance().createContent(myToolWindowContent, "", false);
        toolWindow.getContentManager().removeAllContents(true);
        toolWindow.getContentManager().addContent(content);

        return myToolWindowContent;
    }


    public static void addClassLabel(JPanel panel, String name) {
        final JLabel classNameText = new JLabel("<html><center>Class: " + name + "</center></html>");
        classNameText.setFont(new Font("Ariel", Font.BOLD, DimensionManager.fontSize(18)));
        classNameText.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(classNameText);
    }


    public static void addMethodLabel(JPanel panel, String name) {
        final JLabel methodText = new JLabel("<html><center>" + name + "()</center></html>");
        methodText.setFont(new Font("Ariel", Font.ITALIC, DimensionManager.fontSize(14)));
        methodText.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(methodText);
    }


    public static void clearAllHighlights(@NotNull Editor editor) {
        editor.getMarkupModel().removeAllHighlighters();
    }

    public static void highlightAllMethods(@NotNull Editor editor, @NotNull Project project) {
        Document doc = editor.getDocument();

        // get PsiFile
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(doc);
        if (psiFile instanceof PsiJavaFile) {
            final TextAttributes attr = new TextAttributes();
            attr.setBackgroundColor(JBColor.LIGHT_GRAY);

            PsiClass[] psiClasses = ((PsiJavaFile) psiFile).getClasses();
            for (PsiClass psiClas : psiClasses) {
                for (PsiMethod method : psiClas.getMethods()) {
                    int line = getMethodStartLineNumber(method, editor.getDocument());
                    editor.getMarkupModel().addLineHighlighter(line, HighlighterLayer.WARNING, attr);
                }
            }
        }
    }

    private static int getMethodStartLineNumber(@NotNull PsiMethod method, @NotNull Document document) {
        if (method.getNameIdentifier() == null)
            return -1;

        int offset = method.getNameIdentifier().getTextOffset();
        return document.getLineNumber(offset);
    }
}
