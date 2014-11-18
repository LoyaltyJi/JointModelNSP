package microblog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 * @author qt
 *
 */
public class BeamSearch {
//	public String train_file="";
//	public String test_file="";  //��ѵ���ļ���ʽ��ͬ����Ϊ�ƶ��׼
//	public String output_file=""; 
//	public String model_file="";
//	public String evaluation_file="";
//	public int number_of_iterations=10;// training number;
//	public boolean bNewTrain=true;
//	
//	public ArrayList<String> arrTrainSource;
//	public ArrayList<String> arrTestSource;
//	public Model model= new Model();  //����model;�洢������Ȩ��
//	public ArrayList<String> arrTestResult;
//	
//	private String sentence="";//������ľ���
//	//private int lenofSentence=0;//������ľ��ӳ���
//	public static String[] arrPOS=  {"VA","VC","VE","VV","NR","NT","NN","LC","PN","DT","CD","OD","M","AD","P","CC","CS",
//		                              "DEC","DEG","DER","DEV","AS","SP","ETC","MSP","IJ","ON","LB","SB","BA","JJ","FW","PU"};
//	public ArrayList<State> agenda= new ArrayList<State>();//��ǰ��ע���м�
//	HashMap<String, Float> htFeatures= new HashMap<String,Float>();   //key=��������value=����ֵ
//	
//	
//	private int search_width=50; //�������	
//	private String goldTagSquence="";//�ƽ��ע����	
//	
//	/**
//	 * initial training
//	 * @param train_file �ѱ�ע�ļ�
//	 * @param model_file 
//	 * @param number_of_iterations
//	 * @param bNewTrain  �µ�ѵ��
//	 */
//	public BeamSearch(String train_file, String model_file, int number_of_iterations, boolean bNewTrain, int search_width){
//		this.train_file=train_file;
//		this.model_file = model_file;
//		this.number_of_iterations = number_of_iterations;	
//		this.bNewTrain=bNewTrain;
//		this.search_width=search_width;
//	}
//	public BeamSearch(){		
//	}
//	/**
//	 * initial training
//	 * @param test_file �ѱ�ע�����ļ���ѵ���ļ���ʽ��ͬ
//	 * @param model_file 
//	 * @param output_file ���ղ��Խ��
//	 */
//	public BeamSearch(String test_file, String model_file, String output_file, String evaluation_file,  int search_width){
//		this.test_file=test_file;
//		this.model_file = model_file;
//		this.output_file = output_file;	
//		this.evaluation_file=evaluation_file;
//		this.search_width=search_width;
//	}
//	
//	/**
//	 * �������������Train
//	 * @throws Exception 
//	 */
//	public void trainProcess() throws Exception{
//		initialTrain();
//		for(int n=0;n<number_of_iterations;n++){
//			for(int i=0;i<arrTrainSource.size();i++){
//				this.goldTagSquence = arrTrainSource.get(i);			
//				trainer();			
//			}
//			System.out.println("Iterator:"+n);
//	   }
//		model.save(model_file);		
//	}
//	/**
//	 * ����
//	 */	
//	public void testProcess(){
//		try {
//			initialTest();
//			for(int i=0;i<arrTestSource.size();i++){
//				this.goldTagSquence=arrTestSource.get(i);	//���Լ��ѱ�ע�����Եõ����Ǳ�ע����
//				this.sentence= TagSeConvertState(this.goldTagSquence).GetSentence();    
//				this.arrTestResult.add(Decoder());				
//			}		
//			save(this.arrTestResult, this.output_file);
//			Evaluator eva= new Evaluator(this.arrTestResult, arrTestSource, evaluation_file);
//			eva.Computer();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	private void initialTest(){
//		this.arrTestSource = new ArrayList<String>();
//		this.arrTestResult = new ArrayList<String>();
//		this.model.load(model_file);
//		File file = new File(this.test_file);
//		BufferedInputStream fis;
//		try {
//			fis = new BufferedInputStream(new FileInputStream(file));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					fis, "utf-8"), 5 * 1024 * 1024);// ��50M�Ļ����ȡ�ı��ļ�
//			String line = "";
//			String context = "";
//			while ((line = reader.readLine()) != null) {
//				if(line.trim().length()>0){
//					this.arrTestSource.add(line.trim()+" #_#");//ÿ�����Ӽ��Ͻ�����
//				}				
//			}			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * ѵ����ʼ��
//	 */
//	private void initialTrain(){
//		this.arrTrainSource = new ArrayList<String>();
//		if(this.bNewTrain==true) {
//			this.model.features=new HashMap<String, Double>();
//		}else{
//		    this.model.load(model_file);
//		}
//		File file = new File(this.train_file);
//		BufferedInputStream fis;
//		try {
//			fis = new BufferedInputStream(new FileInputStream(file));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					fis, "utf-8"), 5 * 1024 * 1024);// ��50M�Ļ����ȡ�ı��ļ�
//			String line = "";
//			String context = "";
//			while ((line = reader.readLine()) != null) {
//				if(line.trim().length()>0){
//					this.arrTrainSource.add(line.trim()+ " #_#"); //ÿ�����Ӽ��Ͻ�����);	
//				}				
//			}			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * @throws Exception 
//	 * 
//	 */
//	public void trainer() throws Exception{
//		this.agenda.clear();		
//		this.agenda.add(new State());	
//		State goldState= TagSeConvertState(this.goldTagSquence);
//		this.sentence= goldState.GetSentence();   
//		
//		State curGoldState = new State();
//		
//		String curGoldsequence="";//��ǰ�ָ���ַ���
//		int icurGoldWordIndex=0, iintraWord=0;//�ƽ�״̬�Ĵ��±꣬�����±ꡣ
//		String [] arrGoldWord = new String[goldState.size];  //��׼�ĵ�������
//		String [] arrGoldTag = new String[goldState.size];   //��׼��tag����
//		for(int i=0;i<goldState.size; i++){
//			String [] tempStrs= goldState.content[i].split("_");
//			arrGoldWord[i] = tempStrs[0];
//			arrGoldTag[i] = tempStrs[1];
//		}
//		
//		for(int i=0; i<sentence.length();i++){
//			ArrayList<State> temAgenda = new ArrayList<State>();
//			String curSChar = String.valueOf(sentence.charAt(i));
//			for(int j=0;j<agenda.size();j++){
//			    State state = new State(agenda.get(j));
//			    if(i>0){
//					State tempCands=Append(state, curSChar);  //append action
//					temAgenda.add(tempCands);	
//			    }				
//				for(int k=0;k<arrPOS.length;k++){
//					State temCand=Sep(state, curSChar, arrPOS[k]);//seperate action
//					temAgenda.add(temCand);
//				}
//			}			
//			this.agenda= Best(temAgenda , this.search_width);
//			
//			//�����׼�Ĳ�������
//			boolean bnewWord=false;
//			if(iintraWord>arrGoldWord[icurGoldWordIndex].length()-1){
//				icurGoldWordIndex++;
//				iintraWord=0;
//				bnewWord=true;
//			}
//			String curChar= arrGoldWord[icurGoldWordIndex].substring(iintraWord,iintraWord+1);
//			if(curGoldState.size==0 || bnewWord==true ){//���� ���´�
//				curGoldState = this.Sep(curGoldState, curChar, arrGoldTag[icurGoldWordIndex]); 				
//			}else {
//				curGoldState = this.Append(curGoldState, curChar);
//			}
//			iintraWord++;			
//			curGoldsequence  = curGoldState.toString();
//			System.out.println(curGoldsequence);
//			boolean bEqual=false;
//			for(int j=0;j<agenda.size();j++){				
//				if(agenda.get(j).toString().equals(curGoldsequence))
//					bEqual=true;
//			}
//			if(bEqual==false)//û�б�ע������ƽ��׼��ͬ
//			{
//				model.addWeighte(curGoldState.arrFeature);//��׼Ȩ������
//			    State temState=Best(this.agenda,1).get(0);
//			    model.subtractWeighte(temState.arrFeature);	
//			}			
//		}
//		
//		this.agenda= Best(this.agenda,1);
//		if((goldState.toString().equals(this.agenda.get(0).toString()))==false){//���յĽ�����׼�����
//			model.addWeighte(goldState.arrFeature);//��׼Ȩ������
//		    model.subtractWeighte(this.agenda.get(0).arrFeature);	
//		}		
//	}
//	
//
//	/**
//	 * �ѱ�ע����ת��ΪState����
//	 * @param tagSequence
//	 * @return
//	 */
//	public State TagSeConvertState(String tagSequence){
//		State newState= new State();
//		newState.content = tagSequence.trim().split(" ");	
//		newState.size=newState.content.length;
//		newState.arrFeature= this.GetLocalFeatures(newState);
//		return newState;
//	}	
//	
//	/**
//	 * ������
//	 * @return
//	 * @throws Exception 
//	 */
//	public String Decoder() throws Exception{
//		this.agenda.clear();		
//		this.agenda.add(new State());		
//		for(int i=0; i<sentence.length();i++){
//			ArrayList<State> temAgenda = new ArrayList<State>();
//			String curChar = String.valueOf(sentence.charAt(i));
//			for(int j=0;j<agenda.size();j++){//����״̬���У������µ�״̬
//			    State state = new State(agenda.get(j));
//			    if(i>0){
//					State tempCands=Append(state,curChar );  //append action
//					temAgenda.add(tempCands);	
//			    }				
//				for(int k=0;k<arrPOS.length;k++){
//					State temCand=Sep(state, curChar, arrPOS[k]);//seperate action
//					temAgenda.add(temCand);
//				}
//			}
//			System.out.println("index:"+i);
//			this.agenda= Best(temAgenda , this.search_width);	//��ǰK��������ߵ�����
//			for(int p=0;p<this.agenda.size();p++){
//				System.out.println((this.agenda.get(0)).toString()+ ":"+ this.agenda.get(0).score);
//			}
//		}
//		this.agenda= Best(this.agenda,1);
//		
//		
//		System.out.println(this.agenda.get(0).toString());
//		return this.agenda.get(0).toString();
//	}	
//	
//	/**
//	 * ����ǰk����ߵı�ע����
//	 * @param n 
//	 * @return
//	 */
//	public ArrayList<State> Best(ArrayList<State> temAgenda, int k){
//		ArrayList<State> retAgenda = new  ArrayList<State>();		
//		
//		State[] array = (State[])temAgenda.toArray(new State[temAgenda.size()]);
//		HeapSort heapSort = new HeapSort();
//		if(k> temAgenda.size()) return temAgenda;
//		retAgenda = heapSort.heapSortK(array, temAgenda.size(), k);        
//        return retAgenda;        
//	}
//	
//	public double CountScore(State state){
//		double score=0;	
//		//double[] fw= new double[arrFeature.size()];
//		for(int i=0;i<state.arrFeature.size();i++){
//			String name = state.arrFeature.get(i);			
//			//fw[i]= 0;//Math.random();
//			if(model.features.containsKey(name)){
//				score+=model.features.get(name);
//			}
//			
//		}
//		//System.out.println();
//		state.score +=score;		
//		return score;			
//	}
//	/**
//	 * 1,2��ʾǰ�����ַ�Ϊ�գ�
//	 * @param state
//	 * @return
//	 */
//	public ArrayList<String> GetLocalFeatures(State state){
//		ArrayList<String> arrF = new ArrayList<String>();
//		
//		String c_0, c_1, c_2;
//		String  w_0,w_1,w_2, t_0,t_1,t_2;
//		String start_w_1, end_w_1, end_w_2;
//		int len_w_1=0, len_w_2=0;
//		int size= state.size;
//		if(size>0){
//			String lastState=state.content[size-1];
//			String prelastState="";
//			String preprelastState="";
//			if(size>1)
//				prelastState=state.content[size-2];
//			else
//				prelastState="1_NO";
//			if(size>2)
//				preprelastState=state.content[size-3];
//			else
//				preprelastState="2_NO";			
//			
//			String[] arrOne= lastState.split("_");
//			String[] arrTwo= prelastState.split("_");
//			String[] arrThree= preprelastState.split("_");
//			t_0= arrOne[1];
//			t_1=arrTwo[1];
//			t_2=arrThree[1];
//			w_0=arrOne[0];
//			w_1=arrTwo[0];
//			w_2=arrThree[0];
//			c_0= String.valueOf(w_0.charAt(w_0.length()-1));
//			if(w_0.length()==1){
//				if(w_1.length()==1){
//					c_1= String.valueOf(w_1.charAt(0));
//					c_2= String.valueOf(w_2.charAt(w_2.length()-1));	
//				}else{
//					c_1=String.valueOf(w_1.charAt(w_1.length()-1));
//					c_2=String.valueOf(w_1.charAt(w_1.length()-2));	
//				}
//			}else{
//				if(w_0.length()==2){
//					c_1=String.valueOf(w_0.charAt(0));
//					c_2=String.valueOf(w_1.charAt(w_1.length()-1));	
//				}else{
//					c_1=String.valueOf(w_0.charAt(0));
//					c_2=String.valueOf(w_0.charAt(1));	
//				}				
//			}
//			start_w_1=w_1.substring(0,1);
//			end_w_1=w_1.substring(w_1.length()-1,w_1.length());
//			end_w_2=w_2.substring(w_2.length()-1,w_2.length());
//			if(w_1.equals("1")) len_w_1=w_1.length();
//			if(w_1.equals("2")) len_w_2=w_2.length();
//				
//			
//			//��������
//			if(state.lastAction.equals("A")){
//				arrF.add("c_1c_0="+c_1+c_0);
//				arrF.add("t_0c_0="+t_0+c_0);
//				arrF.add("c_0t_0c_1="+c_0+t_0+c_1);				
//			}else{
//				arrF.add("w_1="+w_1);
//				arrF.add("w_1w_2="+w_1+"_"+w_2);
//				if(len_w_1==1);
//				  arrF.add("w_1LEN="+w_1);
//				arrF.add("start_w_1_len_w_1="+start_w_1+len_w_1);
//				arrF.add("end_w_1_len_w_1="+end_w_1+len_w_1);
//				arrF.add("end_w_1c_0="+end_w_1+c_0);
//				arrF.add("c_1c_0="+c_1+c_0);
//				arrF.add("begin_w_1_end_w_1="+start_w_1+ end_w_1);
//				arrF.add("w_1c_0="+ w_1+" "+c_0 );
//				arrF.add("end_w_2w_1="+ end_w_2 +"_"+w_1);
//				arrF.add("start_w_1c_0="+start_w_1+c_0);
//				arrF.add("end_w_2_end_w_1="+end_w_2+end_w_1);
//				arrF.add("w_2len="+w_2+len_w_1);
//				arrF.add("len_w_2w_1="+len_w_2+w_1);
//				arrF.add("w_1t_1="+w_1+t_1);
//				arrF.add("t_1t_0="+t_1+t_0);
//				arrF.add("t_2t_1t_0="+t_2+t_1+t_0);
//				arrF.add("w_1t_0="+w_1+t_0);
//				arrF.add("t_2w_1="+t_2+w_1);
//				arrF.add("w_1t_1_end_w_2="+w_1+t_1+end_w_2);
//				arrF.add("w_1t_1c_0="+w_1+t_1+c_0);
//				if(w_1.length()==1) arrF.add(c_2+c_1+c_0+t_1);
//				arrF.add("startt_1="+w_1+t_1);  //23
//				arrF.add("w_1t_1="+w_1+t_1);    //24
//				arrF.add("t_0c_0="+t_0+c_0);   //25
//				if(w_1.contains(c_0) && c_0.equals(end_w_1)==false)
//					arrF.add("ct_1end_w_1="+ c_0+t_1+len_w_1);
//				
//				
//				arrF.add("c_0t_0c_1t_1="+c_0+t_0+c_1+t_1);	//30			
//			}			
//		}	
//		return arrF;
//	}
//	
//	/**
//	 * �ѵ�ǰ�ַ���Ϊһ���´ʼ����ѱ�ע����β��
//	 * @param state
//	 * @param character
//	 * @param pos
//	 * @return
//	 */
//	private State Sep(State state, String curChar, String pos){
//		State newState=new State(state);
//		newState.Sep(curChar, pos);
//		ArrayList<String> newArra = GetLocalFeatures(newState);
//		for(String f : newArra){
//			newState.arrFeature.add(f);
//		}		
//		CountScore(newState);
//		
//		return newState;
//	}
//	
//	/**
//	 * append action: �ѵ�ǰ�ַ�ֱ�Ӽ����ѱ�ע���һ����β��������������ͬ
//	 * @param state
//	 * @param character
//	 * @return
//	 * @throws Exception 
//	 */
//	private State Append(State state, String curChar) throws Exception{
//		State newState=new State(state);
//		newState.Add(curChar);
//		ArrayList<String> newArra = GetLocalFeatures(newState);
//		for(String f : newArra){
//			newState.arrFeature.add(f);
//		}		
//		CountScore(newState);
//		return newState;
//	}	
//	/**
//	 * ������ļ�
//	 * @param arrlist
//	 */
//	private void save(ArrayList<String> arrlist, String filename){
//		FileWriter fw;
//		try {
//			fw = new FileWriter(filename);
//			BufferedWriter bw = new BufferedWriter(fw); // ��������ļ������
//			for(int i=0; i<arrlist.size(); i++){
//				  bw.write(arrlist.get(i).trim()+"\r\n");
//			}	 
//			bw.close();	
//			fw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}// ����FileWriter��������д���ַ���
//	}
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		/*String curGoldsequence="abc_AC";
//		int lastIndex= curGoldsequence.lastIndexOf("_");
//		curGoldsequence = curGoldsequence.substring(0,lastIndex)+"AA"+curGoldsequence.substring(lastIndex,curGoldsequence.length());
//		System.out.println(curGoldsequence);
//		*/
//		// TODO Auto-generated method stub
//		  String s="�����й���!";
//		  String g="����_VE �й���_NN !_PU";
//		  BeamSearch bs= new BeamSearch();
//		  bs.sentence=s;
//		  bs.goldTagSquence = g;
//		  String r="";
//		try {
//			for(int i=0; i<10; i++){
//			   bs.trainer();
//		    }
//			//bs.model.load("e:\\a.txt");
//			r = bs.Decoder();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  System.out.println(r);        
//	}
}
