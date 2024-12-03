import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// 定义NotepadApp类，继承自JFrame，用于创建记事本应用程序的图形界面并实现相关功能
public class NotepadApp extends JFrame {

    // 用于文本编辑的JTextPane组件
    private JTextPane textPane;
    // 文件选择器，用于打开和保存文件时选择文件路径
    private JFileChooser fileChooser;
    // 当前正在操作的文件对象，初始化为null
    private File currentFile;

    // 构造函数，用于初始化记事本应用程序的窗口及相关组件
    public NotepadApp() {
        // 设置窗口标题为“记事本”
        setTitle("记事本");
        // 设置窗口大小为宽800像素，高600像素
        setSize(800, 600);
        // 设置窗口关闭时的操作，这里是退出整个应用程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口在屏幕中的显示位置，使其居中显示
        setLocationRelativeTo(null);

        // 调用初始化组件的方法
        initComponents();

        // 设置窗口可见，使其显示在屏幕上
        setVisible(true);
    }

    // 初始化组件的方法，用于创建和设置菜单栏、文本编辑区、文件选择器等各个组件
    private void initComponents() {
        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();

        // 文件菜单
        JMenu fileMenu = new JMenu("文件");
        // 设置文件菜单的快捷键（助记符）为Alt + F
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // 新建文件菜单项
        JMenuItem newMenuItem = new JMenuItem("新建");
        // 设置新建文件菜单项的快捷键（助记符）为Alt + N
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用新建文件的方法
                newFile();
            }
        });
        fileMenu.add(newMenuItem);

        // 打开文件菜单项
        JMenuItem openMenuItem = new JMenuItem("打开");
        // 设置打开文件菜单项的快捷键（助记符）为Alt + O
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用打开文件的方法
                openFile();
            }
        });
        fileMenu.add(openMenuItem);

        // 保存文件菜单项
        JMenuItem saveMenuItem = new JMenuItem("保存");
        // 设置保存文件菜单项的快捷键（助记符）为Alt + S
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用保存文件的方法
                saveFile();
            }
        });
        fileMenu.add(saveMenuItem);

        // 另存为文件菜单项
        JMenuItem saveAsMenuItem = new JMenuItem("另存为");
        // 设置另存为文件菜单项的快捷键（助记符）为Alt + A
        saveAsMenuItem.setMnemonic(KeyEvent.VK_A);
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用另存为文件的方法
                saveAsFile();
            }
        });
        fileMenu.add(saveAsMenuItem);

        // 退出菜单项
        JMenuItem exitMenuItem = new JMenuItem("退出");
        // 设置退出菜单项的快捷键（助记符）为Alt + X
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 退出应用程序
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        // 编辑菜单
        JMenu editMenu = new JMenu("编辑");
        // 设置编辑菜单的快捷键（助记符）为Alt + E
        editMenu.setMnemonic(KeyEvent.VK_E);

        // 剪切菜单项
        JMenuItem cutMenuItem = new JMenuItem("剪切");
        // 设置剪切菜单项的快捷键（助记符）为Alt + T
        cutMenuItem.setMnemonic(KeyEvent.VK_T);
        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用JTextPane的cut方法实现文本剪切功能
                textPane.cut();
            }
        });
        editMenu.add(cutMenuItem);

        // 复制菜单项
        JMenuItem copyMenuItem = new JMenuItem("复制");
        // 设置复制菜单项的快捷键（助记符）为Alt + C
        copyMenuItem.setMnemonic(KeyEvent.VK_C);
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用JTextPane的copy方法实现文本复制功能
                textPane.copy();
            }
        });
        editMenu.add(copyMenuItem);

        // 粘贴菜单项
        JMenuItem pasteMenuItem = new JMenuItem("粘贴");
        // 设置粘贴菜单项的快捷键（助记符）为Alt + P
        pasteMenuItem.setMnemonic(KeyEvent.VK_P);
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用JTextPane的paste方法实现文本粘贴功能
                textPane.paste();
            }
        });
        editMenu.add(pasteMenuItem);

        // 格式菜单（用于设置字体颜色和大小，实现部分扩展要求）
        JMenu formatMenu = new JMenu("格式");
        // 设置格式菜单的快捷键（助记符）为Alt + F
        formatMenu.setMnemonic(KeyEvent.VK_F);

        // 设置字体颜色菜单项
        JMenuItem fontColorMenuItem = new JMenuItem("字体颜色");
        fontColorMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用设置字体颜色的方法
                setFontColor();
            }
        });
        formatMenu.add(fontColorMenuItem);

        // 设置字体大小菜单项
        JMenuItem fontSizeMenuItem = new JMenuItem("字体大小");
        fontSizeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用设置字体大小的方法
                setFontSize();
            }
        });
        formatMenu.add(fontSizeMenuItem);

        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        // 设置帮助菜单的快捷键（助记符）为Alt + H
        helpMenu.setMnemonic(KeyEvent.VK_H);

        // 关于菜单项
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 调用显示关于信息的方法
                showAbout();
            }
        });
        helpMenu.add(aboutMenuItem);

        // 将各个菜单添加到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        // 设置窗口的菜单栏为创建好的菜单栏
        setJMenuBar(menuBar);

        // 创建文本编辑区
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 创建文件选择器
        fileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("文本文件(*.txt)", "txt");
        fileChooser.setFileFilter(filter);
    }

    // 新建文件的方法，用于清空文本编辑区内容并重置当前文件对象为null
    private void newFile() {
        textPane.setText("");
        currentFile = null;
    }

    // 打开文件的方法，用于从用户选择的文件中读取内容并显示在文本编辑区
    private void openFile() {
        // 显示打开文件对话框，并获取用户操作的返回值
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件对象
            currentFile = fileChooser.getSelectedFile();
            try {
                // 创建文件读取流，用于读取文件内容
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                StringBuilder content = new StringBuilder();
                String line;
                // 逐行读取文件内容，并添加到StringBuilder中
                while ((line = reader.readLine())!= null) {
                    content.append(line).append("\n");
                }
                reader.close();
                // 将读取到的文件内容设置为文本编辑区的文本内容
                textPane.setText(content.toString());
            } catch (IOException e) {
                // 如果读取文件过程中出现错误，弹出错误消息框提示用户
                JOptionPane.showMessageDialog(this, "打开文件时出错: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 保存文件的方法，根据当前文件对象是否存在决定是直接保存还是调用另存为方法
    private void saveFile() {
        if (currentFile == null) {
            saveAsFile();
        } else {
            try {
                // 创建文件写入流，用于将文本编辑区的内容写入文件
                FileWriter writer = new FileWriter(currentFile);
                writer.write(textPane.getText());
                writer.close();
            } catch (IOException e) {
                // 如果写入文件过程中出现错误，弹出错误消息框提示用户
                JOptionPane.showMessageDialog(this, "保存文件时出错: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 另存为文件的方法，用于将文本编辑区的内容保存到用户指定的新文件中
    private void saveAsFile() {
        // 显示另存为文件对话框，并获取用户操作的返回值
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件对象
            currentFile = fileChooser.getSelectedFile();
            if (!currentFile.getAbsolutePath().endsWith(".txt")) {
                // 如果用户选择的文件扩展名不是.txt，自动添加.txt扩展名
                currentFile = new File(currentFile.getAbsolutePath() + ".txt");
            }
            try {
                // 创建文件写入流，用于将文本编辑区的内容写入文件
                FileWriter writer = new FileWriter(currentFile);
                writer.write(textPane.getText());
                writer.close();
            } catch (IOException e) {
                // 如果写入文件过程中出现错误，弹出错误消息框提示用户
                JOptionPane.showMessageDialog(this, "保存文件时出错: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 设置字体颜色的方法，通过弹出颜色选择对话框让用户选择字体颜色，并设置到文本编辑区
    private void setFontColor() {
        // 弹出颜色选择对话框，让用户选择颜色，初始颜色为黑色
        Color color = JColorChooser.showDialog(this, "选择字体颜色", Color.BLACK);
        if (color!= null) {
            // 将选择的颜色设置为文本编辑区的前景色（即字体颜色）
            textPane.setForeground(color);
        }
    }

    // 设置字体大小的方法，通过弹出输入对话框让用户输入字体大小，并设置到文本编辑区
    private void setFontSize() {
        // 弹出输入对话框，让用户输入字体大小
        String sizeStr = JOptionPane.showInputDialog(this, "请输入字体大小", "设置字体大小", JOptionPane.QUESTION_MESSAGE);
        if (sizeStr!= null) {
            try {
                // 将用户输入的字符串转换为整数
                int size = Integer.parseInt(sizeStr);
                Font font = textPane.getFont();
                // 根据当前字体和用户输入的大小，设置新的字体大小
                textPane.setFont(font.deriveFont((float) size));
            } catch (NumberFormatException e) {
                // 如果用户输入的内容无法转换为整数，弹出错误消息框提示用户
                JOptionPane.showMessageDialog(this, "输入的字体大小无效", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 显示关于信息的方法，通过弹出消息框显示关于应用程序的简单信息
    private void showAbout() {
        JOptionPane.showMessageDialog(this, "这是一个简单的记事本应用程序", "关于", JOptionPane.INFORMATION_MESSAGE);
    }

    // 主方法，用于启动记事本应用程序
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NotepadApp();
            }
        });
    }
}