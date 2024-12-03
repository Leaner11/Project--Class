import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// 定义Calculator类，继承自JFrame，用于创建计算器的图形界面和实现计算逻辑
public class Calculator extends JFrame {

    // 用于显示输入的数字和计算结果的文本框
    private JTextField displayField;
    // 存储用户当前正在输入的数字字符串
    private String currentInput = "";
    // 记录用户选择的运算符（如 +、-、*、/ 等）
    private String operator = "";
    // 用于存储参与运算的第一个操作数，初始值设为零
    private BigDecimal operand1 = BigDecimal.ZERO;
    // 用于存储参与运算的第二个操作数，初始值设为零
    private BigDecimal operand2 = BigDecimal.ZERO;
    // 布尔变量，用于标记当前显示框中是否已经显示了计算结果
    // 以便在后续输入时进行相应处理
    private boolean isResultDisplayed = false;

    // 构造函数，用于初始化计算器的图形界面和相关设置
    public Calculator() {
        // 设置计算器窗口的标题
        setTitle("计算器");
        // 设置计算器窗口的大小
        setSize(300, 400);
        // 设置窗口关闭时的操作，这里是退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口在屏幕中的位置，使其居中显示
        setLocationRelativeTo(null);

        // 调用初始化组件的方法
        initComponents();

        // 设置窗口可见
        setVisible(true);
    }

