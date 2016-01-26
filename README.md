Code Manager - STRV Developer Meetup Workshop
============
This plugin was created as an example for STRV Android meetup 2016. It shows how to create kind of basic plugin for IntelliJ based IDE. 
Introducing three most used ways of user interaction, as menu item, toolbar button and toolWindow.


Prerequisites
=============
You have to have installed IntelliJ IDEA IDE version 9.0 or later, and Java JDK, preferably version 7 or later. That should be all you need to start developing your own plugin, and running this example.

You can download IntelliJ IDEA Community edition here: [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)


Functionality
=============
1. Menu item for creating new example entity class.  
   Clicking on the "Create Entity" menu item results in displaying two dialogs. First one for selecting location, and the second one for specifying entity class name.  
   New entity class with some example content will be created, and user will be notified about success of this operation by message dialog afterwards.
   
2. Toolbar button for highlighting methods declaration lines in code.  
   Methods declaration lines in currently opened java code get highlighted upon clicking the small red "S" in the toolbar. Hitting this button again will clear the highlights in currently opened file.

3. ToolWindow displaying all classes and method names in currently opened file.  
   Upon clicking on the "Method List" toolWindow at the right side of the editor, new tool window will be opened.  
   This toolWindow will contain labels containing class names and method names in currently opened file. Each time the selected file in the editor is changed, the content of the toolWindow is also changed.


Implementation
==============
You can find prepared utility classes in com.strv.codemanager.utility package. These classes are providing methods for quicker and also easier implementation. Implementation of each action is described in detail.


Creating entity class
---------------------
**File to edit:** CreateEntityAction.java  
**Utility classes and methods used:**

* ProjectManager
    * createSampleEntity(Project project, File directory, String entityName)
* DialogManager
    * pickDirectory(Project project)
    * showInputEntityNameDialog(Project project)
    * showMessageDialog(Project project)

We are going to edit **actionPerformed** method.<br>
First we need to obtain Project instance from action event:<br>
```
Project project = anActionEvent.getProject();
if (project == null)
    return;
```

We are going to need directory path for storing generated file:<br>
```
File file = DialogManager.pickDirectory(project);
if (file == null)
    return;
```

And the name of the generated file:<br>
```
String fileName = DialogManager.showInputEntityNameDialog(project);
if (fileName == null)
    return;
```

With these information we can just call **createSampleEntity** method, and based on return value let user know if the creation was successful:<br>
```
if (ProjectManager.createSampleEntity(project, file, fileName))
    DialogManager.showMessageDialog(project, "File created successfully!");
else
    DialogManager.showMessageDialog(project, "File creation failed!");
```


Text highlighting
-----------------
**File to edit:** HighlightAction.java  
**Utility classes and methods used:**

* UIManager
    * clearAllHighlights(Editor editor)
    * highlightAllMethods(Editor editor, Project project)

We are going to edit **actionPerformed** method.<br>
This time, we do not only need project, but we need editor instance also. Lets get them from action event again:<br>
```
Editor editor = anActionEvent.getData(LangDataKeys.EDITOR);
Project project = anActionEvent.getProject();
if (editor == null || project == null)
    return;
```

We will clear or highlight all methods declaration starting lines based on the count of highlighters in current file:<br>
```
if (editor.getMarkupModel().getAllHighlighters().length > 0)
    UIManager.clearAllHighlights(editor);
else
    UIManager.highlightAllMethods(editor, project);
```


Methods list
------------
**File to edit:** MainToolWindow.java  
**Utility classes and methods used:**

* ProjectManager
    * getClassEntityList(Project project)
* UIManager
    * initUI(ToolWindow toolWindow)
    * addClassLabel(JPanel panel, String text)
    * addMethodLabel(JPanel panel, String text)


We are going to edit **editorSelectionChanged** method.<br>
This time we going to need some kind of panel for storing UI elements. Lets create one and initialize it using prepared **initUI** method:<br>
```
JPanel panel = UIManager.initUI(toolWindow);
```

To be able to display list of classes and methods, we first need to obtain it. So lets create list of **ClassEntity**, which contains class name and method list. We can than fill this list using prepared **getClassEntityList** method.<br>
```
List<ClassEntity> classList = ProjectManager.getClassEntityList(project);
```

All wee need to do now is to iterate through the **ClassEntity** list and print the class label using **addClassLabel** method, and then go through all methods inside the ClassEntity, We can print them using **addMethodLabel** method.<br>
```
for (ClassEntity entity : classList) {
    UIManager.addClassLabel(panel, entity.getName());

    for (String method : entity.getMethodList()) {
        UIManager.addMethodLabel(panel, method);
    }
}
```


Building project
================
You don't need to install Gradle on your system, because there is a [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html). The wrapper is a batch script on Windows, and a shell script for other operating systems. When you start a Gradle build via the wrapper, Gradle will be automatically downloaded and used to run the build.

1. Clone this repository
2. Run `gradlew buildPlugin` in console
3. ZIP file should be available in project root directory

You can also test plugin implementation by launching new instance of IntelliJ IDEA with plugin installed, using `gradlew runPlugin`.


Developed by
============
* Lukáš Hermann ([lukas.hermann@strv.com](lukas.hermann@strv.com))
* STRV ([http://www.strv.com](http://www.strv.com))