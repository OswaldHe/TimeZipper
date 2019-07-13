package the_hack_project;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;


public class Swing2 {
	private static final int Min = 0;
	private static final int Max = 100;
	private static int current = Max;
	private static int locX = 15;
	private static int locY = 80;
	private static int EventNum = 0;
	
	public static class time { 
		private int hrs, min;
	    public time (int h, int m) {
	    	assert hrs <= 23 && hrs >= 0 && min <= 60 && min >= 0; 
	        hrs = h; 
	        min = m;
	    }  	
	    public void addtime(int min){
	    	int newmin = this.min+min;
	    	if(newmin>=60) {
	    		this.min = newmin%60;
	    		this.hrs+=newmin/60;
	    	}else {
	    		this.min = newmin;
	    	}
	    	
	    }
	    
	    public int subtime(time t) {
	    	return (this.hrs-t.hrs)*60+(this.min-t.min);
	    }
	    
	    
	    
	   
	    
	}
	public static time starttime = new time(6,30);
	
	public static class Event{
		private int tr, impor, num;
		private time t;
		private String name;
		public Event(int a, int b, int c, time d, String e){
			tr = a;
			impor = b;
			num = c;
			t =d;
			name = e;
		}
	}
	public static ArrayList<Event> eventlist = new ArrayList<Event>();
	public static ArrayList<Event> resultlist = new ArrayList<Event>();
	
	public static final JPanel jp1 = new JPanel(null);
	public static boolean[] vis ;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame("TimeZipper");
		jf.setSize(250, 250);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		//JTabbedPane tp = new JTabbedPane();
		
		//JPanel jp2 = new JPanel();
		//JPanel jp3 = new JPanel();
		
		JLabel title = new JLabel();
		title.setText("TimeLine");
		title.setFont(new Font("Century", Font.BOLD, 13));
		title.setBounds(95, 10, 120, 13);
		
		JLabel back = new JLabel();
		back.setIcon(new ImageIcon("/Users/admin/Desktop/Oswald/信奥/USACO/src/the_hack_project/TZ_icon.png"));
		back.setBounds(0, 0, 250, 250);
		back.setOpaque(false);
		
		JLabel label1 = new JLabel();
		label1.setText("Event Entered: ");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		label1.setForeground(Color.white);
		label1.setBounds(20, 130, 120, 20);
		JLabel label2 = new JLabel();
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		label2.setBounds(150, 130, 40, 20);
		label2.setText(String.valueOf(EventNum));
		label2.setForeground(Color.white);
		
		
		/*JButton complete = new JButton();
		complete.setText("Complete!");
		complete.setFont(new Font("Ariel", Font.BOLD, 10));
		complete.setForeground(Color.green);
		complete.setBounds(15, 50, 90, 30);
		JButton abandon = new JButton();
		abandon.setText("Cancel it!");
		abandon.setFont(new Font("Ariel", Font.BOLD, 10));
		abandon.setForeground(Color.red);
		abandon.setBounds(120, 50, 90, 30);
*/
		JButton pop = new JButton("Enter Info");
		pop.setFont(new Font("Century Gothic", Font.BOLD, 12));
		pop.setForeground(Color.blue);
		pop.setBounds(130, 180, 90, 30);
		pop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				EventNum++;
				label2.setText(String.valueOf(EventNum));
				showCustomDialog(jf,jf);
			}
		});
		JButton finish = new JButton("Finished");
		finish.setFont(new Font("Century Gothic", Font.BOLD, 12));
		finish.setForeground(Color.blue);
		finish.setBounds(30, 180, 90, 30);
		finish.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				vis = new boolean[eventlist.size()];
				resultlist = new ArrayList<Event>();
				for(int i = 0; i<eventlist.size(); i++) {
			    	   double maxscore = 0.0;
			    	   int maxindex = 0;
			    	   for(Event a:eventlist) {
			    		  double score = 0;
			    		  time endtime = starttime;
			    		  endtime.addtime(a.tr);
			    		  int remain = a.t.subtime(endtime);
			    		  score +=((360-remain)/360.0)*0.6;
			    		  score += (a.impor/10.0)*0.6;
			    		  if(score>maxscore&&!vis[a.num]) {
			    			  maxscore = score;
			    			  maxindex = a.num;
			    			  
			    		  }
			    	   }
			    	   vis[maxindex]=true;
			    	   starttime.addtime(eventlist.get(maxindex).tr);
			    	   System.out.print(maxindex+"-");
			    	   resultlist.add(eventlist.get(maxindex));
			    	   //sort();
			       }
				showResult(jf,jf);
			}
		});
		
		jp1.add(pop);
		jp1.add(finish);
		jp1.add(title);
		jp1.add(label1);
		jp1.add(label2);
		jp1.add(back);
		/*jp1.add(complete);
		jp1.add(abandon);*/
		
		jf.setContentPane(jp1);
		
		jf.setVisible(true);
		
