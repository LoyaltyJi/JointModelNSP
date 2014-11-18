package microblog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Evaluator {
	 public BufferedWriter bw ;
	 public BufferedWriter bwlog;
	 public String error_file;
     public String[] arrTestResult;
     public String[] arrTestStand;    
     int iSegTP=0, iSegFP=0, iSegFN=0;
     int iTagTP=0, iTagFP=0, iTagFN=0;
     
     public Evaluator(){    	 
     }
     public Evaluator(String[] arrTestResult, String[] arrTestStand, BufferedWriter bwlog, String error_file){
    	 this.arrTestResult=arrTestResult;
    	 this.arrTestStand=arrTestStand;   
    	 this.error_file = error_file;
    	 this.bwlog = bwlog;
    	 
    	FileWriter fw;
  		try {
  			fw = new FileWriter(error_file);
  			bw = new BufferedWriter(fw); // ��������ļ������  			
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}// ����FileWriter��������д���ַ���
  		
     }
     
     public void Computer(){
    	 for(int i=0; i<arrTestStand.length;i++){
    		 String sStand = arrTestStand[i];
    		 String sResult= arrTestResult[i];    		 
    		 compareTwoSequence(sStand, sResult);   		 
    	 }
    	 
    	 Save();
    	 try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     public void Save(){
    	 float segP=(float) (iSegTP/((iSegTP+iSegFP)*1.0));
    	 float segC=(float) (iSegTP/((iSegTP+iSegFN)*1.0));
     	 float segF=2*segP*segC/(segP+segC);
     	 float tagP=(float) (iTagTP/((iTagTP+iTagFP)*1.0));
   	     float tagC=(float) (iTagTP/((iTagTP+iTagFN)*1.0));
    	 float tagF=2*tagP*tagC/(tagP+tagC);
    	 try {
			bwlog.write("segmentation result: precise="+ segP + "     call rate="+ segC + "   F="+ segF + "\r\n");
			bwlog.write("segmentation result: precise="+ tagP + "     call rate="+ tagC + "   F="+ tagF + "\r\n");
		    	
    	 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	
 		
     }
     
     public void compareTwoSequence(String sStand, String sResult){
    	 if(sStand.equals(sResult) == false){
    		 try {
    			 bw.write("stand :" + sStand.substring(0,sStand.length()-5)+"\r\n");    		
    			 bw.write("result:" + sResult.substring(0,sResult.length()-5)+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }
    	 State standState= new State();
    	 standState = standState.TagSeConvertState(sStand);
    	 State resultState= new State();
		 resultState = resultState.TagSeConvertState(sResult);
		 //ȥ�������Ľ�β��ʶ��
		 standState.arrWord[standState.size-1]=null; 
		 standState.arrTag[standState.size-1]=null;  
		 standState.size -= 1;
		 resultState.arrWord[resultState.size-1]=null;
		 resultState.arrTag[resultState.size-1]=null;
		 resultState.size -= 1;
		 
    	 int iCurIndexOfStandChar = 0;  //��׼���е�ǰ�ַ�ָ��
		 int iCurIndexOfResultChar = 0; //������е�ǰ�ַ�ָ��
		 int iCurIndexOfStandWord = 0;  //��׼���е�ǰ��ָ��
		 int iCurIndexOfResultWord = 0; //������е�ǰ��ָ��
		 String curWordOfStand="";  //��׼���е�ǰ��
		 String curWordOfResult="";//������е�ǰ��
		 String curTagOfStand="";  //
		 String curTagOfResult=""; //
		 boolean bEndOfStand = false;
		 boolean bEndOfResult = false;
		 boolean bMoveStand = false; 
		 boolean bMoveResult = false;
		 if(iCurIndexOfStandWord<standState.size){			
			    curWordOfStand = standState.arrWord[iCurIndexOfStandWord];
			    curTagOfStand = standState.arrTag[iCurIndexOfStandWord];
			   iCurIndexOfStandChar += curWordOfStand.length();
			 }
			 if(iCurIndexOfResultWord<resultState.size){
				
			    curWordOfResult =  resultState.arrWord[iCurIndexOfResultWord];
			    curTagOfResult = resultState.arrTag[iCurIndexOfResultWord];
			    iCurIndexOfResultChar += curWordOfResult.length();
			 }
		 while(true){ 
			 
			 if(iCurIndexOfStandWord<standState.size && bMoveStand==true){
				   
	 			    curWordOfStand = standState.arrWord[iCurIndexOfStandWord];
	 			    curTagOfStand = standState.arrTag[iCurIndexOfStandWord];
	 			    iCurIndexOfStandChar += curWordOfStand.length();
	 		  }else if (iCurIndexOfStandWord>=standState.size){
	 			    bEndOfStand = true;
	 		  }
			 if(iCurIndexOfResultWord<resultState.size && bMoveResult==true){	
				    //System.out.println(curWordOfResult+ ":" +iCurIndexOfResultWord );
	  			    curWordOfResult = resultState.arrWord[iCurIndexOfResultWord];
	  			    curTagOfResult = resultState.arrTag[iCurIndexOfResultWord];
	  			    iCurIndexOfResultChar += curWordOfResult.length();
	 		}else  if (iCurIndexOfResultWord>=resultState.size){
	 			   bEndOfResult = true;
	 		}
			 if(bEndOfStand==true && bEndOfResult==true) break;  //������β���˳� 		 
			 if(curWordOfStand.equals(curWordOfResult)){
				 iSegTP++;
				 if(curTagOfStand.equals(curTagOfResult)){
					 iTagTP++; 
				 }else{
					 iTagFN++; iTagFP++;
				 }
				 iCurIndexOfStandWord++;  iCurIndexOfResultWord++;   
				 bMoveStand=true; bMoveResult=true;
			 }else{    				 
				 if(iCurIndexOfStandChar< iCurIndexOfResultChar){
					 iSegFN++; iTagFN++;
					 iCurIndexOfStandWord++;    
					 bMoveStand=true; bMoveResult=false;
				 }else if(iCurIndexOfStandChar == iCurIndexOfResultChar){
					 iSegFN++; iTagFN++;iSegFP++; iTagFP++;
					 iCurIndexOfStandWord++; iCurIndexOfResultWord++;  
					 bMoveStand=true; bMoveResult=true;
				 }else{
					 iSegFP++; iTagFP++;
					 iCurIndexOfResultWord++;  
					 bMoveResult=true; bMoveStand=false;
				 }    				 
			 }   			 
			 
		 }
		 return;   	 
     }
     
     
     
     
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String r="����_VE �й���_NN !_CS";
		String s="��_NN ��_VE �й���_NN !_PU";
		Evaluator  ev= new Evaluator();
		ev.compareTwoSequence(s, r);
		System.out.println("seg:"+ev.iSegTP+"  "+ev.iSegFP +"   "+ ev.iSegFN);
		System.out.println("tag:"+ev.iTagTP+"  "+ev.iTagFP +"   "+ ev.iTagFN);
		
		 //int iSegTP=0, iSegFP=0, iSegFN=0;
	     //int iTagTP=0, iTagFP=0, iTagFN=0;
		
	}

}