    // 初始化组件的方法，用于创建和设置计算器的各个组件
    private void initComponents() {
        // 创建主面板，采用边界布局（BorderLayout）
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 创建用于显示输入和结果的文本框
        displayField = new JTextField();
        // 设置文本框不可编辑，只能用于显示
        displayField.setEditable(false);
        // 设置文本框中的文本水平对齐方式为右对齐
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        // 将文本框添加到主面板的北部（上方）区域
        mainPanel.add(displayField, BorderLayout.NORTH);

        // 创建数字和运算符按钮面板，采用网格布局（GridLayout），5行4列
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4));

        // 定义按钮上的标签文本数组
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sqrt", "1/x", "%", "C", "←"
        };

        // 遍历按钮标签数组，为每个标签创建一个按钮并添加点击事件监听器
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        // 将按钮面板添加到主面板的中心区域
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // 将主面板添加到计算器窗口
        add(mainPanel);
    }

    // 内部类，实现ActionListener接口，用于处理按钮的点击事件
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取触发点击事件的按钮对象
            JButton sourceButton = (JButton) e.getSource();
            // 获取按钮上的文本内容
            String buttonText = sourceButton.getText();

            // 如果按钮文本是数字（通过正则表达式匹配）
            if (buttonText.matches("\\d")) {
                // 如果已经显示了结果，先清空当前输入并重置结果显示标记
                if (isResultDisplayed) {
                    currentInput = "";
                    isResultDisplayed = false;
                }
                // 将点击的数字添加到当前输入字符串中
                currentInput += buttonText;
                // 更新显示文本框的内容
                displayField.setText(currentInput);
            } else if (buttonText.equals(".")) {
                // 如果当前输入字符串中不包含小数点且当前未显示结果
                if (!currentInput.contains(".") &&!isResultDisplayed) {
                    // 将小数点添加到当前输入字符串中
                    currentInput += buttonText;
                    // 更新显示文本框的内容
                    displayField.setText(currentInput);
                }
            } else if (buttonText.matches("[+\\-*/]")) {
                // 如果按钮文本是四则运算符
                if (!currentInput.isEmpty()) {
                    // 将当前输入字符串转换为BigDecimal类型，作为第一个操作数
                    operand1 = new BigDecimal(currentInput);
                    // 记录下当前选择的运算符
                    operator = buttonText;
                    // 清空当前输入字符串，准备接收下一个数字作为第二个操作数
                    currentInput = "";
                }
            } else if (buttonText.equals("=")) {
                // 如果按钮文本是等号
                if (!operator.isEmpty() &&!currentInput.isEmpty()) {
                    // 将当前输入字符串转换为BigDecimal类型，作为第二个操作数
                    operand2 = new BigDecimal(currentInput);
                    // 调用计算结果的方法，获取计算结果
                    BigDecimal result = calculateResult();
                    // 将计算结果转换为字符串并显示在文本框中
                    displayField.setText(result.toString());
                    // 更新当前输入字符串为计算结果
                    currentInput = result.toString();
                    // 设置结果显示标记为true，表示已经显示了结果
                    isResultDisplayed = true;
                }
            } else if (buttonText.equals("sqrt")) {
                // 如果按钮文本是开根号
                if (!currentInput.isEmpty()) {
                    // 将当前输入字符串转换为BigDecimal类型，作为操作数
                    operand1 = new BigDecimal(currentInput);
                    // 通过Math.sqrt()方法计算开根号结果，先将BigDecimal转换为double类型进行计算，
                    // 再将结果转换回BigDecimal类型
                    BigDecimal result = BigDecimal.valueOf(Math.sqrt(operand1.doubleValue()));
                    // 将计算结果显示在文本框中
                    displayField.setText(result.toString());
                    // 更新当前输入字符串为计算结果
                    currentInput = result.toString();
                    // 设置结果显示标记为true，表示已经显示了结果
                    isResultDisplayed = true;
                }
            } else if (buttonText.equals("1/x")) {
                // 如果按钮文本是求倒数
                if (!currentInput.isEmpty()) {
                    // 将当前输入字符串转换为BigDecimal类型，作为操作数
                    operand1 = new BigDecimal(currentInput);
                    // 如果操作数不为零
                    if (operand1.compareTo(BigDecimal.ZERO)!= 0) {
                        // 通过BigDecimal.ONE.divide()方法计算倒数
                        BigDecimal result = BigDecimal.ONE.divide(operand1);
                        // 将计算结果显示在文本框中
                        displayField.setText(result.toString());
                        // 更新当前输入字符串为计算结果
                        currentInput = result.toString();
                        // 设置结果显示标记为true，表示已经显示了结果
                        isResultDisplayed = true;
                    } else {
                        // 如果操作数为零，弹出错误提示框，告知用户不能除以零
                        JOptionPane.showMessageDialog(Calculator.this, "不能除以零！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (buttonText.equals("%")) {
                // 如果按钮文本是求余数
                if (!currentInput.isEmpty()) {
                    // 将当前输入字符串转换为BigDecimal类型，作为第一个操作数
                    operand1 = new BigDecimal(currentInput);
                    // 如果已经选择了运算符
                    if (!operator.isEmpty()) {
                        // 将当前输入字符串转换为BigDecimal类型，作为第二个操作数
                        operand2 = new BigDecimal(currentInput);
                        // 通过remainder()方法计算余数
                        BigDecimal result = operand1.remainder(operand2);
                        // 将计算结果显示在文本框中
                        displayField.setText(result.toString());
                        // 更新当前输入字符串为计算结果
                        currentInput = result.toString();
                        // 设置结果显示标记为true，表示已经显示了结果
                        isResultDisplayed = true;
                    } else {
                        // 如果未选择运算符，弹出错误提示框，告知用户请先输入运算符
                        JOptionPane.showMessageDialog(Calculator.this, "请先输入运算符！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (buttonText.equals("C")) {
                // 如果按钮文本是清空（C）
                // 清空当前输入字符串
                currentInput = "";
                // 清空运算符
                operator = "";
                // 将第一个操作数重置为零
                operand1 = BigDecimal.ZERO;
                // 将第二个操作数重置为零
                operand2 = BigDecimal.ZERO;
                // 清空显示文本框的内容
                displayField.setText("");
                // 将结果显示标记重置为false，表示未显示结果
                isResultDisplayed = false;
            } else if (buttonText.equals("←")) {
                // 如果按钮文本是后退一位（←）
                if (!currentInput.isEmpty()) {
                    // 删除当前输入字符串中的最后一个字符
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    // 更新显示文本框的内容
                    displayField.setText(currentInput);
                }
            }
        }
    }

    // 计算结果的方法，根据选择的运算符对两个操作数进行相应的四则运算
    private BigDecimal calculateResult() {
        BigDecimal result = BigDecimal.ZERO;

        switch (operator) {
            case "+":
                result = operand1.add(operand2);
                break;
            case "-":
                result = operand1.subtract(operand2);
                break;
            case "*":
                result = operand1.multiply(operand2);
                break;
            case "/":
                if (operand2.compareTo(BigDecimal.ZERO)!= 0) {
                    // 使用MathContext.DECIMAL128设置除法运算的精度，然后进行除法运算
                    result = operand1.divide(operand2, MathContext.DECIMAL128);
                } else {
                    // 如果除数为零，弹出错误提示框，告知用户不能除以零
                    JOptionPane.showMessageDialog(this, "不能除以零！", "错误", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }

        return result;
    }

    // 主方法，用于启动计算器应用程序
    public static void main(String[] args) {
        // 使用SwingUtilities.invokeLater()方法确保在事件调度线程中创建和显示计算器窗口
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}