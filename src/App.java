import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Properties;
import javax.swing.*;
class App extends JFrame{
    int counter = 0;
    Timer timer, p_timer;	
	int second=59, minute=14,p_second=59, p_minute=14;
	String ddSecond, ddMinute;	
	DecimalFormat dFormat = new DecimalFormat("00");
    JLabel time;   
    String[] Time ={"1 Hour","2 Hour"};
    App() throws IOException{
        Properties props = System.getProperties();
        props.setProperty("javax.accessibility.assistive_technologies", "");
        JPanel container,logo;
        JComboBox select_timer;
        JLabel title, text,para, para2, tNotify;
        JButton pospond, minimize, shutdown;
        logo = new JPanel();
        logo.setBounds(0,10,30,30);
        title = new JLabel("Shutdown in progress",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setBounds(20, 10, 620, 50);

        text = new JLabel("Your computer must be restarted to install an important security update",SwingConstants.CENTER);
        text.setFont(new Font("Arial",Font.PLAIN,16));
        text.setBounds(0, 38, 620, 50);

        para = new JLabel("to protect your PC",SwingConstants.CENTER);
        para.setFont(new Font("Arial",Font.PLAIN,16));
        para.setBounds(0, 55, 620, 50);

        para2 = new JLabel("Please save your work and restart your computer",SwingConstants.CENTER);
        para2.setFont(new Font("Arial",Font.PLAIN,16));
        para2.setBounds(0, 75, 620, 50);

        tNotify = new JLabel("HH:MM:SS",SwingConstants.CENTER);
        tNotify.setFont(new Font("Arial",Font.PLAIN,16));
        tNotify.setBounds(0, 115, 620, 50);

        time = new JLabel("14:59",SwingConstants.CENTER);
        time.setFont(new Font("Arial",Font.BOLD,20));
        time.setBounds(0, 145, 620, 50);
        s_countdownTimer();
        p_countdownTimer();
		timer.start();

        select_timer = new JComboBox(Time);
        select_timer.setBounds(240, 200, 150, 30);

        container = new JPanel();
        container.setLayout(null);
        container.setBounds(0, 280, 620, 40);

        pospond = new JButton();
        pospond.setText("Pospond");
        pospond.setBounds(260,0,100,30);
        container.add(pospond);

        pospond.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                String s_case = (String) select_timer.getSelectedItem();

                switch(s_case){
                    case "1 Hour":
                        p_second = 59;
                        p_minute = 59;
                        break;

                    case "2 Hour":
                        p_second = 59;
                        p_minute = 119;
                        break;

                }
                
                if(counter < 2){
                    time.setText("14:59");
                    timer.stop();
                    p_timer.start();
                    setVisible(false);
                    counter += 1;
                }
                else{
                    pospond.setEnabled(false);
                }
                
            }
        }
        );

        minimize = new JButton();
        minimize.setText("Minimize");
        minimize.setBounds(10,0,100,30);
        container.add(minimize);

        minimize.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setState(JFrame.ICONIFIED);
            }
        }
        );

        shutdown = new JButton();
        shutdown.setText("Shutdown");
        shutdown.setBounds(490,0,120,30);
        container.add(shutdown);

        shutdown.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    shutdown();
                } catch (RuntimeException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        );
        
        

        this.setSize(620,380);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setVisible(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.add(title);
        this.add(text);
        this.add(para);
        this.add(para2);
        this.add(tNotify);
        this.add(time);
        this.add(select_timer);
        this.add(container);
        this.add(logo);
    }

    

    public static void shutdown() throws RuntimeException, IOException {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");
    
        if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
            shutdownCommand = "shutdown -h now";
        }
        else if ("Windows 10".equals(operatingSystem)) {
            shutdownCommand = "shutdown /s /t 0";
        }
        else {
            shutdownCommand = "shutdown /s /t 0";
        }
    
        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }

    public  void s_countdownTimer() {
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);	
				time.setText(ddMinute + ":" + ddSecond);
				
				if(second==-1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);	
					time.setText(ddMinute + ":" + ddSecond);
				}
				if(minute==0 && second==0) {
					timer.stop();
                    try {
                        shutdown();
                    } catch (RuntimeException | IOException e1) {
                        e1.printStackTrace();
                    }
				}
			}
		});	}
        public  void p_countdownTimer() {
		
            p_timer = new Timer(1000, new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    p_second--;
                    ddSecond = dFormat.format(p_second);
                    ddMinute = dFormat.format(p_minute);	
                    time.setText(ddMinute + ":" + ddSecond);
                    
                    if(p_second==-1) {
                        p_second = 59;
                        p_minute--;
                        ddSecond = dFormat.format(p_second);
                        ddMinute = dFormat.format(p_minute);	
                        time.setText(ddMinute + ":" + ddSecond);
                    }
                    if(p_minute==0 && p_second==0) {
                        p_timer.stop();
                        setVisible(true);
                        second = 59;
                        minute = 14;
                        timer.start();
                    }
                }
            });	
    }
    
    public static void main(String[] args) throws IOException{
        new App();
    }
}
