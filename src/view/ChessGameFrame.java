package view;
import controller.GameController;
import model.Chessboard;
import model.Music;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;

public class ChessGameFrame extends JFrame {
    private Music music;
    private JButton playButton;
    private boolean isPlaying = false;
  //  private int clickCounter = 0;
    public static int counter = 0;
    public static JLabel statusLabel;
    public static JLabel statusLabelOne;
    public static int count;
    public static ImageIcon backgroundIcon = Them.sunSet.getBackgroundIcon();
    public static Image backgroundImage = backgroundIcon.getImage();
    private final int WIDTH;
    private final int HEIGTH;
    private final int ONE_CHESS_SIZE;
    private ChessboardComponent chessboardComponent;
    JPanel backgroundPanel = new JPanel() {

        private static final long serialVersionUID = 1L;

        public void paint(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    };

    public static int getCount() {
        return count;
    }

    public ChessGameFrame(int width, int height) {

        setTitle("Jungle"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
        addBGMButton();
        addRESTARTButton();
        backgroundPanel.setOpaque(false);
        backgroundPanel.setSize(720, 720);
        backgroundPanel.setLocation(0, 0);
        add(backgroundPanel);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    private void addLabel() {
        count = 2;
        statusLabel = new JLabel("round :" + count / 2);
        statusLabelOne = new JLabel("current player: " + GameController.currentPlayer);
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabelOne.setLocation(HEIGTH, 60 + HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabelOne.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabelOne.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        add(statusLabelOne);
    }

    private void addHelloButton() {
        JButton button = new JButton("INTRO");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, """
                Welcome!!

                Jungle or Dou Shou Qi (斗兽棋),
                 is a modern Chinese board game with an obscure history,\s
                The game is played on a 7×9 board and is popular with\s
                 children in the Far East. Jungle is a two-player strategy\s
                game and has been cited by The Playboy Winner's Guide to\s
                Board Games as resembling the Western game Stratego.

                Hope you enjoy it!!   ٩(•̀ω•́)۶\s"""));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

 /*   private void load(){
        JFileChooser jfi = new JFileChooser(new File("../data"));
        jfi.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File file = jfi.getSelectedFile();
        try{
            if(file != null){
                if(!file.getName().endsWith(".txt")){
                    JOptionPane.showMessageDialog(null,"The file has a wrong format");
                }else{
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 7; j++) {
                            if( Chessboard.grid[i][j]){

                            }

                        }
                    }
                }

            }

        }

    }

  */
    public void safe() {
  //      JFileChooser file = new JFileChooser();
  //      file.setCurrentDirectory(new File(".")); //当前文件的目录
        /*
        JFileChoose.FILES_ONLY 只能选文件
         JFileChoose.DIRECTIONS_ONLY 只能选文件夹
         JFileChoose.FILES_AND_DIRECTIONS_ONLY 只能选文件夹

         1.构造方法/选择文件时的目录
         2.设置文件打开方式
         3.是否可以多选
         4.设置文件过滤器（可选择的文件类型）；不添加设置默认所有文件
         5.设定所选择的文件/文件夹
         6.获取选择的文件/文件夹
         7.显示选择/保存文件窗口
         8.用7中state的返回值
         9.设置显示框的标题

         JFileChooser.CANCEL_OPTION 点击了取消或关闭
         JFileChooser.APPROVE_OPTION 点击了确认或保存
         JFileChooser.ERROR_OPTION 出现错误
         */
        JFileChooser jfi = new JFileChooser(new File("../data")); //当前文件的目录
        jfi.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfi.setMultiSelectionEnabled(true); //可以多选文件
        jfi.setFileFilter(new FileNameExtensionFilter("zip(*.txt)","txt")); //文件过滤器
   //     int state = jfi.showOpenDialog(null); //显示文件选择框
        jfi.setSelectedFile(new File(counter + ".txt")); //5
        File file = jfi.getSelectedFile();
        int state = jfi.showOpenDialog(null);
        jfi.setDialogTitle("Please choose a path");
 //       jfi.showDialog(new JLabel(), "选择");
        counter++;
        if (!file.getName().endsWith(".txt"))
            file = new File(file.getAbsolutePath() + ".txt");
        BufferedWriter writer = null;

        try {
            if (file != null) {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("round" + count / 2);
                writer.newLine();   //换行
                writer.write("currentPlayer" + GameController.currentPlayer);
                writer.newLine();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        if(Chessboard.grid[i][j].getPiece() == null){
                           writer.write("*");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("shu")) {
                            writer.write("鼠");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("lion")) {
                            writer.write("狮");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("xiang")) {
                            writer.write("象");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("lang")) {
                            writer.write("狼");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("gou")) {
                            writer.write("狗");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("mao")) {
                            writer.write("猫");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("bao")) {
                            writer.write("豹");
                        } else if (Chessboard.grid[i][j].getPiece().getName().equals("hu")) {
                            writer.write("虎");
                        }
                    }
                    writer.newLine();
                }
                writer.flush(); //从缓存写入硬盘
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //        button.addActionListener(e -> {
    //           System.out.println("Click load");
    //           String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });

    private void addLoadButton() {
        JButton button = new JButton("Safe");
        button.addActionListener((e) ->
                safe());
        button.setLocation(HEIGTH, HEIGTH / 10 + 190);
        button.setSize(200, 60);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        add(button);
    }

    private void addBGMButton() {
        playButton = new JButton("BGM off");
//        playButton.addActionListener( this);
        playButton.addActionListener((e) -> music.play("D:\\\\CloudMusic\\\\电台节目\\\\库马达.wav") );
        music = new Music();
        playButton.setLocation(HEIGHT, HEIGHT / 10 + 260);
        playButton.setSize(150, 60);
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        add(playButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            if (!isPlaying) {
                new Thread(() -> {
                    music.play("D:\\CloudMusic\\电台节目\\库马达.wav");
                    isPlaying = false;
                    SwingUtilities.invokeLater(() -> playButton.setText("BGM off"));
                }).start();
                isPlaying = true;
                playButton.setText("BGM on");
            }
        } else {
            if (isPlaying) {
                new Thread(() -> {
                    music.stop();
                    isPlaying = false;
                    SwingUtilities.invokeLater(() -> playButton.setText("BGM off"));
                }).start();
                playButton.setText("BGM off");
            }
        }
    }

    private void addRESTARTButton() {
        JButton button = new JButton("RESTART");
        button.addActionListener((e) ->
                GameController.restart());
        button.setLocation(HEIGHT, HEIGHT / 10 + 330);
        button.setSize(150, 60);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        add(button);
    }

}

/*
public static ImageIcon backgroundIcon = Theme.sunSet.getBackgroundIcon();
    public static Image backgroundImage = backgroundIcon.getImage();
    JPanel backgroundPanel = new JPanel() {

        private static final long serialVersionUID = 1L;

        public void paint(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    };
    sunSet(new ImageIcon("D:/proj/res/sunSet.png")),wave(new ImageIcon("D:/proj/res/wave.png"));
    private final ImageIcon backgroundIcon;
    Theme(ImageIcon backgroundIcon) {
        this.backgroundIcon=backgroundIcon;
    }
    public ImageIcon getBackgroundIcon(){
        return backgroundIcon;
 */