//		new Timer(1000, new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e){
//				current++;
//				event1pb.setValue(current);
//			}
//		}).start();
		
		
	}
	public static void sort(){
		ArrayList<Integer> index = new ArrayList<Integer>();
		for(int i = 0; i<eventlist.size(); i++){
			int remaining = eventlist.get(i).t.subtime(starttime);
			int num = 0;
			while(remaining>0){
				remaining -= eventlist.get(i++).tr;
				num++;
			}
			index.add(num);
		}
		//Collection.sort(index);
	}
	
	public static void showResult(Frame owner, Component parent){
		final JDialog dialog = new JDialog(owner, "Sorting Result", true);
		dialog.setSize(250, 250);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(parent);
		JPanel diapane = new JPanel(null);
		
		JLabel title = new JLabel();
		title.setText("Result Timeline");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		title.setBounds(95, 10, 130, 10);
		
		int y = 30;
		for(int i = 1; i<=EventNum; i++){
			JLabel newEvent = new JLabel(String.valueOf(i)+". "+resultlist.get(i-1).name +"  Deadline: "+resultlist.get(i-1).t.hrs+":"+resultlist.get(i-1).t.min);
			newEvent.setBounds(15, y, 170, 20);
			newEvent.setFont(new Font("Ariel", Font.PLAIN, 12));
			y+=20;
			diapane.add(newEvent);
			if(i==1){
				JProgressBar pb1 = new JProgressBar();
				pb1.setMinimum(Min);
				pb1.setMaximum(Max);
				pb1.setValue(current);
				pb1.setStringPainted(true);
				pb1.setBounds(15, y, 170, 20);
				y+=20;
				diapane.add(pb1);
				new Timer(1000, new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						current --;
						pb1.setValue(current);
					}
				}).start();
			}
		}
		
		
		dialog.setContentPane(diapane);
		dialog.setVisible(true);
	}
	public static void showCustomDialog(Frame owner, Component parent){
		final JDialog dialog = new JDialog(owner, "Add Event", true);
		dialog.setSize(250, 250);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(parent);
		JPanel diapane = new JPanel(null);
		
		JLabel prompt1 = new JLabel();
		prompt1.setText("Event Name: ");
		prompt1.setBounds(20, 30, 80, 15);
		prompt1.setFont(new Font("Ariel", Font.PLAIN, 12));
		JLabel prompt2 = new JLabel();
		prompt2.setText("Deadline： ");
		prompt2.setBounds(20, 60, 95, 15);
		prompt2.setFont(new Font("Ariel", Font.PLAIN, 12));
		JLabel prompt3 = new JLabel();
		prompt3.setText("Time needed: ");
		prompt3.setBounds(20, 90, 80, 15);
		prompt3.setFont(new Font("Ariel", Font.PLAIN, 12));
		JLabel prompt4 = new JLabel();
		prompt4.setText("Importance: ");
		prompt4.setBounds(20, 120, 80, 15);
		prompt4.setFont(new Font("Ariel", Font.PLAIN, 12));
		JLabel prompt5 = new JLabel("Category: ");
		prompt5.setBounds(20, 150, 80, 15);
		prompt5.setFont(new Font("Ariel", Font.PLAIN, 12));
		
		JTextField name = new JTextField(8);
		name.setBounds(120, 30, 90, 20);
		name.setFont(new Font(null, Font.PLAIN, 13));
		JTextField hrs = new JTextField(2);
		hrs.setBounds(120, 60, 50, 20);
		hrs.setFont(new Font(null, Font.PLAIN, 13));
		JLabel mid = new JLabel(":");
		mid.setFont(new Font(null, Font.PLAIN, 13));
		mid.setBounds(180, 60, 10, 10);
		JTextField mins = new JTextField(2);
		mins.setBounds(190, 60, 50, 20);
		mins.setFont(new Font(null, Font.PLAIN, 13));
		String[] times = new String[]{"10 mins","20 mins","30 mins", "40 mins","50 mins", "60 mins", "70 mins", "80 mins", "90 mins"};
		JComboBox<String> timerequired = new JComboBox<String>(times);
		timerequired.setSelectedIndex(1);
		timerequired.setBounds(120, 90, 110, 20);
		JSlider importance = new JSlider(0, 10, 5);
		importance.setMajorTickSpacing(2);
		importance.setMinorTickSpacing(1);
		importance.setPaintTicks(true);
		importance.setPaintLabels(true);
		Hashtable<Integer, JComponent> labels = new Hashtable<Integer, JComponent>();
		labels.put(0, new JLabel("L"));
		labels.put(5, new JLabel("M"));
		labels.put(10, new JLabel("H"));
		importance.setLabelTable(labels);
		importance.setBounds(100, 110, 150, 40);
		String[] category = new String[]{"Essay","Group project","Worksheet","Test Paper"};
		JComboBox<String> cate = new JComboBox<String>(category);
		cate.setSelectedIndex(0);
		cate.setBounds(120, 160, 90, 20);
		
		
		JButton submit = new JButton("submit");
		submit.setFont(new Font(null, Font.BOLD, 12));
		submit.setBounds(130, 200, 80, 30);
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int a, b, c, d;
				String en = name.getText();
				a = Integer.parseInt(hrs.getText());
				b = Integer.parseInt(mins.getText());
				time t = new time(a,b);
				c = (timerequired.getSelectedIndex()+1)*10;
				d = importance.getValue();
				System.out.println("Event Name: "+ en);
				System.out.println("Time required: "+ timerequired.getSelectedItem());
				System.out.println("Deadline: "+ hrs.getText()+":"+mins.getText());
				System.out.println("Importance: "+ importance.getValue());
				eventlist.add(new Event(c,d,EventNum-1,t,en));
				
				dialog.dispose();
			}
		});
		
		diapane.add(prompt1);
		diapane.add(prompt2);
		diapane.add(prompt3);
		diapane.add(prompt4);
		diapane.add(prompt5);
		diapane.add(name);
		diapane.add(timerequired);
		diapane.add(mins);
		diapane.add(hrs);
		diapane.add(mid);
		diapane.add(importance);
		diapane.add(submit);
		diapane.add(cate);
		
		dialog.setContentPane(diapane);
		dialog.setVisible(true);
		
		
		
	}
	
	public static void CreateEvent(){
		JLabel newEvent = new JLabel();
		newEvent.setText("newEvent");
		newEvent.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		newEvent.setBounds(locX, locY, 50, 10);
		
		JProgressBar pb = new JProgressBar();
		pb.setMinimum(Min);
		pb.setMaximum(Max);
		pb.setValue(current);
		pb.setStringPainted(true);
		pb.setBounds(locX+60, locY-3, 120, 20);
		
		JButton complete = new JButton();
		complete.setText("Complete!");
		complete.setFont(new Font("Ariel", Font.BOLD, 10));
		complete.setForeground(Color.green);
		complete.setBounds(locX, locY+20, 90, 30);
		
		JButton abandon = new JButton();
		abandon.setText("Cancel it!");
		abandon.setFont(new Font("Ariel", Font.BOLD, 10));
		abandon.setForeground(Color.red);
		abandon.setBounds(locX+105, locY+20, 90, 30);
		locY += 50;
		
		
		jp1.add(newEvent);
		jp1.add(pb);
		jp1.add(complete);
		jp1.add(abandon);
	}

}


